package tw.edu.ntut.csie.game.character;

import java.util.Vector;

public abstract class character {
    private int _hitpoints;
    private int _physicalAttack;
    private int _magicAttack;
    private int _physicalCritical;
    private int _magicCritical;
    private int _physicalDefense;
    private int _magicDefense;
    private int _dodge;
    private int _accuracy;
    private int _hpRecoveryRate;
    private int _tpRecoveryRate;
    private int _waveHpRecovery;
    private int _waveTpRecovery;
    private int _hpReduceRate;
    private int _tpReduceRate;

    private int _attackRange;
    private int _moveSpeed;
    private float _attackSpeed;

    private int _level;
    private int _star;
    private int _rank;
    private Vector<Integer> _equipment;

    private int _ubLevel;
    private int _skill1Level;
    private int _skill2Level;
    private int _exLevel;

    private Vector<Integer> _initialPattern;
    private Vector<Integer> _loopPattern;
}