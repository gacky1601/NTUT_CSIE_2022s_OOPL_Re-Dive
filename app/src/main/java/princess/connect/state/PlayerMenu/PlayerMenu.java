package princess.connect.state.PlayerMenu;

import java.util.Map;

import princess.connect.Game;
import princeconnect.game.R;
import princess.connect.core.Audio;
import princess.connect.core.MovingBitmap;
import princess.connect.engine.GameEngine;
import princess.connect.extend.BitmapButton;
import princess.connect.state.AbstractGameState;
import princess.connect.state.InitPage;

public class PlayerMenu extends AbstractGameState {

    public PlayerMenu(GameEngine engine) {
        super(engine);
    }

    protected MovingBitmap _background;
    private Map<String, Object> _data;

    @Override
    public void initialize(Map<String, Object> data) {
        _data = data;
        _background = new MovingBitmap();
        _background.loadBitmap(new MovingBitmap(R.drawable.bg_500133));
        _background.resize(1920, 1080).setLocation((Game.GAME_FRAME_WIDTH - _background.getWidth()) / 2,
                (Game.GAME_FRAME_HEIGHT - _background.getHeight()) / 2);
        addGameObject(_background);
        initButton();
    }

    public void initButton() {
        int _buttondx = 270, _buttondy = 660;
        initializeButton(R.drawable.bg_main, R.drawable.bg_main_press, _buttondx, _buttondy + 1, Game.PLAYER_MENU,
                _data);
        initializeButton(R.drawable.bg_other, R.drawable.bg_charater_pressed, _buttondx + 178, _buttondy, Game.CHA_PAGE,
                _data);
        initializeButton(R.drawable.bg_other, R.drawable.bg_adventure_press, _buttondx + 178 + 170, _buttondy,
                Game.ADV_PAGE, _data);
        initializeButton(R.drawable.bg_other, R.drawable.bg_draw_press, _buttondx + 178 + 170 + 170, _buttondy,
                Game.DRAW_PAGE, _data);
        initializeButton(R.drawable.bg_setting, R.drawable.bg_setting_press, _buttondx + 178 + 170 + 170 + 170,
                _buttondy + 1, Game.SETTING_PAGE, _data);
        addGameObject(new MovingBitmap(R.drawable.ic_main, _buttondx + 50, 630));
        addGameObject(new MovingBitmap(R.drawable.ic_charater, _buttondx + 50 + 178, 615));
        addGameObject(new MovingBitmap(R.drawable.ic_adventure, _buttondx + 50 + 178 + 170, 630));
        addGameObject(new MovingBitmap(R.drawable.ic_draw, _buttondx + 50 + 178 + 170 + 170, 630));
        addGameObject(new MovingBitmap(R.drawable.ic_setting, _buttondx + 50 + 178 + 170 + 170 + 170, 630));
        addGameObject(new MovingBitmap(R.drawable.text_main).resize(0.15).setLocation(335, 690));
        addGameObject(new MovingBitmap(R.drawable.text_character).resize(0.15).setLocation(335 + 178, 690));
        addGameObject(new MovingBitmap(R.drawable.text_adventure).resize(0.15).setLocation(330 + 178 + 170, 690));
        addGameObject(new MovingBitmap(R.drawable.text_gacha).resize(0.15).setLocation(330 + 178 + 170 + 170, 690));
        addGameObject(new MovingBitmap(R.drawable.text_menu).resize(0.15).setLocation(330 + 178 + 170 + 170 + 160, 690));
    }

    @Override
    public void pause() {
        ((Audio) _data.get(InitPage.BGM)).pause();
    }

    @Override
    public void resume() {
        ((Audio) _data.get(InitPage.BGM)).resume();
    }

    private void initializeButton(int image_a, int image_b, int x, int y, int state, Map<String, Object> data) {
        BitmapButton Button = new BitmapButton(image_a, image_b, x, y);
        Button.addButtonEventHandler(button -> {
            try {
                Thread.sleep(100);
            } catch (Exception ignore) {
            }
            changeState(state, data);
        });
        addPointerEventHandler(Button);
        addGameObject(Button);
    }
}