package prince.connect.engine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.SensorEventListener;
import android.text.TextPaint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View.OnTouchListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import prince.connect.Game;
import prince.connect.state.GameState;

import static prince.connect.Game.FRAME_RATE;
import static prince.connect.Game.GAME_FRAME_HEIGHT;
import static prince.connect.Game.GAME_FRAME_WIDTH;
import static prince.connect.Game.MAXIMUM_DEBUG_RECORDS;
import static prince.connect.Game.showDebugInfo;
import static prince.connect.Game.showDeviceInfo;
import static prince.connect.GameView.screenHeight;
import static prince.connect.GameView.screenWidth;

/**
 * <code>GameEngine</code>是一個獨立於Android主執行緒外的繪圖與事件處理引擎，
 * 不斷地更新畫面與處理來自{@link GameView}的事件。事件和畫面的更新會轉交給目前
 * 遊戲狀態，可以視需求，在適當的時機切換遊戲狀態。
 *
 * @author <a href="http://www.csie.ntut.edu.tw/labsdtl/">Lab SDT</a>
 * @version 2.0
 * @since 1.0
 */
public class GameEngine implements Runnable {

    /**
     * 目前遊戲使用的畫布。這由Android Framework提供，請勿更改此值。
     */
    public static Canvas canvas;

    /**
     * 更新畫面用的常數。
     */
    private static final int ONE_SECOND = 1000;
    private static final int LINE_HEIGHT = 15;
    private static final int RATIO_INFO_OFFSET = LINE_HEIGHT;
    private static final int ORIENTATION_INFO_OFFSET = 30;
    private static final int ACCELERATION_INFO_OFFSET = 45;
    private static final int FRAME_RATE_INFO_OFFSET = 50;
    private static final String ENGINE_VERSION = "Engine version: 2.0.20120315 Build 1323";
    private static final String NO_DEBUG_INFO_HISTORY = "No debug information history.";
    private static final String FRAME_TIME_INFO_FORMAT = "%1d ms/frame";

    /**
     * 畫面與畫面之間的間隔時間(ms)，為畫面更新率的倒數。
     */
    private int _refreshTime;

    /**
     * 目前遊戲狀態的代碼。
     */
    private int _currentState;

    /**
     * 遊戲引擎內部狀態。
     */
    private boolean _paused = false;
    private boolean _running = false;

    /**
     * 遊戲主程式實體。
     */
    private Game _game;

    /**
     * 遊戲畫面尺寸資訊。
     */
    private GameFrame _gameFrame;

    /**
     * 遊戲畫面持有者。
     */
    private SurfaceHolder _surface;

    /**
     * 遊戲引擎內部所使用的資源。
     */
    private Paint _normalPaint;
    private Paint _warningPaint;
    private Paint _backgroundPaint;

    /**
     * 目前態的遊戲狀。
     */
    private GameState _gameState;
    private GameState _emptyState;
    private HashMap<Integer, GameState> _gameStates;

    /**
     * 負責事件聆聽及轉送的物件。
     */
    private EventDispatcher _eventDispatcher;
    private SurfaceEventHandler _surfaceEventHandler;

    /**
     * 顯示在螢幕上的額外資訊。
     */
    private ArrayList<String> _debugInfo;

    /**
     * 建立一個{@link GameEngine}用來處理畫面的更新及處理事件。
     *
     * @param game    主程式實體
     * @param surface 待更新的畫面
     */
    public GameEngine(Game game, SurfaceHolder surface) {
        _game = game;
        _surface = surface;
        initializePaints();
        setFrameRate(FRAME_RATE);
        _gameFrame = new GameFrame(GAME_FRAME_WIDTH, GAME_FRAME_HEIGHT);
        _emptyState = new EmptyState(this);
        _debugInfo = new ArrayList<String>();
        _eventDispatcher = new EventDispatcher(this);
        _gameStates = new HashMap<Integer, GameState>();
        _surfaceEventHandler = new SurfaceEventHandler(this);
    }

    /**
     * 增加一筆除錯資訊到螢幕上。螢幕上最多顯示{@link #MAXIMUM_DEBUG_RECORDS}筆除錯資訊。
     *
     * @param info 除錯資訊
     */
    public void addDebugInfo(String info) {
        Log.d(getClass().getSimpleName(), info);
        _debugInfo.add(info);
        if (_debugInfo.size() > MAXIMUM_DEBUG_RECORDS) {
            _debugInfo.remove(0);
        }
    }

    /**
     * 清除所有除錯資訊。
     */
    public void clearDebugInfo() {
        _debugInfo.clear();
    }

    /**
     * 終止引擎並通知Game activity結束程式。
     */
    public void exit() {
        synchronized (this) {
            _running = false;
            _game.finish();
            System.gc();
        }
    }

    /**
     * 設定遊戲畫面尺寸，遊戲引擎將跟取此設定的尺寸和實際螢幕尺寸
     * 對遊戲畫面進行縮放。
     *
     * @param width  預期的遊戲畫面寬度
     * @param height 預期的遊戲畫面高度
     */
    public void setGameFrameSize(int width, int height) {
        synchronized (this) {
            _gameFrame.setSize(width, height);
        }
    }

    /**
     * 設定遊戲畫面尺寸顯示比例，遊戲引擎將跟取此設定的尺寸和實際螢幕尺寸
     * 對遊戲畫面進行縮放。
     *
     * @param ratio 顯示比例
     */
    public void setDisplayRatio(float ratio) {
        synchronized (this) {
            _gameFrame.setDisplayRatio(ratio);
        }
    }

    /**
     * 處理按鍵被按下的事件(轉交目前的遊戲狀態)。
     *
     * @param keyCode 被按下的鍵
     */
    public void keyPressed(int keyCode) {
        synchronized (this) {
            if (!_paused) {
                _gameState.keyPressed(keyCode);
            }
        }
    }

    /**
     * 處理按鍵按下後被放開的事件(轉交目前的遊戲狀態)。
     *
     * @param keyCode 被放開的鍵
     */
    public void keyReleased(int keyCode) {
        synchronized (this) {
            if (!_paused) {
                _gameState.keyReleased(keyCode);
            }
        }
    }

    /**
     * 通知遊戲狀態，現在遊戲被暫停中。
     */
    public void pause() {
        synchronized (this) {
            _paused = true;
            _gameState.pause();
        }
    }

    /**
     * 註冊新的遊戲遊戲狀態
     *
     * @param id    遊戲狀態ID (須在整個遊戲中是唯一的)
     * @param state 遊戲狀態
     */
    public void registerGameState(int id, GameState state) {
        _gameStates.put(id, state);
    }

    /**
     * 釋放所有資源。
     */
    public void release() {
        synchronized (this) {
            _running = false;
            _gameState.release();
        }
    }

    /**
     * 通知遊戲狀態，現在遊戲恢復執行。
     */
    public void resume() {
        synchronized (this) {
            _paused = false;
            _gameState.resume();
        }
    }

    /**
     * 在獨立的執行緒中不斷地呼叫遊戲狀態更新畫面。
     */
    @Override
    public void run() {
        while (_running) {
            try {
                long start = 0, timeToSleep = 0;
                canvas = _surface.lockCanvas();
                synchronized (this) {
                    start = System.currentTimeMillis();
                    updateFrame();
                    timeToSleep = postUpdateFrame(start, System.currentTimeMillis());
                }
                waitUntil(timeToSleep);
            } catch (Throwable t) {
                addDebugInfo(t.getMessage());
            } finally {
                // 即使發生任何例外，還是需要釋放螢幕的鎖定，所以放在finally的區塊內
                if (canvas != null) {
                    _surface.unlockCanvasAndPost(canvas);
                    canvas = null;
                }
            }
        }
    }

    /**
     * 取得遊戲現在遊戲狀態的代碼。
     *
     * @return 現在遊戲狀態的代碼
     */
    public int getCurrentState() {
        return _currentState;
    }

    /**
     * 取得遊戲現在的遊戲狀態。
     *
     * @return 現在的遊戲狀態
     */
    public GameState getGameState() {
        synchronized (this) {
            return _gameState;
        }
    }

    /**
     * 取得Touch事件的聆聽者({@link EventDispatcher}先接收事件根據遊戲
     * 畫面的大小換算座標後，再轉送給目前的遊戲狀態)。
     *
     * @return Touch事件聆聽者
     */
    public OnTouchListener getOnTouchListener() {
        return _eventDispatcher;
    }

    /**
     * 取得Sensor事件的聆聽者({@link SensorEventDispatcher}先接收事件，轉換
     * 座標系統後，再轉送給目前的遊戲狀態)。
     *
     * @return Sensor事件聆聽者
     */
    public SensorEventListener getSensorEventListener() {
        return _eventDispatcher;
    }

    /**
     * 取得畫面事件的處理函式。
     *
     * @return 畫面事件的處理函式
     */
    public SurfaceHolder.Callback getSurfaceHolderCallback() {
        return _surfaceEventHandler;
    }

    /**
     * 取得遊戲引擎目前是否暫停(畫面依舊更新，但不轉送事件及呼叫{@link GameState#move()})中。
     *
     * @return true表示引擎目前暫停中
     */
    public boolean isPaused() {
        return _paused;
    }

    /**
     * 取得執行遊戲引擎的執行緒是否還在執行中。
     *
     * @return true表示引擎的執行緒還在執行中
     */
    public boolean isRunning() {
        return _running;
    }

    /**
     * 設定遊戲畫面更新率(單位：頁/秒)。
     *
     * @param rate 畫面更新率
     */
    public void setFrameRate(int rate) {
        _refreshTime = ONE_SECOND / rate;
    }

    /**
     * 將遊戲切換到另外一個遊戲狀態，但沒有任何而外的參數給新遊戲狀態。
     *
     * @param state 新遊戲狀態的代碼
     */
    public void setGameState(int state) {
        setGameState(state, null);
    }

    /**
     * 將遊戲切換至另一個遊戲狀態，並給予新遊戲狀態額外的參數。
     *
     * @param state      新遊戲狀態的代碼
     * @param parameters 給新遊戲狀態的參數
     */
    public void setGameState(int state, Map<String, Object> parameters) {
        synchronized (this) {
            if (_currentState != state) {
                _currentState = state;
                // 如果有前一個遊戲狀態，請求釋放資源
                if (_gameState != null) {
                    _gameState.release();
                    _gameState = null;
                }
                _gameState = _gameStates.get(state) != null ? _gameStates.get(state) : _emptyState;
                _gameState.initialize(parameters);
            }
        }
    }

    /**
     * 設定執行緒是否可以開始執行並更新畫面。請注意：(1) 本函式必須在呼叫
     * {@link Thread#start()}前呼叫，並設定為true；(2) 在執行緒開始執行
     * 後，呼叫此函式並設定為false，則負責執行遊戲引擎的執行緒將立即結束，
     * 如果還有更新畫面的需求，需要再次建立一個新的執行緒實體來執行遊戲引擎。
     *
     * @param running true表示可以開始更新畫面
     */
    public void setRunning(boolean running) {
        _running = running;
    }

    /**
     * 更新螢幕畫面的尺寸。
     *
     * @param width  新的畫面寬度
     * @param height 新的畫面高度
     */
    public void setSurfaceSize(int width, int height) {
        synchronized (this) {
        }
    }

    /**
     * 取得遊戲畫面尺寸資訊。
     *
     * @return 遊戲畫面尺寸資訊
     */
    GameFrame getGameFrame() {
        return _gameFrame;
    }

    /**
     * 視需求初始化顯示文字需要的資源。
     */
    private void initializePaints() {
        (_backgroundPaint = new Paint()).setColor(Color.BLACK);
        (_normalPaint = new TextPaint()).setColor(Color.GREEN);
        (_warningPaint = new TextPaint()).setColor(Color.RED);
    }

    /**
     * 更新遊戲畫面。
     */
    private void updateFrame() {
        if (_gameState != null && canvas != null) {
            canvas.save();
            canvas.drawRect(0, 0, screenWidth, screenHeight, _backgroundPaint);
            canvas.translate(_gameFrame.getOriginX(), _gameFrame.getOriginY());
            canvas.scale(_gameFrame.getDisplayRatio(), _gameFrame.getDisplayRatio());
            if (!_paused) {
                _gameState.move();
            }
            _gameState.show();
            canvas.restore();
        }
    }

    /**
     * 更新遊戲畫面後的後處理(post-processing)。
     *
     * @param start 開始更新畫面的時間戳記
     * @param end   結束畫面更新的時間戳記
     * @return 處理完畫面更新後剩餘的時間
     */
    private long postUpdateFrame(long start, long end) {
        int used = (int) (end - start);
        updateDebugInfo();
        updateDeviceInfo(used);
        return _refreshTime - used;
    }

    /**
     * 如果畫面更新完還有剩餘的時間，讓引擎進入睡眠狀態直到指定的時間後醒來。
     *
     * @param sleepTime 睡眠時間
     */
    private void waitUntil(long sleepTime) {
        if (sleepTime > 0) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 更新螢幕上的除錯資訊。
     */
    private void updateDebugInfo() {
        if (showDebugInfo) {
            float x = 0;
            float y = LINE_HEIGHT;
            canvas.drawText(ENGINE_VERSION, x, y, _normalPaint);
            y += LINE_HEIGHT;
            if (_debugInfo.size() > 0) {
                for (String info : _debugInfo) {
                    canvas.drawText(info, x, y, _normalPaint);
                    y += LINE_HEIGHT;
                }
            } else {
                canvas.drawText(NO_DEBUG_INFO_HISTORY, x, y, _normalPaint);
            }
        }
    }

    /**
     * 更新螢幕上的裝置資訊。
     *
     * @param used 實際更新畫面所用的時間
     */
    private void updateDeviceInfo(int used) {
        if (showDeviceInfo) {
            int baseLine = screenHeight - FRAME_RATE_INFO_OFFSET;
            Paint paint = (used > _refreshTime) ? _warningPaint : _normalPaint;
            canvas.drawText(String.format(FRAME_TIME_INFO_FORMAT, used), 0, baseLine, paint);
            canvas.drawText(_gameFrame.getInfo(), 0, baseLine + RATIO_INFO_OFFSET, paint);
            canvas.drawText(_eventDispatcher.getOrientationInfo(), 0, baseLine + ORIENTATION_INFO_OFFSET, paint);
            canvas.drawText(_eventDispatcher.getAccelerationIfno(), 0, baseLine + ACCELERATION_INFO_OFFSET, paint);
        }
    }
}
