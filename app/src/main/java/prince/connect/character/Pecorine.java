package prince.connect.character;

public class Pecorine extends Character {
    public Pecorine() {
        _hitpoints = 2991;
        _physicalAttack = 503;
        _magicAttack = 0;
        _physicalDefense = 7;
        _magicDefense = 3;
        _physicalCritical = 10;
        _magicCritical = 0;
        _dodge = 0;
        _accuracy = 0;
        _hpRecoveryRate = 0;
        _tpRecoveryRate = 0;
        _waveHpRecovery = 0;
        _waveTpRecovery = 0;
        _hpReduceRate = 0;
        _tpReduceRate = 0;

        _attackRange = 155;
        _moveSpeed = 450;
        _attackSpeed = 2.25;

        _level = 1;
        _star = 1;
        _rank = 1;

        _skill = new Skill[] { new SkillUb(), new Skill1(), new Skill2(), new SkillEx() };
        _initialPattern = new int[] { 2, 1 };
        _loopPattern = new int[] { 0, 0, 2, 0, 1 };
    }
}

class SkillUb extends Skill {
    public SkillUb() {
        _castTime = 0;
    }

    protected void cast() {
        System.out.println("UBTest");
    }
}

class Skill1 extends Skill {
    public Skill1() {
        _castTime = 0.21;
    }

    protected void cast() {
        System.out.println("Skill1Test");
    }
}

class Skill2 extends Skill {
    public Skill2() {
        _castTime = 1.21;
    }

    protected void cast() {
        System.out.println("Skill2Test");
    }
}

class SkillEx extends Skill {
    public SkillEx() {
        _castTime = 0;
    }

    protected void cast() {
        System.out.println("ExTest");
    }
}
