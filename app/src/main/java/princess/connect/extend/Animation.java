package princess.connect.extend;

import java.util.ArrayList;

import princess.connect.GameObject;
import princess.connect.core.MovingBitmap;

/**
 * <code>Animation</code>用播放一連串的圖片的方式來呈現一段動畫。
 *
 * @author <a href="http://www.csie.ntut.edu.tw/labsdtl/">Lab SDT</a>
 * @version 2.0
 * @since 1.0
 */
public class Animation implements GameObject {

    /**
     * 預設畫面與畫面之間的延遲數(沒有延遲)
     */
    public static final int DEFAULT_DELAY = 0;

    /**
     * 遊戲畫面上的位置。
     */
    private int _x;
    private int _y;

    /**
     * 控制動畫播放的內部狀態。
     */
    private int _delay = 0;
    private int _counter = 0;
    private int _frameIndex = 0;

    /**
     * 控制動畫顯示與重複與否的旗標。
     */
    private boolean _visible = true;
    private boolean _repeating = true;

    /**
     * 實際用來繪製動畫的畫格。
     */
    private ArrayList<MovingBitmap> _frames;

    /**
     * 使用預設的設定建立一段動畫.
     */
    public Animation() {
        this(DEFAULT_DELAY);
    }

    /**
     * 使用指定的畫面之間的延遲次數建立一段動畫。
     *
     * @param delay 畫面之間的延遲次數
     */
    public Animation(int delay) {
        setDelay(delay);
        _frames = new ArrayList<MovingBitmap>();
    }

    /**
     * 將指定的資源載入並加到動畫中成為最後一個畫格。
     *
     * @param resId 新的畫格
     */
    public void addFrame(int resId) {
        _frames.add(new MovingBitmap(resId));
    }

    /**
     * 將指定的檔名載入並加到動畫中成為最後一個畫格。
     *
     * @param filename 新的畫格
     */
    public void addFrame(String filename) {
        _frames.add(new MovingBitmap(filename));
    }

    /**
     * 將指定的<code>MovingBitmap</code>加到動畫中成為最後一個畫格。
     *
     * @param bitmap 新的畫格
     */
    public void addFrame(MovingBitmap bitmap) {
        _frames.add(bitmap);
    }

    /**
     * 根據延遲次數的設定，切換到下一個畫格。
     */
    public void move() {
        if (!_visible) {
            return;
        }
        if (--_counter <= 0) {
            _counter = _delay;
            // 目前正在播放中
            if (!isLastFrame() && _frameIndex >= 0) {
                _frameIndex++;
            } else if (!_repeating) {
                // 不重複播放
                _frameIndex = -1;
            } else {
                reset();
            }
        }
    }

    public void release() {
        for (MovingBitmap frame : _frames) {
            frame.release();
        }
        _frames.clear();
        _frames = null;
    }

    /**
     * 將動畫重置回到第一個畫格。
     */
    public void reset() {
        _frameIndex = 0;
    }

    /**
     * 顯示目前的畫格。
     */
    public void show() {
        if (!_visible) {
            return;
        }
        if (_frameIndex >= 0) {
            _frames.get(_frameIndex).setLocation(_x, _y);
            _frames.get(_frameIndex).show();
        }
    }

    /**
     * 取得目前顯示的畫格是否為動畫中的最後一個畫格。
     *
     * @return true如果目前的畫格是動畫中的最後一個畫格
     */
    public boolean isLastFrame() {
        return _frameIndex == _frames.size() - 1;
    }

    /**
     * 取得目前畫格在整個動畫中的索引值。
     *
     * @return 目前畫格的索引值
     */
    public int getCurrentFrameIndex() {
        return _frameIndex;
    }

    /**
     * 取得動畫中的畫格總數量。
     *
     * @return 動畫中的畫格總數量
     */
    public int getFrameCount() {
        return _frames.size();
    }

    /**
     * 取得動畫的高度
     *
     * @return 高度
     */
    public int getHeight() {
        return _frames.get(_frameIndex).getHeight();
    }

    /**
     * 取得動畫的寬度
     *
     * @return 寬度
     */
    public int getWidth() {
        return _frames.get(_frameIndex).getWidth();
    }

    /**
     * 取得動畫在螢幕上的x座標
     *
     * @return 在螢幕上的x座標
     */
    public int getX() {
        return _x;
    }

    /**
     * 取得動畫在螢幕上的y座標
     *
     * @return 在螢幕上的y座標
     */
    public int getY() {
        return _y;
    }

    /**
     * 將動畫切換到指定的畫格。指定的數值必須大於0且小於總畫格數。
     *
     * @param index 指定的畫格
     */
    public void setCurrentFrameIndex(int index) {
        if (index < _frames.size() && _frameIndex >= 0) {
            _frameIndex = index;
        }
    }

    /**
     * 設定畫面與畫面之間延遲的次數，動畫的播放是透過不斷呼叫{@link #move()}的方式
     * 切換畫格，所指定的數字即現在顯示的畫格會在{@link #move()}中被保留不切換到下
     * 一個畫格的次數。
     *
     * @param delay 會面間延遲的次數
     */
    public void setDelay(int delay) {
        _delay = delay;
        _counter = _delay;
    }

    /**
     * 設定動畫在螢幕上顯示的位置。
     *
     * @param x 螢幕上的x座標
     * @param y 螢幕上的x座標
     */
    public void setLocation(int x, int y) {
        _x = x;
        _y = y;
    }

    /**
     * 設定是否不斷地重複播放動畫。
     *
     * @param repeating true表示重複播放；false表示只播放一次
     */
    public void setRepeating(boolean repeating) {
        _repeating = repeating;
    }

    /**
     * 設定顯示與否。
     *
     * @param visible true表示顯示，false表示隱藏
     */
    public void setVisible(boolean visible) {
        _visible = visible;
    }

    public void inversion() {
        for (MovingBitmap frame : _frames) {
            frame.inversion();
        }
    }
}
