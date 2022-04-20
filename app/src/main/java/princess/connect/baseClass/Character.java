package princess.connect.baseClass;

import java.util.ArrayList;
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

    protected List<Skill> _skills;
    protected List<SkillType> _initialPattern;
    protected List<SkillType> _loopPattern;

    protected double _x;
    protected double _y;
    protected Direction _direction;

    protected DamageType _damageType;

    protected List<Character> _allies;
    protected List<Character> _enemies;

    private Action _action;
    private Action _preAction;
    private Boolean _isChangeAction;

    private int _actionFrame;
    private int _idleFrame;

    private int _skillIndex;

    private List<NumberDisplay> _numberDisplays;

    public Character() {
        _allies = new ArrayList<>();
        _enemies = new ArrayList<>();
        _isChangeAction = true;
        _actionFrame = 0;
        _idleFrame = 0;
        _skillIndex = -1;
        _numberDisplays = new ArrayList<>();
    }

    public void release() {
        _allies.clear();
        _enemies.clear();
        _numberDisplays.clear();
        _allies = null;
        _enemies = null;
        _numberDisplays = null;
    }

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

    public boolean isAlive() {
        return _action != Action.DIE;
    }

    public Boolean isChangeAction() {
        if (_isChangeAction) {
            _isChangeAction = false;
            return true;
        }
        return false;
    }

    public List<NumberDisplay> numberDisplays() {
        List<NumberDisplay> damagedisplays = new ArrayList<>(_numberDisplays);
        _numberDisplays.clear();
        return damagedisplays;
    }

    protected void act(List<Character> allies, List<Character> enemies) {
        if (_idleFrame == 0) {
            if (frontmost(enemies, true) == null)
                changeAction(Action.IDLE);
            else if (!isInAttackRange(frontmost(enemies, true)))
                move();
            else {
                _skillIndex++;
                setTarget(allies, enemies);
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
        return frontmost(chars, false);
    }

    private Character frontmost(List<Character> chars, boolean isCondition) {
        Character chara = null;
        double distance = Double.MAX_VALUE, tmp;
        for (int i = 0; i < chars.size(); i++) {
            if (chars.get(i).isAlive() || !isCondition) {
                tmp = distance(chars.get(i));
                if (tmp < distance) {
                    distance = tmp;
                    chara = chars.get(i);
                }
            }
        }
        return chara;
    }

    private void setTarget(List<Character> allies, List<Character> enemies) {
        _allies.clear();
        _enemies.clear();
        for (Character chara : allies)
            if (chara.isAlive())
                _allies.add(chara);
        for (Character chara : enemies)
            if (chara.isAlive())
                _enemies.add(chara);
    }

    private void inflictDamage(Character chara, int damage) {
        if (chara == null || !chara.isAlive())
            return;
        NumberDisplay numberDisplay = new NumberDisplay();
        chara._numberDisplays.add(numberDisplay);
        numberDisplay.numberType = NumberType.valueOf(_damageType.name());
        if (chara._evasion - _accuracy > 0 && numberDisplay.numberType == NumberType.PHYSICAL)
            numberDisplay.isMiss = Math.random() < (1 / (1 + 100.0 / (chara._evasion - _accuracy)));
        if (numberDisplay.isMiss)
            return;
        switch (numberDisplay.numberType) {
            case PHYSICAL:
                numberDisplay.isCritical = Math.random() < (_physicalCritical * 0.005 * _level / chara._level);
                numberDisplay.number = (int) (damage / (1 + chara._physicalDefense / 100.0));
                break;
            case MAGIC:
                numberDisplay.isCritical = Math.random() < (_magicCritical * 0.005 * _level / chara._level);
                numberDisplay.number = (int) (damage / (1 + chara._magicDefense / 100.0));
                break;
        }
        if (numberDisplay.isCritical)
            numberDisplay.number *= 2;
        chara._hp -= numberDisplay.number;
        if (chara._hp <= 0) {
            chara._hp = 0;
            chara.changeAction(Action.DIE);
        }
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

    public enum Direction {
        RIGHT, LEFT
    }

    public enum Action {
        IDLE, RUN, ATTACK, SKILL0, SKILL1, SKILL2, DIE
    }

    public enum NumberType {
        PHYSICAL, MAGIC
    }

    protected enum SkillType {
        ATTACK, Skill0, SKILL1, SKILL2, SkillEX
    }

    protected enum DamageType {
        PHYSICAL, MAGIC
    }

    public class NumberDisplay {
        public NumberType numberType;
        public boolean isMiss = false;
        public boolean isCritical = false;
        public int number = 0;
    }

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
                        inflictDamage(chara, _physicalAttack);
                        break;
                    case MAGIC:
                        inflictDamage(chara, _magicAttack);
                        break;
                }
            }
        }
    }
}