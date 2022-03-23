package tw.edu.ntut.csie.game.state.PlayerMenu;

import java.util.Map;

import tw.edu.ntut.csie.game.Game;
import tw.edu.ntut.csie.game.R;
import tw.edu.ntut.csie.game.core.MovingBitmap;
import tw.edu.ntut.csie.game.engine.GameEngine;
import tw.edu.ntut.csie.game.extend.BitmapButton;
import tw.edu.ntut.csie.game.extend.ButtonEventHandler;
import tw.edu.ntut.csie.game.state.AbstractGameState;

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
        int _buttondx = 300,_buttondy=600;
        initializeButton(_PlayermenuButton,R.drawable.home,R.drawable.home_press, _buttondx,_buttondy);
        initializeButton(_AdventureButton,R.drawable.home,R.drawable.home_press, _buttondx +200,_buttondy);
        initializeButton(_CharaterButton,R.drawable.home,R.drawable.home_press, _buttondx +400,_buttondy);
        initializeButton(_DrawButton,R.drawable.home,R.drawable.home_press, _buttondx +600,_buttondy);
        initializeButton(_SettingButton,R.drawable.home,R.drawable.home_press, _buttondx +800,_buttondy);
    }

}
