package princess.connect.baseClass;

import java.util.List;

public class Area {
    protected int _mapX;
    protected int _mapY;
    protected List<AreaLevel> _levels;

    public int mapX() {
        return _mapX;
    }

    public int mapY() {
        return _mapY;
    }

    public List<AreaLevel> levels() {
        return _levels;
    }

    public class AreaLevel {
        public static final int WIDTH = 200, HEIGHT = 100;
        protected int _icon, _map, _x, _y;
        protected List<Character> _chars;

        public int icon() {
            return _icon;
        }

        public int map() {
            return _map;
        }

        public int x() {
            return _x;
        }

        public int y() {
            return _y;
        }

        public List<Character> chars() {
            return _chars;
        }
    }
}
