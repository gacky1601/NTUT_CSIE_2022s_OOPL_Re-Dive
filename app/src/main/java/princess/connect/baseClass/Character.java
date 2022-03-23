package princess.connect.baseClass;

import java.util.List;

public class Character extends BasicStats {
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

    public void cast(int index) {
        _skills.get(index).cast();
    }

    protected abstract class Skill {
        protected double _castTime;
        protected int _level = 0;

        protected abstract void cast();
    }
}