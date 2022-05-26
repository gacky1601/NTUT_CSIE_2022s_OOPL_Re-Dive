package princess.connect.character;

import java.util.Arrays;

import princess.connect.baseClass.Character;

public class Pecorine extends Character {
    public Pecorine() {
        super();

        _id = 1058;
        _name = "Pecorine";

        _hitpoints = 2991;
        _physicalAttack = 503;
        _magicAttack = 0;
        _physicalDefense = 7;
        _magicDefense = 3;
        _physicalCritical = 10;
        _magicCritical = 0;
        _evasion = 0;
        _accuracy = 0;
        _hpRecoveryRate = 0;
        _tpRecoveryRate = 0;
        _waveHpRecovery = 0;
        _waveTpRecovery = 0;
        _hpReduceRate = 0;
        _tpReduceRate = 0;

        _attackRange = 155;
        _moveSpeed = 450;
        _attackSpeed = 2.250;

        _level = 1;
        _star = 1;
        _rank = 1;

        _skills = Arrays.asList(new Attack(1.133), new Skill0(), new Skill1(), new Skill2(), new SkillEx());
        _initialPattern = Arrays.asList(SkillType.SKILL2, SkillType.SKILL1);
        _loopPattern = Arrays.asList(SkillType.ATTACK, SkillType.ATTACK, SkillType.SKILL2, SkillType.ATTACK,
                SkillType.SKILL1);

        _damageType = DamageType.PHYSICAL;
    }

    private class Skill0 extends Skill {
        private Skill0() {
            _skillTime = 5.333;
        }

        protected void cast() {
        }
    }

    private class Skill1 extends Skill {
        private Skill1() {
            _skillTime = 3.033;
            _castTime = 0.210;
        }

        protected void cast() {
            if (isCastTime(1.550)) {
            } else if (isCastTime(1.750))
                grantsRecoverHP(Pecorine.this, 5 * (_level + 1) + _physicalAttack, true);
        }
    }

    private class Skill2 extends Skill {
        private Character target;

        private Skill2() {
            _skillTime = 2.033;
            _castTime = 1.210;
        }

        protected void cast() {
            if (isCastTime(0))
                target = frontmost(_enemies);
            else if (isCastTime())
                inflictDamage(target, (int) (20 * (_level + 1) + (_physicalAttack * 1.6)));
        }
    }

    private class SkillEx extends Skill {
        private SkillEx() {
        }

        protected void cast() {
        }
    }
}
