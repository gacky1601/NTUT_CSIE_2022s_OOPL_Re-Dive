package princess.connect.character;

import java.util.Arrays;

import princess.connect.baseClass.Character;

public class Miyako extends Character {
    public Miyako() {
        super();

        _id = 1007;
        _name = "Miyako";

        _hitpoints = 842;
        _physicalAttack = 548;
        _magicAttack = 0;
        _physicalDefense = 7;
        _magicDefense = 3;
        _physicalCritical = 5;
        _magicCritical = 0;
        _evasion = 1;
        _accuracy = 0;
        _hpRecoveryRate = 0;
        _tpRecoveryRate = 0;
        _waveHpRecovery = 0;
        _waveTpRecovery = 0;
        _hpReduceRate = 0;
        _tpReduceRate = 0;

        _attackRange = 125;
        _moveSpeed = 450;
        _attackSpeed = 1.700;

        _level = 1;
        _star = 2;
        _rank = 1;

        _skills = Arrays.asList(new Attack(1.533), new Skill0(), new Skill1(), new Skill2(), new SkillEx());
        _initialPattern = Arrays.asList(SkillType.ATTACK, SkillType.ATTACK, SkillType.SKILL1, SkillType.ATTACK,
                SkillType.SKILL2);
        _loopPattern = Arrays.asList(SkillType.ATTACK, SkillType.ATTACK, SkillType.SKILL1, SkillType.SKILL2);

        _damageType = DamageType.PHYSICAL;
    }

    private class Skill0 extends Skill {
        private Skill0() {
            _skillTime = 6.500;
        }

        protected void cast() {
        }
    }

    private class Skill1 extends Skill {
        private Skill1() {
            _skillTime = 4.066;
            _castTime = 0;
        }

        protected void cast() {
        }
    }

    private class Skill2 extends Skill {
        private Skill2() {
            _skillTime = 3.133;
            _castTime = 0.270;
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