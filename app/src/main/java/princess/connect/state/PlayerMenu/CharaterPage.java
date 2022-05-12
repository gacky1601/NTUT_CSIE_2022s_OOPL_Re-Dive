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
    private ArrayList<BitmapButton> allcharter=new ArrayList();
    private int _onShowPage=0;

    public void initialize(Map<String, Object> data) {
        _onShowPage=0;
        super.initialize(data);
        MovingBitmap _Charbackground = new MovingBitmap(R.drawable.cha);
        _Charbackground.setLocation(100,50);
        _Charbackground.resize((int) ( _Charbackground.getWidth()*1.3), (int) (_Charbackground.getHeight()*1.3)+15);
        changebg(R.drawable.bg_100941,0,-200,1920,1080);
        addGameObject(_Charbackground);
        showallcharacter();
        BitmapButton nextpage =new BitmapButton(R.drawable.empty_btn,R.drawable.empty_btn_pressed,1245,350);
//        nextpage.resize_w(0.3);
        nextpage.addButtonEventHandler(button -> change_page("+"));
        addPointerEventHandler(nextpage);
        addGameObject(nextpage);
        BitmapButton lastpage =new BitmapButton(R.drawable.empty_btn,R.drawable.empty_btn_pressed,1245,260);
//        lastpage.resize_w(0.3);
        lastpage.addButtonEventHandler(button -> change_page("-"));
        addPointerEventHandler(lastpage);
        addGameObject(lastpage);
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
        int w=160,h=75;
        ArrayList <BitmapButton> a =loadallcharaters();
        allcharter=a;
        Log.d("All Character Size:",String.valueOf(allcharter.size()));
        for(int i =0;i<3 ;i++){
            for (int j=0;j<3;j++) {
                Log.d("Current Character:",String.valueOf(_onShowPage*9+i*3+j));

                if(_onShowPage*9+i*3+j>allcharter.size()-1)
                    break;
                a.get(_onShowPage*9+i*3+j).setLocation(w + 380 * j, h + 180 * i);
                a.get(_onShowPage*9+i*3+j).resize(0.60);
                addGameObject(a.get(_onShowPage*9+i*3+j));
            }
        }
    }
    private void change_page(String op){
        if(op=="+")
            if(_onShowPage<(allcharter.size()/9))
                _onShowPage++;
        if(op=="-"){
            if(_onShowPage!=0)
                _onShowPage--;
        }
        showallcharacter();
    }

}
