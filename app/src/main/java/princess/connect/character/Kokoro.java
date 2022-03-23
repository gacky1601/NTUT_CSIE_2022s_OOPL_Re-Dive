package princess.connect.character;

import java.util.List;

import princess.connect.baseClass.Character;

public class Kokoro extends Character {
    public Kokoro() {

        _skills = List.of(new SkillUb(), new Skill1(), new Skill2(), new SkillEx());
        _initialPattern = List.of(2, 1);
        _loopPattern = List.of(0, 0, 2, 0, 1);
    }

    private class SkillUb extends Skill {
        private SkillUb() {
            _castTime = 0;
        }

        protected void cast() {
            System.out.println("UBTest_kokoro");
        }
    }

    private class Skill1 extends Skill {
        private Skill1() {
            _castTime = 0.66;
        }

        protected void cast() {
            System.out.println("Skill1_kokoro");
        }
    }

    private class Skill2 extends Skill {
        private Skill2() {
            _castTime = 1.4;
        }

        protected void cast() {
            System.out.println("Skill2Test_kokoro");
        }
    }

    private class SkillEx extends Skill {
        private SkillEx() {
            _castTime = 0;
        }

        protected void cast() {
            System.out.println("ExTest_kokoro");
        }
    }
}
