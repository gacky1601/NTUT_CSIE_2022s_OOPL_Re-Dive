package princess.connect.state.PlayerMenu;

import java.util.Map;
import java.util.Random;

import princeconnect.game.R;
import princess.connect.Game;
import princess.connect.core.MovingBitmap;
import princess.connect.engine.GameEngine;
import princess.connect.extend.BitmapButton;

public class AdventurePage extends PlayerMenu{
    public AdventurePage(GameEngine engine) {
        super(engine);
    }
    private BitmapButton _battle;
    public void initialize(Map<String, Object> data) {
        super.initialize(data);
        for(int i =0 ; i<10;i++) {
            initializeButton(_battle, R.drawable.empty_btn, R.drawable.empty_btn_pressed, 80+120*i, randInt(200,550), Game.ADV_STATE);
        }
    }
    public static int randInt(int min, int max) {
        int x;
        Random randomNum = new Random();
        x = min + randomNum.nextInt((max - min) + 1);
        if(x>350&&x<500){
            return randInt(min, max);
        }
        return x;
    }
}
