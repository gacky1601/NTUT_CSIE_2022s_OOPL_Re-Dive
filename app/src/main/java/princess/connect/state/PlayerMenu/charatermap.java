package princess.connect.state.PlayerMenu;


import android.content.res.Resources;
import java.util.ArrayList;
import princeconnect.game.R;
import princess.connect.GameObject;
import princess.connect.extend.BitmapButton;

public class charatermap implements GameObject {


    public int getTeamLogos(String teamId)
    {
        switch (teamId)
        {
            case "100931":
                return R.drawable.icon_unit_100931;
            case "101431":
                return R.drawable.icon_unit_101431;
        }
        return 0;
    }

    private BitmapButton icon;

    private final int x=230,y=50,mw=190,mh=190;
    public charatermap(){


        icon=new BitmapButton(R.drawable.icon_unit_100931);
        int a;
        ArrayList<BitmapButton> allcha = new ArrayList();
        a=charlist.length;
        String prefix="R.drawable.icon_unit_";
        while(a==0){
//            allcha.add(new BitmapButton(getTeamLogos(String.valueOf(charlist[a]))));
            a--;
        }
    }

    @Override
    public void move() {

    }

    @Override
    public void show() {

    }
    public int charlist[]={};
    public int charnum(){
        return charlist.length;
    }

    @Override
    public void release() {
        icon.release();
        icon=null;
    }



};