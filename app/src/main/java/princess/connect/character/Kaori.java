package princess.connect.character;

import java.util.Arrays;

import princess.connect.baseClass.Character;

public class Kaori extends Character {
    public Kaori() {
        super();

        _id = 1017;
        _name = "Kaori";

        _hitpoints = 593;
        _physicalAttack = 588;
        _magicAttack = 0;
        _physicalDefense = 6;
        _magicDefense = 3;
        _physicalCritical = 25;
        _magicCritical = 0;
        _evasion = 1;
        _accuracy = 0;
        _hpRecoveryRate = 0;
        _tpRecoveryRate = 0;
        _waveHpRecovery = 0;
        _waveTpRecovery = 0;
        _hpReduceRate = 0;
        _tpReduceRate = 0;

        _attackRange = 145;
        _moveSpeed = 450;
        _attackSpeed = 2.170;

        _level = 1;
        _star = 2;
        _rank = 1;

        _skills = Arrays.asList(new Attack(1.033), new Skill0(), new Skill1(), new Skill2(), new SkillEx());
        _initialPattern = Arrays.asList(SkillType.SKILL2, SkillType.SKILL1);
        _loopPattern = Arrays.asList(SkillType.ATTACK, SkillType.SKILL1, SkillType.ATTACK, SkillType.ATTACK,
                SkillType.SKILL1);

        _damageType = DamageType.PHYSICAL;
    }

    private class Skill0 extends Skill {
        private Skill0() {
            _skillTime = 4.033;
        }

        protected void cast() {
        }
    }

    private class Skill1 extends Skill {
        private Skill1() {
            _skillTime = 2.000;
            _castTime = 0;
        }

        protected void cast() {
        }
    }

    private class Skill2 extends Skill {
        private Skill2() {
            _skillTime = 2.000;
            _castTime = 0;
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