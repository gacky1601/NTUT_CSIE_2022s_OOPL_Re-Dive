package tw.edu.ntut.csie.game.state;

import java.util.Map;
import java.util.Vector;

import tw.edu.ntut.csie.game.Game;
import tw.edu.ntut.csie.game.R;
import tw.edu.ntut.csie.game.core.MovingBitmap;
import tw.edu.ntut.csie.game.engine.GameEngine;
import tw.edu.ntut.csie.game.extend.BitmapButton;
import tw.edu.ntut.csie.game.extend.ButtonEventHandler;

public class PlayerMenu extends AbstractGameState{

    public PlayerMenu(GameEngine engine) { super(engine); }

    private MovingBitmap _background;

    private BitmapButton _PlayermenuButton;
    private BitmapButton _AdventureButton;
    private BitmapButton _CharaterButton;
    private BitmapButton _DrawButton;
    private BitmapButton _SettingButton;


    @Override
    public void initialize(Map<String, Object> data) {

        addGameObject(_background = new MovingBitmap(R.drawable.background));
        int _buttondx = 200,_buttondy=650;
        initializeButton(_PlayermenuButton,R.drawable.about,R.drawable.about_pressed, _buttondx,_buttondy);
        initializeButton(_AdventureButton,R.drawable.about,R.drawable.about_pressed, _buttondx +200,_buttondy);
        initializeButton(_CharaterButton,R.drawable.about,R.drawable.about_pressed, _buttondx +400,_buttondy);
        initializeButton(_DrawButton,R.drawable.about,R.drawable.about_pressed, _buttondx +600,_buttondy);
        initializeButton(_SettingButton,R.drawable.about,R.drawable.about_pressed, _buttondx +800,_buttondy);

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
                changeState(Game.RUNNING_STATE);
            }
        });
        addPointerEventHandler(Button);
    }
}
