package princess.connect.character;

import java.util.Arrays;

import princess.connect.baseClass.Character;

public class Anna extends Character {
    public Anna() {
        super();

        _id = 1009;
        _name = "Anna";

        _hitpoints = 652;
        _physicalAttack = 0;
        _magicAttack = 605;
        _physicalDefense = 4;
        _magicDefense = 6;
        _physicalCritical = 0;
        _magicCritical = 10;
        _evasion = 0;
        _accuracy = 0;
        _hpRecoveryRate = 0;
        _tpRecoveryRate = 0;
        _waveHpRecovery = 0;
        _waveTpRecovery = 0;
        _hpReduceRate = 0;
        _tpReduceRate = 0;

        _attackRange = 440;
        _moveSpeed = 450;
        _attackSpeed = 2.250;

        _level = 1;
        _star = 3;
        _rank = 1;

        _skills = Arrays.asList(new Attack(1.133), new Skill0(), new Skill1(), new Skill2(), new SkillEx());
        _initialPattern = Arrays.asList(SkillType.SKILL1, SkillType.ATTACK);
        _loopPattern = Arrays.asList(SkillType.SKILL1, SkillType.ATTACK);

        _damageType = DamageType.MAGIC;
    }

    private class Skill0 extends Skill {
        private Skill0() {
            _skillTime = 5.133;
        }

        protected void cast() {
        }
    }

    private class Skill1 extends Skill {
        private Skill1() {
            _skillTime = 1.400;
            _castTime = 1.580;
        }

        protected void cast() {
        }
    }

    private class Skill2 extends Skill {
        private Skill2() {
            _skillTime = 3.567;
            _castTime = 1.250;
        }

        protected void cast() {
        }
    }

    private class SkillEx extends Skill {
        private SkillEx() {
        }

        protected void cast() {
        }
    }
}