package princess.connect.state.PlayerMenu;




import java.util.List;
import java.util.Map;

import princeconnect.game.R;
import princess.connect.core.MovingBitmap;
import princess.connect.engine.GameEngine;
import princess.connect.extend.BitmapButton;

public class CharaterPage extends PlayerMenu {
    public CharaterPage(GameEngine engine) { super(engine);}
    public void initialize(Map<String, Object> data) {
        add();
        MovingBitmap _Charbackground = new MovingBitmap(R.drawable.cha);
        _Charbackground.setLocation(100,50);
        _Charbackground.resize((int) ( _Charbackground.getWidth()*1.3), (int) (_Charbackground.getHeight()*1.3));
        changebg(R.drawable.bg_100941,0,-200,1920,1080);
        charatermap cm = new charatermap();
        addGameObject(_Charbackground);
        addGameObject(cm);
    }


}
