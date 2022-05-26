package princess.connect.area;

import java.util.Arrays;

import princess.connect.baseClass.Area;
import princess.connect.character.*;

public class Area1 extends Area {
    public Area1() {
        _mapX = 2660;
        _mapY = 2570;
        _levels = Arrays.asList(new Level1(), new Level2(), new Level3(), new Level4(), new Level5(), new Level6(),
                new Level7(), new Level8(), new Level9(), new Level10());
    }

    private class Level1 extends AreaLevel {
        public Level1() {
            _icon = 100050;
            _map = 100021;
            _x = 10;
            _y = 55;
            _chars = Arrays.asList(new Suzuna(), new Io(), new Misaki());
        }
    }

    private class Level2 extends AreaLevel {
        public Level2() {
            _icon = 100010;
            _map = 100061;
            _x = 42;
            _y = 42;
            _chars = Arrays.asList(new Hiyori(), new Rei(), new Yui());
        }
    }

    private class Level3 extends AreaLevel {
        public Level3() {
            _icon = 100010;
            _map = 100067;
            _x = 63;
            _y = 77;
            _chars = Arrays.asList(new Eriko(), new Anna(), new Mitsuki());
        }
    }

    private class Level4 extends AreaLevel {
        public Level4() {
            _icon = 100050;
            _map = 100111;
            _x = 78;
            _y = 42;
            _chars = Arrays.asList(new Kaori(), new Makoto(), new Maho());
        }
    }

    private class Level5 extends AreaLevel {
        public Level5() {
            _icon = 100010;
            _map = 100071;
            _x = 104;
            _y = 60;
            _chars = Arrays.asList(new Kurumi(), new Saren(), new Suzume());
        }
    }

    private class Level6 extends AreaLevel {
        public Level6() {
            _icon = 100050;
            _map = 100111;
            _x = 120;
            _y = 92;
            _chars = Arrays.asList(new Rima(), new Mahiru(), new Shiori());
        }
    }

    private class Level7 extends AreaLevel {
        public Level7() {
            _icon = 100050;
            _map = 100111;
            _x = 136;
            _y = 64;
            _chars = Arrays.asList(new Pecorine(), new Kokoro(), new Kyaru());
        }
    }

    private class Level8 extends AreaLevel {
        public Level8() {
            _icon = 100010;
            _map = 100077;
            _x = 140;
            _y = 30;
            _chars = Arrays.asList(new Akino(), new Tamaki(), new Yukari(), new Mifuyu());
        }
    }

    private class Level9 extends AreaLevel {
        public Level9() {
            _icon = 100020;
            _map = 100014;
            _x = 172;
            _y = 43;
            _chars = Arrays.asList(new Kuuka(), new Monika(), new Ninon(), new Yuki());
        }
    }

    private class Level10 extends AreaLevel {
        public Level10() {
            _icon = 100020;
            _map = 100357;
            _x = 190;
            _y = 83;
            _chars = Arrays.asList(new Miyako(), new Shinobu(), new Akari(), new Yori());
        }
    }
}
