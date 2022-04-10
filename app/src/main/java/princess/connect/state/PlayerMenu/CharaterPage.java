package princess.connect.state.PlayerMenu;




import static princess.connect.GameView.runtime;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import princeconnect.game.R;
import princess.connect.core.MovingBitmap;
import princess.connect.engine.GameEngine;
import princess.connect.extend.BitmapButton;

public class CharaterPage extends PlayerMenu {

    public CharaterPage(GameEngine engine) { super(engine);}
    private ArrayList<ArrayList<BitmapButton>> allcharter=new ArrayList();
    private int _onShowPage=0;

    public void initialize(Map<String, Object> data) {
        add();
        MovingBitmap _Charbackground = new MovingBitmap(R.drawable.cha);
        _Charbackground.setLocation(100,50);
        _Charbackground.resize((int) ( _Charbackground.getWidth()*1.3), (int) (_Charbackground.getHeight()*1.3));
        changebg(R.drawable.bg_100941,0,-200,1920,1080);
        addGameObject(_Charbackground);
        showallcharacter();

    }



    private ArrayList loadallcharaters(){
        ArrayList<BitmapButton> _charater=new ArrayList();
        try {
            for (String charater : runtime.getAssets().list("character")){
                Log.d("charater:",charater);
                _charater.add((new BitmapButton("character/"+charater)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("Charatersize",String.valueOf(_charater.size()));
        return _charater;
    }

    private void showallcharacter(){
        int countw=0,counth=0,w=190,h=80;
        ArrayList <BitmapButton>a=loadallcharaters();
        for(BitmapButton arr :a){
            if (countw>=5){
                countw=0;
                counth++;
            }
            if(counth>=3){
                break;
            }
            arr.setLocation(w+210*countw,h+counth*180);
            addGameObject(arr);
            countw++;
        }
    }

}
