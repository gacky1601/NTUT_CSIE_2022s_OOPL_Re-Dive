package princess.connect.baseClass;

import java.util.ArrayList;
import java.util.List;

import princess.connect.baseClass.Character.Action;
import princess.connect.baseClass.Character.Direction;

public class BattleGround {
    public static final int FRAME = 100, WIDTH = 3000, HEIGHT = 100, SPACING = 100;

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
        int[] y = { 2, 4, 0, 3, 1 };
        Character chara;
        for (int i = 0; i < _characterLeft.size(); i++) {
            chara = _characterLeft.get(i);
            chara._x = SPACING * (4 - i);
            chara._direction = Direction.LEFT;
            chara._y = y[i] * HEIGHT / 4;
            chara.initialize();
        }
        for (int i = 0; i < _characterRight.size(); i++) {
            chara = _characterRight.get(i);
            chara._x = WIDTH - SPACING * (4 - i);
            chara._direction = Direction.RIGHT;
            chara._y = y[i] * HEIGHT / 4;
            chara.initialize();
        }
    }

    public void main() {
        for (Character chara : _characterLeft)
            if (chara.isAlive())
                chara.act(_characterLeft, _characterRight);
        for (Character chara : _characterRight)
            if (chara.isAlive())
                chara.act(_characterRight, _characterLeft);
        if (!isEnd())
            _time--;
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
        if (_time == 0)
            return true;
        boolean isAliveLeft = false, isAliveRight = false;
        for (Character chara : _characterLeft)
            if (chara.isAlive())
                isAliveLeft = true;
        for (Character chara : _characterRight)
            if (chara.isAlive())
                isAliveRight = true;
        return !(isAliveLeft && isAliveRight);
    }

    public boolean isIdle() {
        boolean isIdle = true;
        for (Character chara : _characterLeft)
            if (!(chara.action() == Action.IDLE || chara.action() == Action.DIE))
                isIdle = false;
        for (Character chara : _characterRight)
            if (!(chara.action() == Action.IDLE || chara.action() == Action.DIE))
                isIdle = false;
        return isIdle;
    }

    public boolean isWin() {
        if (!isEnd())
            return false;
        boolean isAliveRight = false;
        for (Character chara : _characterRight)
            if (chara.isAlive())
                isAliveRight = true;
        return !isAliveRight;
    }
}
