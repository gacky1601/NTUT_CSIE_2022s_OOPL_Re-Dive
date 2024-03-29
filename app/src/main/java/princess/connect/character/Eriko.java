package princess.connect.character;

import java.util.Arrays;

import princess.connect.baseClass.Character;

public class Eriko extends Character {
    public Eriko() {
        super();

        _id = 1027;
        _name = "Eriko";

        _hitpoints = 560;
        _physicalAttack = 605;
        _magicAttack = 0;
        _physicalDefense = 6;
        _magicDefense = 4;
        _physicalCritical = 15;
        _magicCritical = 0;
        _evasion = 0;
        _accuracy = 0;
        _hpRecoveryRate = 0;
        _tpRecoveryRate = 0;
        _waveHpRecovery = 0;
        _waveTpRecovery = 0;
        _hpReduceRate = 0;
        _tpReduceRate = 0;

        _attackRange = 230;
        _moveSpeed = 450;
        _attackSpeed = 1.425;

        _level = 1;
        _star = 2;
        _rank = 1;

        _skills = Arrays.asList(new Attack(1.533), new Skill0(), new Skill1(), new Skill2(), new SkillEx());
        _initialPattern = Arrays.asList(SkillType.SKILL1, SkillType.SKILL2);
        _loopPattern = Arrays.asList(SkillType.ATTACK, SkillType.SKILL2, SkillType.ATTACK, SkillType.SKILL2,
                SkillType.ATTACK, SkillType.SKILL1);

        _damageType = DamageType.PHYSICAL;
    }

    private class Skill0 extends Skill {
        private Skill0() {
            _skillTime = 4.733;
        }

        protected void cast() {
        }
    }

    private class Skill1 extends Skill {
        private Skill1() {
            _skillTime = 2.000;
            _castTime = 1.125;
        }

        protected void cast() {
        }
    }

    private class Skill2 extends Skill {
        private Skill2() {
            _skillTime = 2.000;
            _castTime = 1.125;
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