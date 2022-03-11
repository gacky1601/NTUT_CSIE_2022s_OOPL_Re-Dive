package tw.edu.ntut.csie.game.state;

import tw.edu.ntut.csie.game.GameObject;
import tw.edu.ntut.csie.game.R;
import tw.edu.ntut.csie.game.core.MovingBitmap;

public class Practice implements GameObject {
    private MovingBitmap mPractice;
    private int px, py;

    public Practice() {
        mPractice = new MovingBitmap(R.drawable.about);
        px = 250;
        py = 50;
    }

    public void initialize() {
        mPractice.setLocation(300, 50);
    }

    @Override
    public void show() {
        mPractice.show();
    }

    @Override
    public void release() {
        mPractice.release();
        mPractice = null;
    }

    @Override
    public void move() {
    }


}
