package princess.connect.character;

import java.util.Arrays;

import princess.connect.baseClass.Character;

public class Shiori extends Character {
    public Shiori() {
        super();

        _id = 1038;
        _name = "Shiori";

        _hitpoints = 455;
        _physicalAttack = 503;
        _magicAttack = 0;
        _physicalDefense = 4;
        _magicDefense = 4;
        _physicalCritical = 40;
        _magicCritical = 0;
        _evasion = 1;
        _accuracy = 0;
        _hpRecoveryRate = 0;
        _tpRecoveryRate = 0;
        _waveHpRecovery = 0;
        _waveTpRecovery = 0;
        _hpReduceRate = 0;
        _tpReduceRate = 0;

        _attackRange = 710;
        _moveSpeed = 450;
        _attackSpeed = 1.970;

        _level = 1;
        _star = 2;
        _rank = 1;

        _skills = Arrays.asList(new Attack(1.500, 1.000), new Skill0(), new Skill1(), new Skill2(), new SkillEx());
        _initialPattern = Arrays.asList(SkillType.SKILL1, SkillType.SKILL2);
        _loopPattern = Arrays.asList(SkillType.ATTACK, SkillType.ATTACK, SkillType.SKILL1, SkillType.ATTACK,
                SkillType.SKILL2);

        _damageType = DamageType.PHYSICAL;
    }

    private class Skill0 extends Skill {
        private Skill0() {
            _skillTime = 4.633;
        }

        protected void cast() {
        }
    }

    private class Skill1 extends Skill {
        private Skill1() {
            _skillTime = 2.633;
            _castTime = 0;
        }

        protected void cast() {
        }
    }

    private class Skill2 extends Skill {
        private Skill2() {
            _skillTime = 1.900;
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