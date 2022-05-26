package princess.connect.character;

import java.util.Arrays;

import princess.connect.baseClass.Character;

public class Djeeta extends Character {
    public Djeeta() {
        super();

        _id = 1057;
        _name = "Djeeta";

        _hitpoints = 735;
        _physicalAttack = 351;
        _magicAttack = 0;
        _physicalDefense = 7;
        _magicDefense = 4;
        _physicalCritical = 50;
        _magicCritical = 0;
        _evasion = 0;
        _accuracy = 0;
        _hpRecoveryRate = 0;
        _tpRecoveryRate = 0;
        _waveHpRecovery = 0;
        _waveTpRecovery = 0;
        _hpReduceRate = 0;
        _tpReduceRate = 0;

        _attackRange = 245;
        _moveSpeed = 450;
        _attackSpeed = 1.965;

        _level = 1;
        _star = 3;
        _rank = 1;

        _skills = Arrays.asList(new Attack(1.167), new Skill0(), new Skill1(), new Skill2(), new SkillEx());
        _initialPattern = Arrays.asList(SkillType.SKILL1, SkillType.SKILL2);
        _loopPattern = Arrays.asList(SkillType.ATTACK, SkillType.ATTACK, SkillType.SKILL1, SkillType.ATTACK,
                SkillType.SKILL2);

        _damageType = DamageType.PHYSICAL;
    }

    private class Skill0 extends Skill {
        private Skill0() {
            _skillTime = 4.767;
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