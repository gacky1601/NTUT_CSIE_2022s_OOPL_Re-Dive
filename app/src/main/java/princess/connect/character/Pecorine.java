package princess.connect.character;

import java.util.Arrays;

import princess.connect.baseClass.Character;

public class Pecorine extends Character {
    public Pecorine() {
        _id = 1058;
        _name = "Pecorine";
        
        _hitpoints = 2991;
        _physicalAttack = 503;
        _magicAttack = 0;
        _physicalDefense = 7;
        _magicDefense = 3;
        _physicalCritical = 10;
        _magicCritical = 0;
        _evasion = 0;
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

        _skills = Arrays.asList(new SkillUb(), new Skill1(), new Skill2(), new SkillEx());
        _initialPattern = Arrays.asList(2, 1);
        _loopPattern = Arrays.asList(0, 0, 2, 0, 1);

        _damageType = DamageType.PHYSICAL;
    }

    private class SkillUb extends Skill {
        private SkillUb() {
            _castTime = 0;
        }

        protected void cast() {
            System.out.println("UBTest_pekorine");
        }
    }

    private class Skill1 extends Skill {
        private Skill1() {
            _castTime = 0.21;
        }

        protected void cast() {
            System.out.println("Skill1Test_pekorine");
        }
    }

    private class Skill2 extends Skill {
        private Skill2() {
            _castTime = 1.21;
        }

        protected void cast() {
            System.out.println("Skill2Test_pekorine");
        }
    }

    private class SkillEx extends Skill {
        private SkillEx() {
            _castTime = 0;
        }

        protected void cast() {
            System.out.println("ExTest_pekorine");
        }
    }
}
