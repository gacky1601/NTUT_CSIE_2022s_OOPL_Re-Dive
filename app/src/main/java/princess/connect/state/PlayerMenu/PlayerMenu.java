package princess.connect.state.PlayerMenu;

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
        add();

    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    private void initializeButton(BitmapButton Button,int image_a,int image_b,int x,int y,int state) {
        addGameObject(Button = new BitmapButton(image_a, image_b, x, y));
        Button.addButtonEventHandler(new ButtonEventHandler() {
            @Override
            public void perform(BitmapButton button) {
                changeState(state);
            }
        });
        addPointerEventHandler(Button);
    }

    public  void add(){
        addGameObject(_background = new MovingBitmap(R.drawable.background));
        int _buttondx = 200,_buttondy=550;
        initializeButton(_PlayermenuButton,R.drawable.home,R.drawable.home_pressed, _buttondx,_buttondy,Game.PLAYER_MENU);
        initializeButton(_AdventureButton,R.drawable.adventure,R.drawable.adventure_pressed, _buttondx +221,_buttondy,Game.ADV_PAGE);
        initializeButton(_CharaterButton,R.drawable.charater,R.drawable.charater_pressed, _buttondx +221+210,_buttondy,Game.CHA_STATE);
        initializeButton(_DrawButton,R.drawable.draw,R.drawable.draw_pressed, _buttondx +221+210+200,_buttondy,0);
        initializeButton(_SettingButton,R.drawable.setting,R.drawable.setting_pressed, _buttondx +221+210+200+213,_buttondy,0);
    }

}
