package princess.connect.character;

import java.util.Arrays;

import princess.connect.baseClass.Character;

public class Kyaru extends Character{
    public Kyaru() {
        _id = 1060;
        _name = "Kyaru";
        
        _hitpoints = 427;
        _physicalAttack = 0;
        _magicAttack = 584;
        _physicalDefense = 3;
        _magicDefense = 5;
        _physicalCritical = 0;
        _magicCritical = 35;
        _evasion = 0;
        _accuracy = 0;
        _hpRecoveryRate = 0;
        _tpRecoveryRate = 0;
        _waveHpRecovery = 0;
        _waveTpRecovery = 0;
        _hpReduceRate = 0;
        _tpReduceRate = 0;

        _attackRange = 750;
        _moveSpeed = 450;
        _attackSpeed = 2.07;
        _attackCastTime = 1.400;

        _level = 1;
        _star = 1;
        _rank = 1;

        _skills = Arrays.asList(new SkillUb(), new Skill1(), new Skill2(), new SkillEx());
        _initialPattern = Arrays.asList(2, 1);
        _loopPattern = Arrays.asList(0, 0, 2, 0, 1);

        _damageType = DamageType.MAGIC;
    }

    private class SkillUb extends Skill {
        private SkillUb() {
            _castTime = 0;
        }

        protected void cast() {
            System.out.println("UBTest_kyaru");
        }
    }

    private class Skill1 extends Skill {
        private Skill1() {
            _castTime = 1.27;
        }

        protected void cast() {
            System.out.println("Skill1Test_kyaru");
        }
    }

    private class Skill2 extends Skill {
        private Skill2() {
            _castTime = 0.83;
        }

        protected void cast() {
            System.out.println("Skill2Test_kyaru");
        }
    }

    private class SkillEx extends Skill {
        private SkillEx() {
            _castTime = 0;
        }

        protected void cast() {
            System.out.println("ExTest_kyaru");
        }
    }
}
