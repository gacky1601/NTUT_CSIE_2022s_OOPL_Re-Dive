package prince.connect.state.PlayerMenu;

import java.util.Map;

import prince.connect.Game;
import prince.connect.core.MovingBitmap;
import prince.connect.engine.GameEngine;
import prince.connect.extend.BitmapButton;
import prince.connect.extend.ButtonEventHandler;

public class AdventurePage extends PlayerMenu{
    public AdventurePage(GameEngine engine) { super(engine); }
    @Override
    public void initialize(Map<String, Object> data) {
        add();

    }
}
