package princess.connect.baseClass;

import java.util.List;

public class Character extends BasicStats {
    protected int _id;
    protected String _name;

    protected int _attackRange;
    protected int _moveSpeed;
    protected double _attackSpeed;
    protected double _attackCastTime;

    protected int _level;
    protected int _star;
    protected int _rank;
    protected List<Integer> _equipments;

    protected abstract class Skill {
        protected double _castTime;
        protected int _level = 0;

        protected abstract void cast();
    }

    protected List<Skill> _skills;
    protected List<Integer> _initialPattern;
    protected List<Integer> _loopPattern;

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
        IDLE, RUN, ATTACK
    };

    private Action _action = Action.RUN;
    private Action _preAction = null;

    private int _actionFrame = 0;
    private int _idleFrame = 0;

    private Boolean _isChangeAction = true;

    public String name() {
        return _name;
    }

    public int x() {
        return (int) _x;
    }

    public int y() {
        return (int) _y;
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
        if (!isAttackRange(enemies)) {
            move();
            changeAction(Action.RUN);
        } else {
            if (_idleFrame == 0) {
                attack(frontmost(enemies));
                changeAction(Action.ATTACK);
                _actionFrame = (int) (_attackCastTime * BattleGround.FRAME);
                _idleFrame = (int) (_attackSpeed * BattleGround.FRAME);
            } else if (_actionFrame == 0) {
                changeAction(Action.IDLE);
                _idleFrame--;
            } else
                _actionFrame--;
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

    private boolean isAttackRange(List<Character> chars) {
        for (Character chara : chars)
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
        int index = 0;
        double distance = distance(chars.get(index)), tmp;
        for (int i = 1; i < chars.size(); i++) {
            tmp = distance(chars.get(index));
            if (tmp < distance) {
                distance = tmp;
                index = i;
            }
        }
        return chars.get(index);
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
                _hitpoints -= (int) (damage / (1 + _physicalDefense / 100.0));
                break;
            case MAGIC:
                _hitpoints -= (int) (damage / (1 + _magicDefense / 100.0));
                break;
        }
    }
}