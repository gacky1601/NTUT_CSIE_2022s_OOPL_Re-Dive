package princess.connect.baseClass;

import java.util.ArrayList;
import java.util.List;

public class Character extends BasicStats {
    protected int _id;
    protected String _name;

    protected int _hp;
    protected int _tp;

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

    private List<ValueDisplay> _valueDisplays;

    public Character() {
        _allies = new ArrayList<>();
        _enemies = new ArrayList<>();
        _isChangeAction = true;
        _actionFrame = 0;
        _idleFrame = 0;
        _skillIndex = -1;
        _valueDisplays = new ArrayList<>();
    }

    public void release() {
        _allies.clear();
        _enemies.clear();
        _valueDisplays.clear();
        _allies = null;
        _enemies = null;
        _valueDisplays = null;
    }

    public String name() {
        return _name;
    }

    public int hp() {
        return _hp;
    }

    public int hitpoints() {
        return _hitpoints;
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

    public List<ValueDisplay> valueDisplays() {
        List<ValueDisplay> damagedisplays = new ArrayList<>(_valueDisplays);
        _valueDisplays.clear();
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

    protected Character frontmost(List<Character> chars) {
        return frontmost(chars, false);
    }

    protected Character frontmost(List<Character> chars, boolean isCondition) {
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

    protected void inflictDamage(Character chara, int damage) {
        if (chara == null || !chara.isAlive())
            return;
        ValueDisplay valueDisplay = new ValueDisplay();
        chara._valueDisplays.add(valueDisplay);
        valueDisplay.valueType = ValueType.valueOf(_damageType.name());
        if (chara._evasion - _accuracy > 0 && valueDisplay.valueType == ValueType.PHYSICAL)
            valueDisplay.isMiss = Math.random() < (1 / (1 + 100.0 / (chara._evasion - _accuracy)));
        if (valueDisplay.isMiss)
            return;
        switch (_damageType) {
            case PHYSICAL:
                valueDisplay.isCritical = Math.random() < (_physicalCritical * 0.005 * _level / chara._level);
                valueDisplay.value = (int) (damage / (1 + chara._physicalDefense / 100.0));
                break;
            case MAGIC:
                valueDisplay.isCritical = Math.random() < (_magicCritical * 0.005 * _level / chara._level);
                valueDisplay.value = (int) (damage / (1 + chara._magicDefense / 100.0));
                break;
        }
        if (valueDisplay.isCritical)
            valueDisplay.value *= 2;
        chara._hp -= valueDisplay.value;
        if (chara._hp <= 0) {
            chara._hp = 0;
            chara.changeAction(Action.DIE);
        }
    }

    protected void grantsRecoverHP(Character chara, int hp) {
        if (chara == null || !chara.isAlive())
            return;
        ValueDisplay valueDisplay = new ValueDisplay();
        chara._valueDisplays.add(valueDisplay);
        valueDisplay.valueType = ValueType.HP;
        valueDisplay.value = (int) (hp * (1 + _hpRecoveryRate / 100.0));
        chara._hp += valueDisplay.value;
        if (chara._hp > chara._hitpoints)
            chara._hp = chara._hitpoints;
    }

    protected void grantsRecoverTP(Character chara, int tp) {
        if (chara == null || !chara.isAlive())
            return;
        ValueDisplay valueDisplay = new ValueDisplay();
        chara._valueDisplays.add(valueDisplay);
        valueDisplay.valueType = ValueType.TP;
        valueDisplay.value = (int) (tp * (1 + _tpRecoveryRate / 100.0));
        chara._tp += valueDisplay.value;
        if (chara._tp > 1000)
            chara._tp = 1000;
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

    public enum ValueType {
        HP, TP, PHYSICAL, MAGIC
    }

    protected enum SkillType {
        ATTACK, Skill0, SKILL1, SKILL2, SkillEX
    }

    protected enum DamageType {
        PHYSICAL, MAGIC
    }

    public class ValueDisplay {
        public ValueType valueType;
        public boolean isMiss = false;
        public boolean isCritical = false;
        public int value = 0;
    }

    protected abstract class Skill {
        protected int _level = 0;
        protected double _skillTime = 0;
        protected double _castTime = 0;

        protected boolean isCastTime() {
            return isCastTime(_castTime);
        }

        protected boolean isCastTime(double castTime) {
            return _actionFrame == (int) ((_skillTime - castTime) * BattleGround.FRAME);
        }

        protected abstract void cast();
    }

    protected class Attack extends Skill {
        private Character chara;

        public Attack(Double skillTime) {
            _skillTime = skillTime;
            _castTime = skillTime / 2;
        }

        protected void cast() {
            if (isCastTime(_skillTime))
                chara = frontmost(_enemies);
            else if (isCastTime()) {
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