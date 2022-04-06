package princess.connect.state;

import java.util.List;
import java.util.Map;

import princeconnect.game.R;
import princess.connect.Pointer;
import princess.connect.core.MovingBitmap;
import princess.connect.engine.GameEngine;
import princess.connect.baseClass.BattleGround;
import princess.connect.baseClass.Character;
import princess.connect.character.*;
import princess.connect.extend.Animation;

public class Adventure extends AbstractGameState{

    /**
     * 建構一個<code>GameState</code>實體。
     *
     * @param engine 執行狀態處理者的引擎
     */

    public Adventure(GameEngine engine) {
        super(engine);
    }

    @Override
    public void initialize(Map<String, Object> data) {
        MovingBitmap background = new MovingBitmap(R.drawable.bg_100021,0,-200);
        background.resize(1920  ,1080);
        addGameObject(background);
        List<Character> characterLeft = List.of(new Pecorine());
        List<Character> characterRight = List.of(new Kokoro());
        BattleGround ground = new BattleGround(characterLeft, characterRight);
        ground.initialize();
        ground.main();
        Animation animation = getAnimationFromAssets("character/pecorine/idle");
        animation.setDelay(1);
        animation.setLocation(-0,-0);
        addGameObject(animation);
    }

    @Override
    public void pause() {    }

    @Override
    public void resume() {    }

}
