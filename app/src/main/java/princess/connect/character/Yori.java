package princess.connect.character;

import java.util.Arrays;

import princess.connect.baseClass.Character;

public class Yori extends Character {
    public Yori() {
        super();

        _id = 1022;
        _name = "Yori";

        _hitpoints = 511;
        _physicalAttack = 0;
        _magicAttack = 474;
        _physicalDefense = 3;
        _magicDefense = 5;
        _physicalCritical = 0;
        _magicCritical = 0;
        _evasion = 0;
        _accuracy = 0;
        _hpRecoveryRate = 0;
        _tpRecoveryRate = 0;
        _waveHpRecovery = 0;
        _waveTpRecovery = 0;
        _hpReduceRate = 0;
        _tpReduceRate = 0;

        _attackRange = 575;
        _moveSpeed = 450;
        _attackSpeed = 2.190;

        _level = 1;
        _star = 1;
        _rank = 1;

        _skills = Arrays.asList(new Attack(1.167), new Skill0(), new Skill1(), new Skill2(), new SkillEx());
        _initialPattern = Arrays.asList(SkillType.SKILL1, SkillType.SKILL2);
        _loopPattern = Arrays.asList(SkillType.ATTACK, SkillType.ATTACK, SkillType.SKILL1, SkillType.ATTACK,
                SkillType.SKILL2);

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
            _skillTime = 2.000;
            _castTime = 1.250;
        }

        protected void cast() {
        }
    }

    private class Skill2 extends Skill {
        private Skill2() {
            _skillTime = 2.267;
            _castTime = 0.980;
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