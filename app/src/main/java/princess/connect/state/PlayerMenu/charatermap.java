package princess.connect.state.PlayerMenu;


import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
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
    public ArrayList<BitmapButton> allcha = new ArrayList();
    public charatermap(){


        icon=new BitmapButton(R.drawable.icon_unit_100931);

        int a;

        a=charlist.length;
        Log.d("na",String.valueOf(a));
        String prefix="R.drawable.icon_unit_";
        allcha.add(new BitmapButton(R.drawable.icon_unit_101431));
        allcha.add(new BitmapButton(R.drawable.icon_unit_101431));
        allcha.add(new BitmapButton(R.drawable.icon_unit_101431));
        allcha.add(new BitmapButton(R.drawable.icon_unit_101431));


    }

    @Override
    public void move() {

    }

    @Override
    public void show() {
        int count=0;
        for(BitmapButton arr :allcha){
            Log.d("allcha","arr");
            arr.setLocation(100+150*count,100);
            arr.setVisible(true);
            arr.show();
            count++;
        }

    }
    public int charlist[]={100931, 101031, 101131, 101231, 101431, 101831, 102831, 102931, 103031, 103231, 103631, 103731, 104331, 104431, 104731};
    public int charnum(){
        return charlist.length;
    }

    @Override
    public void release() {
        icon.release();
        icon=null;
    }
    public static String getResourceString(String name, Context context) {
        int nameResourceID = context.getResources().getIdentifier(name,
                "string", context.getApplicationInfo().packageName);
        if (nameResourceID == 0) {
            throw new IllegalArgumentException(
                    "No resource string found with name " + name);
        } else {
            return context.getString(nameResourceID);
        }
    }


};