package princess.connect.state.PlayerMenu;

import java.util.HashMap;
import java.util.Map;

import princess.connect.Game;
import princeconnect.game.R;
import princess.connect.core.MovingBitmap;
import princess.connect.engine.GameEngine;
import princess.connect.extend.BitmapButton;
import princess.connect.extend.ButtonEventHandler;
import princess.connect.state.AbstractGameState;

public class PlayerMenu extends AbstractGameState {

    public PlayerMenu(GameEngine engine) { super(engine); }

    private MovingBitmap _background;
    public BitmapButton _PlayermenuButton;
    public BitmapButton _AdventureButton;
    public BitmapButton _CharaterButton;
    public BitmapButton _DrawButton;
    public BitmapButton _SettingButton;


    @Override
    public void initialize(Map<String, Object> data) {
        _background = new MovingBitmap(R.drawable.background);
        addGameObject(_background);
        _background.resize(0.85);
        _background.setLocation(-300,-300);
        int _buttondx = 230,_buttondy=660;
        initializeButton(_PlayermenuButton,R.drawable.bg_main,R.drawable.bg_main_press, _buttondx,_buttondy+1,Game.PLAYER_MENU);
        initializeButton(_AdventureButton,R.drawable.bg_other,R.drawable.bg_charater_pressed, _buttondx +178,_buttondy,Game.CHA_PAGE);
        initializeButton(_CharaterButton,R.drawable.bg_other,R.drawable.bg_adventure_press, _buttondx +178+170,_buttondy,Game.ADV_STATE);
        initializeButton(_DrawButton,R.drawable.bg_other,R.drawable.bg_draw_press, _buttondx +178+170+170,_buttondy,Game.DRAW_PAGE);
        initializeButton(_SettingButton,R.drawable.bg_setting,R.drawable.bg_setting_press, _buttondx +178+170+170+170,_buttondy+1,Game.SETTING_PAGE);
        addGameObject(new MovingBitmap(R.drawable.ic_main,280,630));
        addGameObject(new MovingBitmap(R.drawable.ic_charater,280+178,615));
        addGameObject(new MovingBitmap(R.drawable.ic_adventure,280+178+170,630));
        addGameObject(new MovingBitmap(R.drawable.ic_draw,280+178+170+170,630));
        addGameObject(new MovingBitmap(R.drawable.ic_setting,280+178+170+170+170,630));
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    private void initializeButton(BitmapButton Button,int image_a,int image_b,int x,int y,int state) {
        initializeButton(Button, image_a, image_b, x, y, state, null);
    }

    private void initializeButton(BitmapButton Button,int image_a,int image_b,int x,int y,int state, Map<String, Object> data) {
        Button= new BitmapButton(image_a, image_b, x, y);
        addGameObject(Button);
        Button.addButtonEventHandler(button -> changeState(state, data));
        addPointerEventHandler(Button);

    }

    public void changebg(int bg){
        _background.loadBitmap(bg);
    }

    public void changebg(int bg,int x,int y,int width,int height){
        _background.loadBitmap(bg);
        _background.setLocation(x,y);
        _background.resize(width,height);
    }

}