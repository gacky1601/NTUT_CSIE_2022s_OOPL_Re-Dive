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

    private ArrayList<ArrayList<BitmapButton>> loadallcharaters1(){
        ArrayList<ArrayList<BitmapButton>> temp=new ArrayList();
        ArrayList<BitmapButton>  _nc = new ArrayList<>();
        try {
            for (String charater:runtime.getAssets().list("character/icon")){
                Log.d("charater:",charater);
                _nc.add((new BitmapButton("character/icon/"+charater)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int Char_amount= _nc.size(),ncamount=0;
        ArrayList<BitmapButton> row0=new ArrayList<>();
        ArrayList<BitmapButton> row1=new ArrayList<>();
        ArrayList<BitmapButton> row2=new ArrayList<>();
        for (int i=0 ;i<3;i++){
            for (int j=0 ;j<3;j++){
                for(int k = 0; k<5; k++){
                    if(j==0)
                        row0.add(_nc.get(ncamount));
                    if(j==1)
                        row1.add(_nc.get(ncamount));
                    if(j==2)
                        row2.add(_nc.get(ncamount));
                    Char_amount--;
                    ncamount++;
                }
            }
            temp.add(row0);
            temp.add(row1);
            temp.add(row1);
            row0.clear();
            row1.clear();
            row2.clear();
        }
        return temp;
    }

    public charatermap(){
//        allcharter=loadallcharaters1();
    }

    @Override
    public void move() {}

    @Override
    public void show() {
//        ArrayList<ArrayList<BitmapButton>> _allcharter=new ArrayList();
//        _allcharter=loadallcharaters1();
        int countw=0,counth=0,w=190,h=80;
//        for(int i=0;i<4;i++){
//            for (int j=0;j<5;j++){
//                allcharter.get(0).get(0).setLocation(w+i*210,h+j*180);
//                allcharter.get(0).get(0).show();
//            }
//        }
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
//        _allcharter.get(0).get(0).setLocation(100,100);
//        _allcharter.get(0).get(0).show();
    }


    @Override
    public void release() {}


};