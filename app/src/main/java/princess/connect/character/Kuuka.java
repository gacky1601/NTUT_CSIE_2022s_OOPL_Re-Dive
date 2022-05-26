package princess.connect.character;

import java.util.Arrays;

import princess.connect.baseClass.Character;

public class Kuuka extends Character {
    public Kuuka() {
        super();

        _id = 1045;
        _name = "Kuuka";

        _hitpoints = 826;
        _physicalAttack = 507;
        _magicAttack = 0;
        _physicalDefense = 5;
        _magicDefense = 6;
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

        _attackRange = 130;
        _moveSpeed = 450;
        _attackSpeed = 2.375;

        _level = 1;
        _star = 2;
        _rank = 1;

        _skills = Arrays.asList(new Attack(1.067), new Skill0(), new Skill1(), new Skill2(), new SkillEx());
        _initialPattern = Arrays.asList(SkillType.SKILL1, SkillType.SKILL2);
        _loopPattern = Arrays.asList(SkillType.ATTACK, SkillType.SKILL1, SkillType.SKILL2, SkillType.ATTACK,
                SkillType.ATTACK, SkillType.SKILL1, SkillType.SKILL2);

        _damageType = DamageType.PHYSICAL;
    }

    private class Skill0 extends Skill {
        private Skill0() {
            _skillTime = 4.167;
        }

        protected void cast() {
        }
    }

    private class Skill1 extends Skill {
        private Skill1() {
            _skillTime = 3.000;
            _castTime = 0.375;
        }

        protected void cast() {
        }
    }

    private class Skill2 extends Skill {
        private Skill2() {
            _skillTime = 2.000;
            _castTime = 1.375;
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