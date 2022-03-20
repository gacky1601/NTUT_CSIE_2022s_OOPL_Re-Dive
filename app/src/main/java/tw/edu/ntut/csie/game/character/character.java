package tw.edu.ntut.csie.game.character;

import tw.edu.ntut.csie.game.basic.BasicStats;

public class Character extends BasicStats {
    protected int _attackRange;
    protected int _moveSpeed;
    protected double _attackSpeed;

    protected int _level;
    protected int _star;
    protected int _rank;
    protected int[] _equipment;

    protected Skill[] _skill;
    protected int[] _initialPattern;
    protected int[] _loopPattern;

    public void cast(int index) {
        _skill[index].cast();
    }
}
