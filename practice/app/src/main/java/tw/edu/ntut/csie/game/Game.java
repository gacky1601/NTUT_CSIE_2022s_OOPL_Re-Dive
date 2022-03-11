package tw.edu.ntut.csie.game;

import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import tw.edu.ntut.csie.game.engine.GameEngine;
import tw.edu.ntut.csie.game.state.StateOver;
import tw.edu.ntut.csie.game.state.StateReady;
import tw.edu.ntut.csie.game.state.StateRun;

import static android.hardware.Sensor.TYPE_ACCELEROMETER;
import static android.hardware.Sensor.TYPE_ORIENTATION;
import static android.hardware.SensorManager.SENSOR_DELAY_GAME;
import static android.view.Window.FEATURE_NO_TITLE;
import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

/**
 * <code>Game</code>是Android版Game Framework的主進入點，負責初始畫功能選單上
 * 的按鈕、畫面更新的執行緒及遊戲畫面。
 *
 * @author <a href="http://www.csie.ntut.edu.tw/labsdtl/">Lab SDT</a>
 * @version 2.0
 * @since 1.0
 */
public class Game extends Activity {

    /**
     * 遊戲第一個狀態的代碼，所有遊戲的第一個狀態其代碼都需等於{@link #INITIAL_STATE}。
     */
    public static final int INITIAL_STATE = 1;
    public static final int RUNNING_STATE = 2;
    public static final int OVER_STATE = 3;

    /**
     * 預設的畫面更新速度，一秒約15張畫面(理想值)。
     */
    public static final int FRAME_RATE = 15;

    /**
     * 預設的最大除錯資訊顯示數量
     */
    public static final int MAXIMUM_DEBUG_RECORDS = 10;

    /**
     * 遊戲畫面的寬度。
     */
    public static final int GAME_FRAME_WIDTH = 640;

    /**
     * 遊戲畫面的高度。
     */
    public static final int GAME_FRAME_HEIGHT = 376;

    /**
     * 開啟或關閉在選單上顯示資訊選項。
     */
    public static boolean ENABLE_INFO_SWITCH_MENU = true;

    /**
     * 開啟或關閉顯示除錯資訊。
     */
    public static boolean showDebugInfo = true;

    /**
     * 開啟或關閉顯示畫面更新速度與感應器的資訊。
     */
    public static boolean showDeviceInfo = true;

    /**
     * 選單項目的ID。
     */
    private static final int ITEM_MENU = 1;
    private static final int ITEM_EXIT = 2;
    private static final int ITEM_DEVICE_INFO = 3;
    private static final int ITEM_DEBUG_INFO = 4;

    /**
     * 持有實際遊戲畫面的物件。
     */
    private GameView _view;

    /**
     * 更新遊戲畫面與處理事件的引擎。
     */
    private GameEngine _engine;

    /**
     * 各項感應器的管理員。
     */
    private SensorManager _sensors;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 設定為全螢幕程式
        requestWindowFeature(FEATURE_NO_TITLE);
        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);
        setContentView(R.layout.main);

        // 取得遊戲畫面持有者與感應器管理員
        _view = (GameView) findViewById(R.id.GameView);
        _sensors = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (!_view.isInEditMode()) {
            _engine = new GameEngine(this, _view.getHolder());
            // 如果不想對遊戲畫面進行縮放，可以使用setDisplayRatio(1.0f)告知引擎顯示比例
            //_engine.setDisplayRatio(1.0f);
            // TODO 註冊狀態處理者
            _engine.registerGameState(INITIAL_STATE, new StateReady(_engine));
            _engine.registerGameState(RUNNING_STATE, new StateRun(_engine));
            _engine.registerGameState(OVER_STATE, new StateOver(_engine));
            _engine.setGameState(INITIAL_STATE);
            _view.setGameEngine(_engine);
        }
    }

    @Override
    protected void onPause() {
        _engine.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        _sensors.registerListener(_engine.getSensorEventListener(), _sensors.getDefaultSensor(TYPE_ORIENTATION), SENSOR_DELAY_GAME);
        _sensors.registerListener(_engine.getSensorEventListener(), _sensors.getDefaultSensor(TYPE_ACCELEROMETER), SENSOR_DELAY_GAME);
        _engine.resume();
        super.onResume();
    }

    @Override
    protected void onStop() {
        _sensors.unregisterListener(_engine.getSensorEventListener());
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        _engine.release();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, ITEM_MENU, 0, R.string.menu);
        if (ENABLE_INFO_SWITCH_MENU) {
            menu.add(0, ITEM_DEVICE_INFO, 0, R.string.deviceInfo);
            menu.add(0, ITEM_DEBUG_INFO, 0, R.string.debugInfo);
        }
        menu.add(0, ITEM_EXIT, 0, R.string.exit);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case ITEM_MENU:
                _engine.setGameState(INITIAL_STATE);
                return true;
            case ITEM_EXIT:
                _engine.exit();
                return true;
            case ITEM_DEVICE_INFO:
                showDeviceInfo = !showDeviceInfo;
                return true;
            case ITEM_DEBUG_INFO:
                showDebugInfo = !showDebugInfo;
                return true;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent msg) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU || keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            return super.onKeyDown(keyCode, msg);
        } else {
            _engine.keyPressed(keyCode);
            return true;
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent msg) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return super.onKeyUp(keyCode, msg);
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (_engine.getCurrentState() != INITIAL_STATE) {
                _engine.setGameState(INITIAL_STATE);
                return true;
            } else {
                return super.onKeyUp(keyCode, msg);
            }
        } else {
            _engine.keyReleased(keyCode);
            return true;
        }
    }
}
