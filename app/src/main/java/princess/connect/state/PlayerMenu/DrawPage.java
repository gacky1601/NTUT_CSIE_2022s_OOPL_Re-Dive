package princess.connect.state.PlayerMenu;

import java.util.Map;

import princeconnect.game.R;
import princess.connect.core.MovingBitmap;
import princess.connect.engine.GameEngine;

public class DrawPage extends PlayerMenu{
    public DrawPage(GameEngine engine) {
        super(engine);



    }
    public void initialize(Map<String, Object> data) {
        super.initialize(data);
        changebg(R.drawable.bg_100503,0,-200,1920,1080);
        super.initButton();
    }





}
