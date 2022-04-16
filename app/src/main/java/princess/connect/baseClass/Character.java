package princess.connect.baseClass;

import java.util.List;

public class Character extends BasicStats {
    protected int _id;
    protected String _name;

    protected int _attackRange;
    protected int _moveSpeed;
    protected double _attackSpeed;

    protected int _level;
    protected int _star;
    protected int _rank;
    protected List<Integer> _equipments;

    protected abstract class Skill {
        protected int _level = 0;
        protected double _skillTime = 0;
        protected double _castTime = 0;

        protected abstract void cast();
    }

    protected class Attack extends Skill {
        public Attack(Double skillTime) {
            _skillTime = skillTime;
            _castTime = skillTime / 2;
        }

        protected void cast() {
            if (_actionFrame == (int) (_castTime * BattleGround.FRAME)) {
                Character chara = frontmost(_enemies);
                switch (_damageType) {
                    case PHYSICAL:
                        chara.takeDamage(_physicalAttack, _damageType);
                        break;
                    case MAGIC:
                        chara.takeDamage(_magicAttack, _damageType);
                        break;
                }
            }
        }
    }

    protected enum SkillType {
        ATTACK, Skill0, SKILL1, SKILL2, SkillEX
    }

    protected List<Skill> _skills;
    protected List<SkillType> _initialPattern;
    protected List<SkillType> _loopPattern;

    protected double _x;
    protected double _y;

    public enum Direction {
        RIGHT, LEFT
    };

    protected Direction _direction;

    protected enum DamageType {
        PHYSICAL, MAGIC
    }

    protected DamageType _damageType;

    public enum Action {
        IDLE, RUN, ATTACK, SKILL0, SKILL1, SKILL2, DIE
    };

    private Action _action = Action.RUN;
    private Action _preAction = null;

    private int _actionFrame = 0;
    private int _idleFrame = 0;

    private Boolean _isChangeAction = true;

    private int _skillIndex = -1;

    protected List<Character> _allies;
    protected List<Character> _enemies;

    public String name() {
        return _name;
    }

    public int x() {
        return (int) _x;
    }

    public int y() {
        return (int) _y;
    }

    public Direction direction() {
        return _direction;
    }

    public Action action() {
        return _action;
    }

    public Action preAction() {
        return _preAction;
    }

    public Boolean isChangeAction() {
        if (_isChangeAction) {
            _isChangeAction = false;
            return true;
        }
        return false;
    }

    protected void act(List<Character> allies, List<Character> enemies) {
        if (_idleFrame == 0) {
            if (frontmost(enemies) == null)
                changeAction(Action.IDLE);
            else if (!isInAttackRange(frontmost(enemies)))
                move();
            else {
                _allies = allies;
                _enemies = enemies;
                _skillIndex++;
                castSkill();
            }
        } else if (_actionFrame == 0) {
            _idleFrame--;
            changeAction(Action.IDLE);
        } else {
            _actionFrame--;
            castSkill();
        }
    }

    private void changeAction(Action action) {
        if (_action != action) {
            _preAction = _action;
            _action = action;
            _isChangeAction = true;
        }
    }

    private double distance(Character chara) {
        return Math.sqrt(Math.pow(_x - chara._x, 2) + Math.pow(_y - chara._y, 2));
    }

    private boolean isInAttackRange(Character chara) {
        if (distance(chara) < _attackRange)
            return true;
        return false;
    }

    private void move() {
        switch (_direction) {
            case RIGHT:
                _x -= Double.valueOf(_moveSpeed) / BattleGround.FRAME;
                break;
            case LEFT:
                _x += Double.valueOf(_moveSpeed) / BattleGround.FRAME;
                break;
        }
        changeAction(Action.RUN);
    }

    private Character frontmost(List<Character> chars) {
        Character chara = null;
        double distance = Double.MAX_VALUE, tmp;
        for (int i = 0; i < chars.size(); i++) {
            if (chars.get(i)._action != Action.DIE) {
                tmp = distance(chars.get(i));
                if (tmp < distance) {
                    distance = tmp;
                    chara = chars.get(i);
                }
            }
        }
        return chara;
    }

    private void takeDamage(int damage, DamageType damageType) {
        switch (damageType) {
            case PHYSICAL:
                _hp -= (int) (damage / (1 + _physicalDefense / 100.0));
                break;
            case MAGIC:
                _hp -= (int) (damage / (1 + _magicDefense / 100.0));
                break;
        }
        if (_hp <= 0)
            changeAction(Action.DIE);
    }

    private SkillType skillType() {
        if (_skillIndex < _initialPattern.size())
            return _initialPattern.get(_skillIndex);
        else
            return _loopPattern.get((_skillIndex - _initialPattern.size()) % _loopPattern.size());
    }

    private void castSkill() {
        SkillType skillType = skillType();
        Skill skill = _skills.get(skillType.ordinal());
        skill.cast();
        changeAction(Action.valueOf(skillType.name()));
        if (_idleFrame == 0) {
            _actionFrame = (int) (skill._skillTime * BattleGround.FRAME);
            _idleFrame = (int) (_attackSpeed * BattleGround.FRAME);
        }
    }
}