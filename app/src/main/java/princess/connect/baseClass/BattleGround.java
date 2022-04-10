package princess.connect.baseClass;

import java.util.ArrayList;
import java.util.List;

import princess.connect.baseClass.Character.Direction;

public class BattleGround {
    public static final int FRAME = 100;
    public static final int WIDTH = 3000;
    public static final int HEIGHT = 300;
    public static final int SPACING = 100;

    private int _time;
    private List<Character> _characterLeft;
    private List<Character> _characterRight;

    public BattleGround(List<Character> left, List<Character> right) {
        _characterLeft = new ArrayList<>(left);
        _characterRight = new ArrayList<>(right);
    }

    public void release() {
        _characterLeft.clear();
        _characterRight.clear();
        _characterLeft = null;
        _characterRight = null;
    }

    public void initialize() {
        _time = 90 * FRAME;
        int[] y = { 2, 4, 0, 3, 1};
        for (int i = 0; i < _characterLeft.size(); i++) {
            _characterLeft.get(i)._x = SPACING * (4 - i);
            _characterLeft.get(i)._y = y[i] * HEIGHT / 4;
            _characterLeft.get(i)._direction = Direction.LEFT;
        }
        for (int i = 0; i < _characterRight.size(); i++) {
            _characterRight.get(i)._x = WIDTH - SPACING * (4 - i);
            _characterRight.get(i)._y =  y[i] * HEIGHT / 4;
            _characterRight.get(i)._direction = Direction.RIGHT;
        }
    }

    public void main() {
        if (_time % (FRAME / 1) == 0) {
            System.out.println("\nTime: " + _time);
            for (Character chara : _characterLeft)
                System.out.println(chara.name() + " : " + chara.x() + " " + chara.y() + " HP:" + chara._hitpoints
                        + " " + chara.preAction() + " " + chara.action() + " ");
            for (Character chara : _characterRight)
                System.out.println(chara.name() + " : " + chara.x() + " " + chara.y() + " HP:" + chara._hitpoints
                        + " " + chara.preAction() + " " + chara.action() + " ");
        }
        if (_characterLeft.get(0)._hitpoints < 0 && _characterRight.get(0)._hitpoints < 0)
            _time = 0;

        for (Character chara : _characterRight)
            chara.act(_characterRight, _characterLeft);
        for (Character chara : _characterLeft)
            chara.act(_characterLeft, _characterRight);

        _time--;
    }

    public int time() {
        return _time;
    }

    public List<Character> character(Direction direction) {
        switch (direction) {
            case LEFT:
                return _characterLeft;
            case RIGHT:
                return _characterRight;
        }
        return null;
    }
}
