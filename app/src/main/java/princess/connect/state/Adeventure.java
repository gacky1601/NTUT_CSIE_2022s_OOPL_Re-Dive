package princess.connect.state;

import java.util.List;
import java.util.Map;

import princeconnect.game.R;
import princess.connect.Pointer;
import princess.connect.core.MovingBitmap;
import princess.connect.engine.GameEngine;

public class Adeventure extends AbstractGameState{

    /**
     * 建構一個<code>GameState</code>實體。
     *
     * @param engine 執行狀態處理者的引擎
     */

    protected Adeventure(GameEngine engine) {
        super(engine);
    }

    @Override
    public void initialize(Map<String, Object> data) {
        addGameObject(new MovingBitmap(R.drawable.background,280,300));
    }

    @Override
    public void pause() {    }

    @Override
    public void resume() {    }

}
