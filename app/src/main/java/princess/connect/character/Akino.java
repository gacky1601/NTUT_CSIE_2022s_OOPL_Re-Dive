package princess.connect.character;

import java.util.Arrays;

import princess.connect.baseClass.Character;

public class Akino extends Character {
    public Akino() {
        super();

        _id = 1032;
        _name = "Akino";

        _hitpoints = 773;
        _physicalAttack = 595;
        _magicAttack = 0;
        _physicalDefense = 8;
        _magicDefense = 5;
        _physicalCritical = 25;
        _magicCritical = 0;
        _evasion = 0;
        _accuracy = 0;
        _hpRecoveryRate = 0;
        _tpRecoveryRate = 0;
        _waveHpRecovery = 0;
        _waveTpRecovery = 0;
        _hpReduceRate = 0;
        _tpReduceRate = 0;

        _attackRange = 180;
        _moveSpeed = 450;
        _attackSpeed = 2.125;

        _level = 1;
        _star = 3;
        _rank = 1;

        _skills = Arrays.asList(new Attack(1.133), new Skill0(), new Skill1(), new Skill2(), new SkillEx());
        _initialPattern = Arrays.asList(SkillType.SKILL1, SkillType.SKILL2);
        _loopPattern = Arrays.asList(SkillType.ATTACK, SkillType.ATTACK, SkillType.SKILL1, SkillType.ATTACK,
                SkillType.SKILL2);

        _damageType = DamageType.PHYSICAL;
    }

    private class Skill0 extends Skill {
        private Skill0() {
            _skillTime = 6.300;
        }

        protected void cast() {
        }
    }

    private class Skill1 extends Skill {
        private Skill1() {
            _skillTime = 2.067;
            _castTime = 1.358;
        }

        protected void cast() {
        }
    }

    private class Skill2 extends Skill {
        private Skill2() {
            _skillTime = 3.133;
            _castTime = 1.558;
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