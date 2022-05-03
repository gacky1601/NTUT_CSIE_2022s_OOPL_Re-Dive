package princess.connect.state.PlayerMenu;

import java.util.Map;

import princeconnect.game.R;
import princess.connect.engine.GameEngine;

public class SettingPage extends PlayerMenu{
    public SettingPage(GameEngine engine) { super(engine); }
    public void initialize(Map<String, Object> data) {
        super.initialize(data);
        changebg(R.drawable.bg_100742,0,-200,1920,1080);
    }
}
