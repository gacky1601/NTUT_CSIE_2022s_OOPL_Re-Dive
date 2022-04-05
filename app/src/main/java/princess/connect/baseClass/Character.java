package princess.connect.baseClass;

import java.util.List;

public class Character extends BasicStats {
    protected int _id;

    protected int _attackRange;
    protected int _moveSpeed;
    protected double _attackSpeed;

    protected int _level;
    protected int _star;
    protected int _rank;
    protected List<Integer> _equipments;

    protected List<Skill> _skills;
    protected List<Integer> _initialPattern;
    protected List<Integer> _loopPattern;

    protected abstract class Skill {
        protected double _castTime;
        protected int _level = 0;

        protected abstract void cast();
    }

    protected int _x;
    protected int _y;

    protected enum Direction {
        RIGHT, LEFT
    };

    protected Direction _direction;

    protected enum DamageType {
        PHYSICAL, MAGIC
    }

    protected DamageType _damageType;

    private int _restFrame = 0;

    protected void act(List<Character> allies, List<Character> enemies) {
        if (!isAttackRange(enemies))
            move();
        else {
            if (_restFrame == 0) {
                normalAttack(frontmost(enemies));
                _restFrame = (int) _attackSpeed * BasicStats.FRAME;
            } else
                _restFrame--;
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
                _x -= _moveSpeed / BasicStats.FRAME;
                break;
            case LEFT:
                _x += _moveSpeed / BasicStats.FRAME;
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

    private void normalAttack(Character chara) {
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