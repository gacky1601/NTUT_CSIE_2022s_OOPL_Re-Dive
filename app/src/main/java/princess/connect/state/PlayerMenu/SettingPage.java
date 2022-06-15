package princess.connect.state.PlayerMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import princeconnect.game.R;
import princess.connect.Game;
import princess.connect.baseClass.Character;
import princess.connect.core.MovingBitmap;
import princess.connect.engine.GameEngine;
import princess.connect.extend.BitmapButton;
import princess.connect.state.InitPage;

public class SettingPage extends PlayerMenu{
    public SettingPage(GameEngine engine) { super(engine); }
    public void initialize(Map<String, Object> data) {
        super.initialize(data);
        _background.loadBitmap(new MovingBitmap(R.drawable.bg_500367));
        _background.resize(1920, 1080).setLocation((Game.GAME_FRAME_WIDTH - _background.getWidth()) / 2, (Game.GAME_FRAME_HEIGHT - _background.getHeight()) / 2);
        BitmapButton confirm = new BitmapButton("interface/button/white.png").resize(1.5);
        confirm.setLocation((Game.GAME_FRAME_WIDTH - confirm.getWidth()) / 2, (Game.GAME_FRAME_HEIGHT - confirm.getHeight()) / 2);
        confirm.addButtonEventHandler(button -> {
            List<Character> newPlayerCharsList = new ArrayList<>();
            List<Character> allCharsList = (List<Character>) data.get(InitPage.ALL_CHARACTER_LIST);
            int[] levelProgress = {1, 10};
            for (Character chara : allCharsList) {
                try {
                    Character newChara = chara.getClass().newInstance();
                    newChara.setRank(7);
                    newPlayerCharsList.add(newChara);
                } catch (Exception ignore) {}
            }
            data.put(InitPage.PLAYER_CHARACTER_LIST, newPlayerCharsList);
            data.put(InitPage.LEVEL_PROGRESS, levelProgress);
        });
        addPointerEventHandler(confirm);
        addGameObject(confirm);
        super.initButton();
    }
}
