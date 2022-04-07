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
    private MovingBitmap _background,_icon;
    private BitmapButton _startButton;


    @Override
    public void initialize(Map<String, Object> data) {
        _background = new MovingBitmap(R.drawable.background);
        _startButton = new BitmapButton(R.drawable.start, R.drawable.start_pressed, 0, 0);
        _icon=new MovingBitmap(R.drawable.icon_init,430,500);
        _icon.resize((int)(_icon.getWidth()*1.3),(int)(_icon.getHeight()*1.3));
        addGameObject(_background);
        addGameObject(_icon);
        addGameObject(_startButton);

        _startButton.addButtonEventHandler(button -> changeState(Game.PLAYER_MENU));
        addPointerEventHandler(_startButton);

    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

}

