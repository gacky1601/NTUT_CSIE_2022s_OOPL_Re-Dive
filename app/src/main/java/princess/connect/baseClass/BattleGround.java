package princess.connect.baseClass;

import java.util.ArrayList;
import java.util.List;

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
        Character chara;
        for (int i = 0; i < _characterLeft.size(); i++) {
            chara = _characterLeft.get(i);
            chara._x = SPACING * (4 - i);
            chara._direction = Direction.LEFT;
            initCharacter(chara, i);
        }
        for (int i = 0; i < _characterRight.size(); i++) {
            chara = _characterRight.get(i);
            chara._x = WIDTH - SPACING * (4 - i);
            chara._direction = Direction.RIGHT;
            initCharacter(chara, i);
        }
    }

    public void main() {
        for (Character chara : _characterLeft)
            if (chara.isAlive())
                chara.act(_characterLeft, _characterRight);
        for (Character chara : _characterRight)
            if (chara.isAlive())
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
            if (chara.isAlive())
                isAliveLeft = true;
        for (Character chara : _characterRight)
            if (chara.isAlive())
                isAliveRight = true;
        return !(isAliveLeft && isAliveRight);
    }

    private void initCharacter(Character chara, int index) {
        int[] y = { 2, 4, 0, 3, 1 };
        chara._y = y[index] * HEIGHT / 4;
        chara._hp = chara._hitpoints;
        chara._tp = 0;
    }
}
