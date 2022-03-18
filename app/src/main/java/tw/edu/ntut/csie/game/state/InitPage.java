package tw.edu.ntut.csie.game.state;

import java.util.List;
import java.util.Map;

import tw.edu.ntut.csie.game.Pointer;
import tw.edu.ntut.csie.game.R;
import tw.edu.ntut.csie.game.core.MovingBitmap;
import tw.edu.ntut.csie.game.engine.GameEngine;

public class InitPage extends GameState{
    private MovingBitmap _background;
    /**
     * 建構一個<code>GameState</code>實體。
     *
     * @param engine 執行狀態處理者的引擎
     */
    protected InitPage(GameEngine engine) {
        super(engine);
    }

    @Override
    public void initialize(Map<String, Object> data) {
        _background = new MovingBitmap(R.drawable.background);

    }

    @Override
    public void move() {

    }

    @Override
    public void show() {

    }

    @Override
    public void keyPressed(int keyCode) {

    }

    @Override
    public void keyReleased(int keyCode) {

    }

    @Override
    public boolean pointerPressed(Pointer actionPointer, List<Pointer> pointers) {
        return false;
    }

    @Override
    public boolean pointerMoved(Pointer actionPointer, List<Pointer> pointers) {
        return false;
    }

    @Override
    public boolean pointerReleased(Pointer actionPointer, List<Pointer> pointers) {
        return false;
    }

    @Override
    public void release() {

    }

    @Override
    public void orientationChanged(float pitch, float azimuth, float roll) {

    }

    @Override
    public void accelerationChanged(float dX, float dY, float dZ) {

    }



    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
