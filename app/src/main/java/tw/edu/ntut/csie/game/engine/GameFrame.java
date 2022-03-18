package tw.edu.ntut.csie.game.engine;

import static tw.edu.ntut.csie.game.GameView.screenHeight;
import static tw.edu.ntut.csie.game.GameView.screenWidth;

/**
 * <code>GameFrame</code>紀錄遊戲預期的畫面尺寸與計算在實際螢幕上
 * 顯示的尺寸。
 *
 * @author <a href="http://www.csie.ntut.edu.tw/labsdtl/">Lab SDT</a>
 * @version 2.0
 * @since 2.0
 */
class GameFrame {

    /**
     * 預設的遊戲畫面投射比例，1.0表示不進行對實際螢幕尺寸進行遊戲畫面的縮放。
     */
    public static final float DISPLAY_RATIO = 1.0f;

    private static final String RATIO_INFO_FORMAT = "Ratio: %1$.2f [%2$d x %3$d] to [%4$d x %5$d]";

    /**
     * 遊戲畫面的尺寸。
     */
    private int _width;
    private int _height;

    /**
     * 目前遊戲畫面的投射比例。
     */
    private float _displayRatio;

    /**
     * 遊戲畫面原點在螢幕上實際的座標。
     */
    private float _originX;
    private float _originY;

    /**
     * 遊戲畫面經縮小/放大後的尺寸。
     */
    private float _scaledWidth;
    private float _scaledHeight;

    private String _info;

    /**
     * 用實際螢幕尺寸建立遊戲畫面尺寸。
     */
    GameFrame() {
        _width = screenWidth;
        _height = screenHeight;
        setDisplayRatio(DISPLAY_RATIO);
    }

    /**
     * 用指定的寬度及高度建立遊戲畫面尺寸。
     *
     * @param width  指定的寬度
     * @param height 指定的高度
     */
    GameFrame(int width, int height) {
        setSize(width, height);
    }

    /**
     * 取得遊戲畫面的原始寬度。
     *
     * @return 遊戲畫面的原始寬度
     */
    public int getWidth() {
        return _width;
    }

    /**
     * 取得遊戲畫面的原始高度。
     *
     * @return 遊戲畫面的原始高度
     */
    public int getHeight() {
        return _height;
    }

    /**
     * 將螢幕上的X座標轉換成遊戲畫面中的X座標
     *
     * @param x 螢幕上的X座標
     * @return 遊戲畫面中的X座標
     */
    public int getXOnGameFrame(float x) {
        float shiftedX = (x - _originX) / _displayRatio;
        return (int) ((shiftedX < 0) ? 0 : ((shiftedX > _width) ? _width : shiftedX));
    }

    /**
     * 將螢幕上的Y座標轉換成遊戲畫面中的Y座標
     *
     * @param y 螢幕上的Y座標
     * @return 遊戲畫面中的Y座標
     */
    public int getYOnGameFrame(float y) {
        float shiftedY = (y - _originY) / _displayRatio;
        return (int) ((shiftedY < 0) ? 0 : ((shiftedY > _height) ? _height : shiftedY));
    }

    /**
     * 取得目前遊戲畫面的投射比例。
     *
     * @return 遊戲畫面的投射比例
     */
    public float getDisplayRatio() {
        return _displayRatio;
    }

    /**
     * 取得遊戲畫面原點在螢幕上實際的X座標。
     *
     * @return 遊戲畫面原點在螢幕上實際的X座標
     */
    public float getOriginX() {
        return _originX;
    }

    /**
     * 取得遊戲畫面原點在螢幕上實際的Y座標。
     *
     * @return 遊戲畫面原點在螢幕上實際的Y座標
     */
    public float getOriginY() {
        return _originY;
    }

    /**
     * 取得遊戲畫面實際顯示寬度。
     *
     * @return 遊戲畫面實際顯示寬度
     */
    public float getScaledWidth() {
        return _scaledWidth;
    }

    /**
     * 取得遊戲畫面實際顯示高度。
     *
     * @return 遊戲畫面實際顯示高度
     */
    public float getScaledHeight() {
        return _scaledHeight;
    }

    /**
     * 取得遊戲畫面尺寸資訊。
     *
     * @return 遊戲畫面尺寸資訊
     */
    public String getInfo() {
        return _info;
    }

    /**
     * 使用指定的顯示比例更新遊戲畫面顯示尺寸。
     *
     * @param ratio 新的顯示比例
     */
    void setDisplayRatio(float ratio) {
        _displayRatio = ratio;
        _scaledWidth = _width * _displayRatio;
        _scaledHeight = _height * _displayRatio;
        _originX = (screenWidth - _scaledWidth) / 2.0f;
        _originY = (screenHeight - _scaledHeight) / 2.0f;
        _info = String.format(RATIO_INFO_FORMAT, _displayRatio, _width, _height, (int) _scaledWidth, (int) _scaledHeight);
    }

    /**
     * 使用指定的尺寸更新遊戲畫面顯示尺寸。
     *
     * @param width  新的遊戲畫面寬度
     * @param height 新的遊戲畫面高度
     */
    void setSize(int width, int height) {
        _width = width;
        _height = height;
        float widthRatio = (float) screenWidth / (float) _width;
        float heightRatio = (float) screenHeight / (float) _height;
        setDisplayRatio(Math.min(widthRatio, heightRatio));
    }
}
