package princess.connect.state;

import java.util.Map;

import princess.connect.Game;
import princeconnect.game.R;
import princess.connect.core.MovingBitmap;
import princess.connect.engine.GameEngine;
import princess.connect.extend.BitmapButton;
import princess.connect.extend.ButtonEventHandler;

public class InitPage extends AbstractGameState{

    public InitPage(GameEngine engine) {
        super(engine);
    }

    private MovingBitmap _background;
    private BitmapButton _startButton;


    @Override
    public void initialize(Map<String, Object> data) {
        addGameObject(_background = new MovingBitmap(R.drawable.background));

        addGameObject(_startButton = new BitmapButton(R.drawable.start, R.drawable.start_pressed, 0, 0));
        _startButton.addButtonEventHandler(new ButtonEventHandler() {
            @Override
            public void perform(BitmapButton button) {
                changeState(Game.PLAYER_MENU);
            }
        });
        addPointerEventHandler(_startButton);

    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

}

