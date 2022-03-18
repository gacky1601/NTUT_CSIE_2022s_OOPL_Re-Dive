package tw.edu.ntut.csie.game.state;

import android.util.Log;

import java.util.List;
import java.util.Map;

import tw.edu.ntut.csie.game.Game;
import tw.edu.ntut.csie.game.GameObject;
import tw.edu.ntut.csie.game.Pointer;
import tw.edu.ntut.csie.game.R;
import tw.edu.ntut.csie.game.core.Audio;
import tw.edu.ntut.csie.game.core.MovingBitmap;
import tw.edu.ntut.csie.game.engine.GameEngine;
import tw.edu.ntut.csie.game.extend.Animation;
import tw.edu.ntut.csie.game.extend.Integer;

public class StateRun extends GameState {

    public static final int DEFAULT_SCORE_DIGITS = 4;
    private MovingBitmap _background;
    private MovingBitmap _android;
    private MovingBitmap _cloud;
    private MovingBitmap toyz;
    private MovingBitmap apple;
    private Practice test;
    private int px,py;
    private int _cx, _cy;
    public StateRun(GameEngine engine) {
        super(engine);
    }

    @Override
    public void initialize(Map<String, Object> data) {
        test=new Practice();
        test.initialize();

        px=250;py=150;
        apple=new MovingBitmap(R.drawable.apple);
        apple.setLocation(250,50);
        _background = new MovingBitmap(R.drawable.background);
        _android = new MovingBitmap(R.drawable.android_green);
        _android.setLocation(100, 200);
        _cloud = new MovingBitmap(R.drawable.cloud);
        _cx = 10;_cy = 120;
        _cloud.setLocation(_cx, _cy);
        toyz = new MovingBitmap(R.drawable.toyz);
        toyz.setLocation(300, 200);

    }

    @Override
    public void move() {
        _cloud.setLocation(_cx, _cy);

        if(px<350){ px+=5; }
        else{px-=5;}
        toyz.setLocation(px,py);
    }

    @Override
    public void show() {
        _background.show();
        apple.show();
        _cloud.show();
        toyz.show();
        _android.show();
        test.show();
    }

    @Override
    public void release() {
        test.release();
    apple.release();
        _android.release();
        _cloud.release();
        toyz.release();
        test=null;
        _background = null;
apple=null;
        _android = null;
        _cloud = null;
        toyz = null;
    }

    @Override
    public void keyPressed(int keyCode) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyReleased(int keyCode) {
        // TODO Auto-generated method stub
    }

    @Override
    public void orientationChanged(float pitch, float azimuth, float roll) {
        if (pitch > 15 && pitch < 60 && _cx > 50)
            _cx -= 2;
        if (pitch < -15 && pitch > -60 && _cx + _cloud.getWidth() < 500)
            _cx += 2;
    }

    @Override
    public void accelerationChanged(float dX, float dY, float dZ) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean pointerPressed(Pointer actionPointer, List<Pointer> pointers) {
        _android.setLocation(actionPointer.getX(),actionPointer.getY());
        return true;
    }

    @Override
    public boolean pointerMoved(Pointer actionPointer, List<Pointer> pointers) {
        return false;
    }

    public void resizeAndroidIcon() {}

    @Override
    public boolean pointerReleased(Pointer actionPointer, List<Pointer> pointers) {
        return false;
    }

    @Override
    public void pause() { }

    @Override
    public void resume() {}
}
