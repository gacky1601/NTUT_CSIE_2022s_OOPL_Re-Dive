package prince.connect.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.IOException;

import prince.connect.GameObject;

import static prince.connect.GameView.runtime;
import static prince.connect.engine.GameEngine.canvas;

/**
 * <code>MovingBitmap</code>用來顯示螢幕上會移動的圖片。
 *
 * @author <a href="http://www.csie.ntut.edu.tw/labsdtl/">Lab SDT</a>
 * @version 2.0
 * @since 1.0
 */
public class MovingBitmap implements GameObject {

    /**
     * 圖片於遊戲畫面上的座標，這並非非螢幕上的座標，實際座標將由遊戲引擎
     * 根據螢幕尺寸轉換。
     */
    private int _x;
    private int _y;

    /**
     * 設定圖片顯示與否。
     */
    private boolean _visible;

    /**
     * 圖片所使用的系統層資源。
     */
    private Bitmap _bitmap;
    //new new new new new new new new new new new new new new new new new new new new new new new new
    //private Bitmap _bitmapBackUp;
    private Matrix _matrixShow = new Matrix();
    private Matrix _matrixTranslate = new Matrix();
    private Matrix _matrixScale = new Matrix();
    private Matrix _matrixRotate = new Matrix();

    private int _width;
    private int _height;

    /**
     * 建立一個空的<code>MovingBitmap</code>物件，在{@link #show()}顯示前，
     * 需呼叫{@link #loadBitmap(int)}或{@link #loadBitmap(String)}載入欲顯
     * 示的圖片，否則會擲出{@link }例外。
     */
    public MovingBitmap() {
        _visible = true;
    }

    /**
     * 建立<code>MovingBitmap</code>物件並立即載入指定的圖片。
     *
     * @param resId 圖片的來源
     */
    public MovingBitmap(int resId) {
        _visible = true;
        loadBitmap(resId);
    }

    /**
     * 建立<code>MovingBitmap</code>物件並立即載入指定的圖片。
     *
     * @param filename 圖片的檔名
     */
    public MovingBitmap(String filename) {
        _visible = true;
        loadBitmap(filename);
    }

    /**
     * 建立<code>MovingBitmap</code>物件並立即載入指定的圖片。
     *
     * @param resId 圖片的來源
     * @param x     圖片的X座標
     * @param x     圖片的Y座標
     */
    public MovingBitmap(int resId, int x, int y) {
        _visible = true;
        loadBitmap(resId);
        setLocation(x, y);
    }

    /**
     * 建立<code>MovingBitmap</code>物件並立即載入指定的圖片。
     *
     * @param filename 圖片的檔名
     * @param x        圖片的X座標
     * @param x        圖片的Y座標
     */
    public MovingBitmap(String filename, int x, int y) {
        _visible = true;
        loadBitmap(filename);
        setLocation(x, y);
    }

    /**
     * 從指定的Android資源ID載入圖片。
     *
     * @param resId 圖片來源
     */
    public void loadBitmap(int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inTargetDensity = 1;
        _bitmap = BitmapFactory.decodeResource(runtime.getResources(), resId, options);
        //new new new new new new new new new new new new new new new new new new new new new new new new
        //_bitmapBackUp = BitmapFactory.decodeResource(runtime.getResources(), resId, options);
        _width = _bitmap.getWidth();
        _height = _bitmap.getHeight();
    }

    /**
     * 從指定的檔名載入圖片。
     *
     * @param fileName 圖片來源
     */
    public void loadBitmap(String fileName) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            options.inTargetDensity = 1;
            _bitmap = BitmapFactory.decodeStream(runtime.getAssets().open(fileName), null, options);
            //new new new new new new new new new new new new new new new new new new new new new new new new
            //_bitmapBackUp = BitmapFactory.decodeStream(runtime.getAssets().open(fileName), null, options);
            _width = _bitmap.getWidth();
            _height = _bitmap.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void move() {
    }

    @Override
    public void release() {
        _bitmap.recycle();
        _bitmap = null;
    }

    //new new new new new new new new new new new new new new new new new new new new new new new new
    //new new new new new new new new new new new new new new new new new new new new new new new new
    @Override
    public void show() {
        if (_visible) {
            _matrixShow.reset();
            _matrixShow.postConcat(_matrixRotate);
            _matrixShow.postConcat(_matrixScale);
            _matrixShow.postConcat(_matrixTranslate);
            canvas.drawBitmap(_bitmap, _matrixShow, null);
        }
    }
    /**
     * 設定圖片在遊戲畫面上顯示的位置。
     *
     * @param x 新位置的x座標
     * @param y 新位置的y座標
     */
    public void setLocation(int x, int y) {
        _x = x;
        _y = y;
        _matrixTranslate.setTranslate(x, y);
    }
    /**
     * 將圖片縮放到指定的大小。
     *
     * @param width  新的圖片寬度
     * @param height 新的圖片高度
     */
    public void resize(int width, int height) {
        width = (width <= 0) ? 1 : width;
        height = (height <= 0) ? 1 : height;
        _width = width;
        _height = height;
        _matrixScale.setScale((float) width/_bitmap.getWidth(), (float) height/_bitmap.getHeight());
    }
    public void rotate(int degree) {
        _matrixRotate.setRotate(degree,_bitmap.getWidth()/2,_bitmap.getHeight()/2);
    }
    //new new new new new new new new new new new new new new new new new new new new new new new new
    //new new new new new new new new new new new new new new new new new new new new new new new new

    /**
     * 取得圖片在遊戲畫面上顯示位置的x座標。
     *
     * @return 圖片在遊戲畫面上顯示位置的x座標
     */
    public int getX() {
        return _x;
    }

    /**
     * 取得圖片在遊戲畫面上顯示位置的y座標。
     *
     * @return 圖片在遊戲畫面上顯示位置的y座標
     */
    public int getY() {
        return _y;
    }

    /**
     * 取得圖片在遊戲畫面上的寬度。
     *
     * @return 圖片在遊戲畫面上的寬度
     */
    public int getWidth() {
        return _width;
    }

    /**
     * 取得圖片在遊戲畫面上的高度。
     *
     * @return 圖片在遊戲畫面上的高度
     */
    public int getHeight() {
        return _height;
    }

    /**
     * 設定圖片顯示與否。
     *
     * @param visible true代表顯示，false代表隱藏
     */
    public void setVisible(boolean visible) {
        _visible = visible;
    }
}