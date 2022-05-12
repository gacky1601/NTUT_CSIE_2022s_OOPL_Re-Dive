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
        changebg(R.drawable.bg2,-100,-200,1920,1080);
        boolean min=false;
        for(int i =0 ; i<10;i++) {
            if(min)
                initializeButton(_battle, R.drawable.empty_btn, R.drawable.empty_btn_pressed, 80+120*i, 300+randInt(20,100), Game.ADV_STATE);
            else
                initializeButton(_battle, R.drawable.empty_btn, R.drawable.empty_btn_pressed, 80+120*i, 300-randInt(20,100), Game.ADV_STATE);
            min=!min;
        }
    }
    public static int randInt(int min, int max) {
        Random randomNum = new Random();
        return min + randomNum.nextInt((max - min) + 1);
    }
}
