package princess.connect.baseClass;

import java.util.ArrayList;
import java.util.List;

import princess.connect.baseClass.Character.Direction;

public class BattleGround {
    static final int FRAME = 100;

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
        int[] y = { 150, 300, 0, 225, 75 };
        for (int i = 0, x = 400; i < _characterLeft.size(); i++, x -= 100) {
            _characterLeft.get(i)._x = x;
            _characterLeft.get(i)._y = y[i];
            _characterLeft.get(i)._direction = Direction.LEFT;
        }
        for (int i = 0, x = 2600; i < _characterRight.size(); i++, x += 100) {
            _characterRight.get(i)._x = x;
            _characterRight.get(i)._y = y[i];
            _characterRight.get(i)._direction = Direction.RIGHT;
        }
    }

    public void main() {
        while (_time != 0) {
            if (_time % (FRAME / 10) == 0) {
                System.out.println("\n" + _time);
                for (Character chara : _characterLeft)
                    System.out.println(chara._id + " : " + chara._x + " " + chara._y + " HP:" + chara._hitpoints);
                for (Character chara : _characterRight)
                    System.out.println(chara._id + " : " + chara._x + " " + chara._y + " HP:" + chara._hitpoints);
            }

            for (Character chara : _characterRight)
                chara.act(_characterRight, _characterLeft);
            for (Character chara : _characterLeft)
                chara.act(_characterLeft, _characterRight);

            _time--;
        }
    }
}
