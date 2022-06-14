package princess.connect.baseClass;

import java.util.ArrayList;
import java.util.List;

public class Gacha {
    final double THREE_STAR_RATE = 2.5, TWO_STAR_RATE = 18, ONE_STAR_RATE = 79.5;

    private List<Character> _3starChars, _2starChars, _1starChars;

    public Gacha(List<Character> chars) {
        _3starChars = new ArrayList<>();
        _2starChars = new ArrayList<>();
        _1starChars = new ArrayList<>();

        for (Character chara : chars) {
            if (chara.star() == 3)
                _3starChars.add(chara);
            else if (chara.star() == 2)
                _2starChars.add(chara);
            else
                _1starChars.add(chara);
        }
    }

    public Character draw1() {
        return draw1(THREE_STAR_RATE, TWO_STAR_RATE);
    }

    public Character draw1(double threeStarRate, double twoStarRate) {
        double rand = Math.random();
        if (rand < threeStarRate / 100.0)
            return _3starChars.get((int) (Math.random() * _3starChars.size()));
        else if (rand < (threeStarRate + twoStarRate) / 100.0)
            return _2starChars.get((int) (Math.random() * _2starChars.size()));
        else
            return _1starChars.get((int) (Math.random() * _1starChars.size()));
    }

    public List<Character> draw10() {
        List<Character> chars = new ArrayList<>();
        for (int i = 0; i < 9; i++)
            chars.add(draw1());
        chars.add(draw1(THREE_STAR_RATE, TWO_STAR_RATE + ONE_STAR_RATE));
        return chars;
    }
}
