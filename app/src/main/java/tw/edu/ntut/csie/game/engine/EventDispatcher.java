package tw.edu.ntut.csie.game.engine;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import tw.edu.ntut.csie.game.Pointer;

/**
 * <code>EventDispatcher</code>是{@link GameEngine}的輔助類別，用以接收事件、
 * 進行座標轉換和轉送事件給目前遊戲的狀態處理者。
 *
 * @author <a href="http://www.csie.ntut.edu.tw/labsdtl/">Lab SDT</a>
 * @version 2.0
 * @since 2.0
 */
class EventDispatcher implements OnTouchListener, SensorEventListener {

    /**
     * 定位與加速度資訊的字串顯示格式。
     */
    private static final String ORIENTATION_INFO_FORMAT = "Orientation [%1$.1f, %2$.1f, %3$.1f]";
    private static final String ACCELERATION_INFO_FORMAT = "Acceleration [%1$.1f, %2$.1f, %3$.1f]";

    /**
     * 定位與加速度資訊。
     */
    private String _orientationInfo;
    private String _accelerationInfo;

    /**
     * 委託的遊戲引擎。
     */
    private GameEngine _engine;

    /**
     * 建立<code>EventDispatcher</code>實體，僅供{@link GameEngine}使用。
     *
     * @param engine 委託的遊戲引擎
     */
    EventDispatcher(GameEngine engine) {
        _engine = engine;
        _orientationInfo = String.format(ORIENTATION_INFO_FORMAT, 0.0f, 0.0f, 0.0f);
        _accelerationInfo = String.format(ACCELERATION_INFO_FORMAT, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        synchronized (_engine) {
            int sensor = event.sensor.getType();
            float values[] = event.values;
            switch (sensor) {
                case Sensor.TYPE_ORIENTATION:
                    _orientationInfo = String.format(ORIENTATION_INFO_FORMAT, values[1], values[0], values[2]);
                    _engine.getGameState().orientationChanged(values[1], values[0], values[2]);
                    break;
                case Sensor.TYPE_ACCELEROMETER:
                    _accelerationInfo = String.format(ACCELERATION_INFO_FORMAT, values[0], values[1], values[2]);
                    _engine.getGameState().accelerationChanged(values[0], values[1], values[2]);
                    break;
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        synchronized (_engine) {
            if (_engine.isPaused() || _engine.getGameState() == null) {
                return false;
            }
            ArrayList<Pointer> pointers = getPointers(event);
            Pointer actionPointer = getActionPointer(event);
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    return _engine.getGameState().pointerPressed(actionPointer, pointers);
                case MotionEvent.ACTION_POINTER_DOWN:
                    return _engine.getGameState().pointerPressed(actionPointer, pointers);
                case MotionEvent.ACTION_MOVE:
                    return _engine.getGameState().pointerMoved(actionPointer, pointers);
                case MotionEvent.ACTION_POINTER_UP:
                    return _engine.getGameState().pointerReleased(actionPointer, pointers);
                case MotionEvent.ACTION_UP:
                    return _engine.getGameState().pointerReleased(actionPointer, pointers);
            }
            return true;
        }
    }

    /**
     * 取得定位資訊。
     *
     * @return 定位資訊
     */
    public String getOrientationInfo() {
        return _orientationInfo;
    }

    /**
     * 取得加速度資訊。
     *
     * @return 加速度資訊
     */
    public String getAccelerationIfno() {
        return _accelerationInfo;
    }

    /**
     * 將Android的事件轉換成Game Framework的{@link Pointer}。
     *
     * @param event 觸碰螢幕的事件
     * @return 經轉換的{@link Pointer}實體
     */
    private ArrayList<Pointer> getPointers(MotionEvent event) {

        ArrayList<Pointer> pointers = new ArrayList<Pointer>();
        for (int i = 0; i < event.getPointerCount(); i++) {
            int id = event.getPointerId(i);
            int x = _engine.getGameFrame().getXOnGameFrame(event.getX(i));
            int y = _engine.getGameFrame().getYOnGameFrame(event.getY(i));
            pointers.add(new Pointer(id, x, y));
        }
        Collections.sort(pointers, new Comparator<Pointer>() {
            @Override
            public int compare(Pointer lhs, Pointer rhs) {
                return (lhs.getID() == rhs.getID()) ? 0 : (lhs.getID() > rhs.getID() ? 1 : -1);
            }
        });
        return pointers;
    }


    /**
     * 取得觸發的Pointer
     *
     * @param event 觸碰螢幕的事件
     * @return 經轉換的{@link Pointer}實體
     */
    private Pointer getActionPointer(MotionEvent event) {
        int actionIndex = event.getActionIndex();
        int id = event.getPointerId(actionIndex);
        int x = _engine.getGameFrame().getXOnGameFrame(event.getX(actionIndex));
        int y = _engine.getGameFrame().getYOnGameFrame(event.getY(actionIndex));
        return new Pointer(id, x, y);
    }
}
