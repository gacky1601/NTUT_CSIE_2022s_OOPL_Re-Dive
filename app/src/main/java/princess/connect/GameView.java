package princess.connect;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.WindowManager;

import princess.connect.engine.GameEngine;

import static android.content.Context.WINDOW_SERVICE;

/**
 * <code>GameView</code>用來呈現遊戲畫面的類別。
 *
 * @author <a href="http://www.csie.ntut.edu.tw/labsdtl/">Lab SDT</a>
 * @version 2.0
 * @since 1.0
 */
public class GameView extends SurfaceView {

    /**
     * 目前遊戲的執行環境。
     */
    public static Context runtime;

    /**
     * 實際螢幕畫素的寬度。
     */
    public static int screenWidth;

    /**
     * 實際螢幕畫素的高度。
     */
    public static int screenHeight;

    /**
     * 更新遊戲畫面與處理事件的引擎。
     */
    private GameEngine _engine;

    /**
     * 建立一個GameView物件以顯示遊戲畫面
     *
     * @param context    程式的執行環境
     * @param attributes 程式的外部屬性
     */
    public GameView(Context context, AttributeSet attributes) {
        super(context, attributes);
        runtime = context;

        // 取得實際的螢幕寬度和高度，讓遊戲引擎能夠換算遊戲畫面與實際螢幕的投射比例
        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        screenWidth = windowManager.getDefaultDisplay().getWidth();
        screenHeight = windowManager.getDefaultDisplay().getHeight();
        setFocusable(true);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (_engine != null) {
            if (hasWindowFocus) {
                _engine.resume();
            } else {
                _engine.pause();
            }
        }
    }

    /**
     * 設定接收事件及更新畫面的遊戲引擎。
     *
     * @param engine 遊戲引擎
     */
    public void setGameEngine(GameEngine engine) {
        _engine = engine;
        setOnTouchListener(_engine.getOnTouchListener());
        getHolder().addCallback(_engine.getSurfaceHolderCallback());
    }
}
