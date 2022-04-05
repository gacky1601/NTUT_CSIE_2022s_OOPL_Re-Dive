package princess.connect.state.PlayerMenu;




import java.util.List;
import java.util.Map;

import princeconnect.game.R;
import princess.connect.core.MovingBitmap;
import princess.connect.engine.GameEngine;

public class CharaterPage extends PlayerMenu {
    private int[] cha={100931, 101031, 101131, 101231, 101431, 101831, 102831, 102931, 103031, 103231, 103631, 103731, 104331, 104431, 104731};
    public CharaterPage(GameEngine engine) { super(engine);}
    public void initialize(Map<String, Object> data) {
        add();
        MovingBitmap _Charbackground = new MovingBitmap(R.drawable.chabg);
        changebg(R.drawable.bg2);
        charatermap cm = new charatermap();
        cm.charlist=cha;
        addGameObject(_Charbackground);
        addGameObject(cm);
    }


}
