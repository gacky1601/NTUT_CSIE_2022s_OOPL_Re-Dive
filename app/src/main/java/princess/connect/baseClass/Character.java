package princess.connect.baseClass;

import java.util.List;

public class Character extends BasicStats {
    protected int _id;
    protected String _name;

    protected int _attackRange;
    protected int _moveSpeed;
    protected double _attackSpeed;
    protected double _attackTime;

    protected int _level;
    protected int _star;
    protected int _rank;
    protected List<Integer> _equipments;

    protected abstract class Skill {

        protected int _level = 0;
        protected double _skillTime = 0;
        protected double _castTime = 0;

        protected abstract void cast(List<Character> allies, List<Character> enemies);
    }

    protected enum SkillType {
        Skill0, SKILL1, SKILL2, SkillEX, ATTACK
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
    public Action _preAction = null;

    private int _actionFrame = 0;
    private int _idleFrame = 0;

    private Boolean _isChangeAction = true;

    private int _skillIndex = -1;

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
            else if (!isInAttackRange(frontmost(enemies))) {
                move();
                changeAction(Action.RUN);
            } else {
                skill(allies, enemies);
            }
        } else if (_actionFrame == 0) {
            changeAction(Action.IDLE);
            _idleFrame--;
        } else
            _actionFrame--;

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

    private void attack(Character chara) {
        switch (_damageType) {
            case PHYSICAL:
                chara.takeDamage(_physicalAttack, _damageType);
                break;
            case MAGIC:
                chara.takeDamage(_magicAttack, _damageType);
                break;
        }
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

    private void cast(SkillType skillType, List<Character> allies, List<Character> enemies) {
        switch (skillType) {
            case ATTACK:
                attack(frontmost(enemies));
                _actionFrame = (int) (_attackTime * BattleGround.FRAME);
                break;
            default:
                _skills.get(skillType.ordinal()).cast(allies, enemies);
                _actionFrame = (int) (_skills.get(skillType.ordinal())._skillTime * BattleGround.FRAME);
                break;
        }
        changeAction(Action.valueOf(skillType.name()));
        _idleFrame = (int) (_attackSpeed * BattleGround.FRAME);
    }

    private void skill(List<Character> allies, List<Character> enemies) {
        _skillIndex++;
        if (_skillIndex < _initialPattern.size())
            cast(_initialPattern.get(_skillIndex), allies, enemies);
        else
            cast(_loopPattern.get((_skillIndex - _initialPattern.size()) % _loopPattern.size()), allies, enemies);
    }
}