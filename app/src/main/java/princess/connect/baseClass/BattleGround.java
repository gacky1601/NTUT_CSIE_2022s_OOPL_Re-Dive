package princess.connect.baseClass;

import java.util.ArrayList;
import java.util.List;

import princess.connect.baseClass.Character.Action;
import princess.connect.baseClass.Character.Direction;

public class BattleGround {
    public static final int FRAME = 100;
    public static final int WIDTH = 3000;
    public static final int HEIGHT = 100;
    public static final int SPACING = 100;

    private int _time;
    private List<Character> _characterLeft;
    private List<Character> _characterRight;

    public BattleGround(List<Character> left, List<Character> right) {
        _characterLeft = new ArrayList<>(left);
        _characterRight = new ArrayList<>(right);
    }

    public void release() {
        for (Character chara : _characterLeft)
            chara.release();
        for (Character chara : _characterRight)
            chara.release();
        _characterLeft.clear();
        _characterRight.clear();
        _characterLeft = null;
        _characterRight = null;
    }

    public void initialize() {
        _time = 90 * FRAME;
        int[] y = { 2, 4, 0, 3, 1 };
        for (int i = 0; i < _characterLeft.size(); i++) {
            _characterLeft.get(i)._x = SPACING * (4 - i);
            _characterLeft.get(i)._y = y[i] * HEIGHT / 4;
            _characterLeft.get(i)._direction = Direction.LEFT;
        }
        for (int i = 0; i < _characterRight.size(); i++) {
            _characterRight.get(i)._x = WIDTH - SPACING * (4 - i);
            _characterRight.get(i)._y = y[i] * HEIGHT / 4;
            _characterRight.get(i)._direction = Direction.RIGHT;
        }
    }

    public void main() {
        for (Character chara : _characterLeft)
            if (chara.action() != Action.DIE)
                chara.act(_characterLeft, _characterRight);
        for (Character chara : _characterRight)
            if (chara.action() != Action.DIE)
                chara.act(_characterRight, _characterLeft);

        _time--;
        if (isEnd())
            _time = 0;
    }

    public int time() {
        return _time;
    }

    public List<Character> characters() {
        List<Character> chars = new ArrayList<>();
        chars.addAll(_characterLeft);
        chars.addAll(_characterRight);
        return chars;
    }

    public boolean isEnd() {
        boolean isAliveRight = false, isAliveLeft = false;
        for (Character chara : _characterLeft)
            if (chara.action() != Action.DIE)
                isAliveLeft = true;
        for (Character chara : _characterRight)
            if (chara.action() != Action.DIE)
                isAliveRight = true;
        return !(isAliveLeft && isAliveRight);
    }
}
