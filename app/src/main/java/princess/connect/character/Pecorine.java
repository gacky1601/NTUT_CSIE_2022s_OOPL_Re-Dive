package princess.connect.character;

import java.util.Arrays;
import java.util.List;

import princess.connect.baseClass.Character;

public class Pecorine extends Character {
    public Pecorine() {
        _id = 1058;
        _name = "Pecorine";
        
        _hp = 2991;
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
        _attackSpeed = 2.25;
        _attackTime = 1.133;

        _level = 1;
        _star = 1;
        _rank = 1;

        _skills = Arrays.asList(new Skill0(), new Skill1(), new Skill2(), new SkillEx());
        _initialPattern = Arrays.asList(SkillType.SKILL2, SkillType.SKILL1);
        _loopPattern = Arrays.asList(SkillType.ATTACK, SkillType.ATTACK, SkillType.SKILL2, SkillType.ATTACK, SkillType.SKILL1);

        _damageType = DamageType.PHYSICAL;
    }

    private class Skill0 extends Skill {
        private Skill0() {
            _skillTime = 5.333;
        }

        protected void cast(List<Character> allies, List<Character> enemies) {
            System.out.println("UBTest_pekorine");
        }
    }

    private class Skill1 extends Skill {
        private Skill1() {
            _skillTime = 3.033;
            _castTime = 0.21;
        }

        protected void cast(List<Character> allies, List<Character> enemies) {
            System.out.println("Skill1Test_pekorine");
        }
    }

    private class Skill2 extends Skill {
        private Skill2() {
            _skillTime = 2.033;
            _castTime = 1.21;
        }

        protected void cast(List<Character> allies, List<Character> enemies) {
            System.out.println("Skill2Test_pekorine");
        }
    }

    private class SkillEx extends Skill {
        private SkillEx() {
        }

        protected void cast(List<Character> allies, List<Character> enemies) {
            System.out.println("ExTest_pekorine");
        }
    }
}
