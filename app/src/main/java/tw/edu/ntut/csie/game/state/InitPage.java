package tw.edu.ntut.csie.game.state;

import java.util.List;
import java.util.Map;

import tw.edu.ntut.csie.game.Game;
import tw.edu.ntut.csie.game.Pointer;
import tw.edu.ntut.csie.game.R;
import tw.edu.ntut.csie.game.core.MovingBitmap;
import tw.edu.ntut.csie.game.engine.GameEngine;
import tw.edu.ntut.csie.game.extend.BitmapButton;
import tw.edu.ntut.csie.game.extend.ButtonEventHandler;

public class InitPage extends AbstractGameState{

    public InitPage(GameEngine engine) {
        super(engine);
    }

    private MovingBitmap _background;
    private BitmapButton _startButton;


    @Override
    public void initialize(Map<String, Object> data) {
        addGameObject(_background = new MovingBitmap(R.drawable.background));
        initializeStartButton();

    }

    @Override
    public void move() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    private void initializeStartButton() {
        addGameObject(_startButton = new BitmapButton(R.drawable.start, R.drawable.start_pressed, 660, 600));
        _startButton.addButtonEventHandler(new ButtonEventHandler() {
            @Override
            public void perform(BitmapButton button) {
                changeState(Game.RUNNING_STATE);
            }
        });
        addPointerEventHandler(_startButton);
    }
}

