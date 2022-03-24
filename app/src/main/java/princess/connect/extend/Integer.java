package princess.connect.extend;

import princess.connect.GameObject;
import princess.connect.core.MovingBitmap;

/**
 * <code>Integer</code>用多張圖片顯示多位數的整數。
 *
 * @author <a href="http://www.csie.ntut.edu.tw/labsdtl/">Lab SDT</a>
 * @version 2.0
 * @since 1.0
 */
public class Integer implements GameObject {

    /**
     * 預設的整數顯示位數。
     */
    public static final int DEFAULT_DIGITS = 4;

    /**
     * 顯示整數的左上角座標。
     */
    private int _x;
    private int _y;

    /**
     * 顯示的值和位數。
     */
    private int _value;
    private int _digits;

    /**
     * 用來顯示0~9的及負號的圖片。
     */
    private MovingBitmap[] _digitImages;

    /**
     * 使用預設值建立一個<code>Integer</code>物件。
     */
    public Integer() {
        this(DEFAULT_DIGITS);
    }

    /**
     * 使用指定的顯示位數建立一個<code>Integer</code>物件。
     *
     * @param digits 欲顯示的位數
     */
    public Integer(int digits) {
        this(digits, 0, 0, 0);
    }

    /**
     * 使用指定的顯示位數、初始值和初始位置，建立一個<code>Integer</code>物件。
     *
     * @param digits    欲顯示的位數
     * @param initValue 初始值
     * @param x         初始位置的X座標
     * @param y         初始位置的X座標
     */
    public Integer(int digits, int initValue, int x, int y) {
        initialize();
        setDigits(digits);
        setLocation(x, y);
        _value = initValue;
    }

    /**
     * 對目前顯示的數值加上指定的值。
     *
     * @param addend 加數(目前的值為被加數)
     */
    public void add(int addend) {
        _value += addend;
    }

    @Override
    public void move() {
    }

    @Override
    public void release() {
        for (int i = 0; i < 11; i++) {
            _digitImages[i].release();
        }
    }

    @Override
    public void show() {
        int nx;
        int MSB;
        if (_value >= 0) {
            MSB = _value;
            nx = _x + _digitImages[0].getHeight() * (_digits - 1);
        } else {
            MSB = -_value;
            nx = _x + _digitImages[0].getWidth() * _digits;
        }
        for (int i = 0; i < _digits; i++) {
            int d = MSB % 10;
            MSB /= 10;
            _digitImages[d].setLocation(nx, _y);
            _digitImages[d].show();
            nx -= _digitImages[d].getWidth();
        }
        if (_value < 0) {
            _digitImages[10].setLocation(nx, _y);
            _digitImages[10].show();
        }
    }

    /**
     * 對目前顯示的數值減去指定的值。
     *
     * @param addend 減數(目前的值為被減數)
     */
    public void subtract(int subtrahend) {
        _value -= subtrahend;
    }

    /**
     * 變更顯示的位數。
     *
     * @param digits 新的顯示位數
     */
    public void setDigits(int digits) {
        _digits = digits;
    }

    /**
     * 設定最高位數字的顯示位置，其他位數會依據每個數字的圖片大小依序排列顯示。
     *
     * @param x 顯示位置的x座標
     * @param y 顯示位置的x座標
     */
    public void setLocation(int x, int y) {
        _x = x;
        _y = y;
    }

    /**
     * 設定欲顯示的整數數值。
     *
     * @param value 新的整數值
     */
    public void setValue(int value) {
        _value = value;
    }

    /**
     * 取得目前顯示的整數數值。
     *
     * @return 整數值
     */
    public int getValue() {
        return _value;
    }

    /**
     * 進行初始化。
     */
    private void initialize() {
        _digitImages = new MovingBitmap[11];

    }
}
