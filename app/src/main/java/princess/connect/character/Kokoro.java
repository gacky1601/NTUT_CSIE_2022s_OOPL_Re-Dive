package princess.connect.character;

import java.util.Arrays;

import princess.connect.baseClass.Character;

public class Kokoro extends Character {
    public Kokoro() {
        _id = 1059;
        _name = "Kokoro";

        _hitpoints = 521;
        _physicalAttack = 619;
        _magicAttack = 0;
        _physicalDefense = 6;
        _magicDefense = 3;
        _physicalCritical = 10;
        _magicCritical = 0;
        _evasion = 1;
        _accuracy = 0;
        _hpRecoveryRate = 0;
        _tpRecoveryRate = 0;
        _waveHpRecovery = 0;
        _waveTpRecovery = 0;
        _hpReduceRate = 0;
        _tpReduceRate = 0;

        _attackRange = 500;
        _moveSpeed = 450;
        _attackSpeed = 2.34;
        _attackCastTime = 1.167;

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
            System.out.println("UBTest_kokoro");
        }
    }

    private class Skill1 extends Skill {
        private Skill1() {
            _castTime = 0.66;
        }

        protected void cast() {
            System.out.println("Skill1_kokoro");
        }
    }

    private class Skill2 extends Skill {
        private Skill2() {
            _castTime = 1.4;
        }

        protected void cast() {
            System.out.println("Skill2Test_kokoro");
        }
    }

    private class SkillEx extends Skill {
        private SkillEx() {
            _castTime = 0;
        }

        protected void cast() {
            System.out.println("ExTest_kokoro");
        }
    }
}
