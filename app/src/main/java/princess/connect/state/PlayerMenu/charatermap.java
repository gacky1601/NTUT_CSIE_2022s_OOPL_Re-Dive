package princess.connect.state.PlayerMenu;


import static princess.connect.GameView.runtime;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import princeconnect.game.R;
import princess.connect.GameObject;
import princess.connect.core.MovingBitmap;
import princess.connect.extend.BitmapButton;

public class charatermap implements GameObject {
    private ArrayList<ArrayList<BitmapButton>> allcharter=new ArrayList();
    private int _onShowPage=0;
    private ArrayList loadallcharaters(){
        ArrayList<BitmapButton> _charater=new ArrayList();
        try {
            for (String charater:runtime.getAssets().list("character/icon")){
                Log.d("charater:",charater);
                _charater.add((new BitmapButton("character/icon/"+charater)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("Charatersize",String.valueOf(_charater.size()));
        return _charater;
    }



    public charatermap(){}

    @Override
    public void move() {}

    @Override
    public void show() {
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
            arr.show();
            countw++;
        }

    }


    @Override
    public void release() {}


};