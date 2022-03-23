package prince.connect.state.PlayerMenu;

import java.util.Map;

import prince.connect.Game;
import princeconnect.game.R;
import prince.connect.core.MovingBitmap;
import prince.connect.engine.GameEngine;
import prince.connect.extend.BitmapButton;
import prince.connect.extend.ButtonEventHandler;
import prince.connect.state.AbstractGameState;

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

    private void initializeButton(BitmapButton Button,int image_a,int image_b,int x,int y) {
        addGameObject(Button = new BitmapButton(image_a, image_b, x, y));
        Button.addButtonEventHandler(new ButtonEventHandler() {
            @Override
            public void perform(BitmapButton button) {
                changeState(Game.ADV_PAGE);
            }
        });
        addPointerEventHandler(Button);
    }

    public  void add(){
        addGameObject(_background = new MovingBitmap(R.drawable.background));
        int _buttondx = 200,_buttondy=550;
        initializeButton(_PlayermenuButton,R.drawable.home,R.drawable.home_pressed, _buttondx,_buttondy);
        initializeButton(_AdventureButton,R.drawable.adventure,R.drawable.adventure_pressed, _buttondx +221,_buttondy);
        initializeButton(_CharaterButton,R.drawable.charater,R.drawable.charater_pressed, _buttondx +221+210,_buttondy);
        initializeButton(_DrawButton,R.drawable.draw,R.drawable.draw_pressed, _buttondx +221+210+200,_buttondy);
        initializeButton(_SettingButton,R.drawable.setting,R.drawable.setting_pressed, _buttondx +221+210+200+213,_buttondy);
    }

}
