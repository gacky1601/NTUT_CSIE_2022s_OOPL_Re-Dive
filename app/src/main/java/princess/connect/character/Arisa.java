package princess.connect.character;

import java.util.Arrays;

import princess.connect.baseClass.Character;

public class Arisa extends Character {
    private int _ubTime;

    public Arisa() {
        super();

        _id = 1063;
        _name = "Arisa";

        _hitpoints = 562;
        _physicalAttack = 405;
        _magicAttack = 0;
        _physicalDefense = 5;
        _magicDefense = 5;
        _physicalCritical = 30;
        _magicCritical = 0;
        _evasion = 0;
        _accuracy = 0;
        _hpRecoveryRate = 0;
        _tpRecoveryRate = 0;
        _waveHpRecovery = 0;
        _waveTpRecovery = 0;
        _hpReduceRate = 0;
        _tpReduceRate = 0;

        _attackRange = 625;
        _moveSpeed = 450;
        _attackSpeed = 1.97;

        _level = 1;
        _star = 3;
        _rank = 1;

        _skills = Arrays.asList(new Attack(1.50, 1.0), new Skill0(), new Skill1(), new Skill2(), new SkillEx());
        _initialPattern = Arrays.asList(SkillType.SKILL1, SkillType.SKILL2, SkillType.ATTACK, SkillType.SKILL2);
        _loopPattern = Arrays.asList(SkillType.ATTACK, SkillType.SKILL2, SkillType.SKILL1, SkillType.ATTACK,
                SkillType.SKILL2, SkillType.ATTACK, SkillType.SKILL2, SkillType.SKILL2);

        _damageType = DamageType.PHYSICAL;

        _ubTime = 0;
    }

    private class Skill0 extends Skill {
        private Skill0() {
            _skillTime = 4.70;
        }

        protected void cast() {
            _ubTime++;
        }
    }

    private class Skill1 extends Skill {
        private Character target;

        private Skill1() {
            _skillTime = 1.90;
            _castTime = 1.90;
        }

        protected void cast() {
            if (isCastTime(0))
                target = highestAttack(_enemies, DamageType.MAGIC);
            else if (isCastTime())
                inflictDamage(target, (int) (10 * (_level + 1) + _physicalAttack * 0.8));
        }
    }

    private class Skill2 extends Skill {
        private Skill2() {
            _skillTime = 2.50;
            _castTime = 0.51;
        }

        protected void cast() {
            if (isCastTime()) {
                if (_ubTime >= 1)
                    grantsRecoverTP(Arisa.this, (int) (200 + (_level * 2.0)), true);
                else
                    grantsRecoverTP(Arisa.this, (int) (125 + (_level * 1.25)), true);
            }
        }
    }

    private class SkillEx extends Skill {
        private SkillEx() {
        }

        protected void cast() {
        }
    }
}
