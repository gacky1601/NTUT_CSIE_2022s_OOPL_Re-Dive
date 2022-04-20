package princess.connect.character;

import java.util.Arrays;

import princess.connect.baseClass.Character;

public class Kokoro extends Character {
    public Kokoro() {
        super();

        _id = 1059;
        _name = "Kokoro";

        _hp = 521;
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

        _level = 1;
        _star = 1;
        _rank = 1;

        _skills = Arrays.asList(new Attack(1.167), new Skill0(), new Skill1(), new Skill2(), new SkillEx());
        _initialPattern = Arrays.asList(SkillType.SKILL2, SkillType.SKILL1);
        _loopPattern = Arrays.asList(SkillType.ATTACK, SkillType.ATTACK, SkillType.SKILL2, SkillType.ATTACK,
                SkillType.SKILL1);

        _damageType = DamageType.PHYSICAL;
    }

    private class Skill0 extends Skill {
        private Skill0() {
            _skillTime = 3.633;
        }

        protected void cast() {
        }
    }

    private class Skill1 extends Skill {
        private Skill1() {
            _skillTime = 2.733;
            _castTime = 0.66;
        }

        protected void cast() {
        }
    }

    private class Skill2 extends Skill {
        private Skill2() {
            _skillTime = 2.000;
            _castTime = 1.4;
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
