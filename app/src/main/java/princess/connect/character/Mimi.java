package princess.connect.character;

import java.util.Arrays;

import princess.connect.baseClass.Character;

public class Mimi extends Character {
    public Mimi() {
        super();

        _id = 1020;
        _name = "Mimi";

        _hitpoints = 622;
        _physicalAttack = 583;
        _magicAttack = 0;
        _physicalDefense = 7;
        _magicDefense = 4;
        _physicalCritical = 20;
        _magicCritical = 0;
        _evasion = 0;
        _accuracy = 0;
        _hpRecoveryRate = 0;
        _tpRecoveryRate = 0;
        _waveHpRecovery = 0;
        _waveTpRecovery = 0;
        _hpReduceRate = 0;
        _tpReduceRate = 0;

        _attackRange = 360;
        _moveSpeed = 450;
        _attackSpeed = 2.125;

        _level = 1;
        _star = 2;
        _rank = 1;

        _skills = Arrays.asList(new Attack(1.133), new Skill0(), new Skill1(), new Skill2(), new SkillEx());
        _initialPattern = Arrays.asList(SkillType.SKILL2, SkillType.SKILL1);
        _loopPattern = Arrays.asList(SkillType.ATTACK, SkillType.ATTACK, SkillType.SKILL2, SkillType.ATTACK,
                SkillType.SKILL1);

        _damageType = DamageType.PHYSICAL;
    }

    private class Skill0 extends Skill {
        private Skill0() {
            _skillTime = 3.233;
        }

        protected void cast() {
        }
    }

    private class Skill1 extends Skill {
        private Skill1() {
            _skillTime = 2.400;
            _castTime = 0.85;
        }

        protected void cast() {
        }
    }

    private class Skill2 extends Skill {
        private Skill2() {
            _skillTime = 2.833;
            _castTime = 0.42;
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