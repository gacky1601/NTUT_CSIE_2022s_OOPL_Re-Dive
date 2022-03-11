package tw.edu.ntut.csie.game;

import android.view.KeyEvent;

/**
 * 對鍵盤事件有興趣的類別，可以實作<code>KeyEventHandler</code>介面來接收及
 * 處理遊戲引擎轉送的鍵盤事件。
 *
 * @author <a href="http://www.csie.ntut.edu.tw/labsdtl/">Lab SDT</a>
 * @version 2.0
 * @since 2.0
 */
public interface KeyEventHandler {

    /**
     * 上方向鍵的鍵值
     */
    public static final int KEY_UP = KeyEvent.KEYCODE_DPAD_UP;

    /**
     * 下方向鍵的鍵值
     */
    public static final int KEY_DOWN = KeyEvent.KEYCODE_DPAD_DOWN;

    /**
     * 左方向鍵的鍵值
     */
    public static final int KEY_RIGHT = KeyEvent.KEYCODE_DPAD_RIGHT;

    /**
     * 右方向鍵的鍵值
     */
    public static final int KEY_LEFT = KeyEvent.KEYCODE_DPAD_LEFT;

    /**
     * 選擇鍵的鍵值
     */
    public static final int KEY_SELECT = KeyEvent.KEYCODE_DPAD_CENTER;

    /**
     * 處理按鍵被按下的事件。
     *
     * @param keyCode 被按下的鍵值
     */
    public void keyPressed(int keyCode);

    /**
     * 處理按鍵被放開的事件。
     *
     * @param keyCode 被放開的鍵值
     */
    public void keyReleased(int keyCode);
}
