package princess.connect.state.PlayerMenu;

import java.util.Map;

import princess.connect.engine.GameEngine;

public class AdventurePage extends PlayerMenu{
    public AdventurePage(GameEngine engine) { super(engine); }

    public void initialize(Map<String, Object> data) {
        add();
    }
}
