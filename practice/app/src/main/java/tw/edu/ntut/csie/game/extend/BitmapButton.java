package tw.edu.ntut.csie.game.extend;

import java.util.ArrayList;
import java.util.List;

import tw.edu.ntut.csie.game.GameObject;
import tw.edu.ntut.csie.game.Pointer;
import tw.edu.ntut.csie.game.PointerEventHandler;
import tw.edu.ntut.csie.game.core.MovingBitmap;

/**
 * <code>BitmapButton</code>處理指標點的事件，管理未被按下時、被按下時
 * 及當指標停留在上方但未被按下時的狀態，並根據不同狀態顯示不同的圖片，並
 * 在按鈕被放開時，通知已註冊的按鈕事件處理者。
 *
 * @author <a href="http://www.csie.ntut.edu.tw/labsdtl/">Lab SDT</a>
 * @version 2.0
 * @since 2.0
 */
public class BitmapButton implements GameObject, PointerEventHandler {

    /**
     * 按鈕的位置。
     */
    private int _x;
    private int _y;

    /**
     * 按鈕的狀態。
     */
    private boolean _pressed;
    private boolean _hovered;
    private boolean _visible;

    /**
     * 不同狀態的按鈕圖案。
     */
    private MovingBitmap _normalImage;
    private MovingBitmap _pressedImage;
    private MovingBitmap _hoveredImage;

    /**
     * 已註冊的按鈕事件處理者。
     */
    private List<ButtonEventHandler> _handlers;

    /**
     * 用指定的圖片資源ID，建立一個<code>BitmapButton</code>實體。
     *
     * @param normalImage 按鈕未被按下時顯示的圖片
     */
    public BitmapButton(int normalImage) {
        _hovered = false;
        _pressed = false;
        _visible = true;
        loadBitmap(normalImage);
        _handlers = new ArrayList<ButtonEventHandler>();
    }

    /**
     * 用指定的圖片檔案路徑，建立一個<code>BitmapButton</code>實體。
     *
     * @param normalImage 按鈕未被按下時顯示的圖片
     */
    public BitmapButton(String normalImage) {
        _hovered = false;
        _pressed = false;
        _visible = true;
        loadBitmap(normalImage);
        _handlers = new ArrayList<ButtonEventHandler>();
    }

    /**
     * 用指定的圖片資源ID，建立一個<code>BitmapButton</code>實體，
     * 並指定按鈕的位置。
     *
     * @param normalImage 按鈕未被按下時顯示的圖片
     * @param x           按鈕的X座標
     * @param y           按鈕的X座標
     */
    public BitmapButton(int normalImage, int x, int y) {
        this(normalImage);
        setLocation(x, y);
    }

    /**
     * 用指定的圖片檔案路徑，建立一個<code>BitmapButton</code>實體，
     * 並指定按鈕的位置。
     *
     * @param normalImage 按鈕未被按下時顯示的圖片
     * @param x           按鈕的X座標
     * @param y           按鈕的X座標
     */
    public BitmapButton(String normalImage, int x, int y) {
        this(normalImage);
        setLocation(x, y);
    }

    /**
     * 用指定的圖片資源ID，建立一個<code>BitmapButton</code>實體，
     * 並指定按鈕的位置。
     *
     * @param normalImage  按鈕未被按下時顯示的圖片
     * @param pressedImage 按鈕被按下時顯示的圖片
     * @param x            按鈕的X座標
     * @param y            按鈕的X座標
     */
    public BitmapButton(int normalImage, int pressedImage, int x, int y) {
        this(normalImage);
        loadPressedBitmap(pressedImage);
        setLocation(x, y);
    }

    /**
     * 用指定的圖片檔案路徑，建立一個<code>BitmapButton</code>實體，
     * 並指定按鈕的位置。
     *
     * @param normalImage  按鈕未被按下時顯示的圖片
     * @param pressedImage 按鈕被按下時顯示的圖片
     * @param x            按鈕的X座標
     * @param y            按鈕的X座標
     */
    public BitmapButton(String normalImage, String pressedImage, int x, int y) {
        this(normalImage);
        loadPressedBitmap(pressedImage);
        setLocation(x, y);
    }

    /**
     * 用指定的資源ID，載入按鈕未被按下時顯示的圖片。
     *
     * @param id 圖片資源ID
     */
    public void loadBitmap(int id) {
        if (_normalImage == null) {
            _normalImage = new MovingBitmap();
        }
        _normalImage.loadBitmap(id);
    }

    /**
     * 用指定的檔案路徑，載入按鈕未被按下時顯示的圖片。
     *
     * @param filename 圖片檔案路徑
     */
    public void loadBitmap(String filename) {
        if (_normalImage == null) {
            _normalImage = new MovingBitmap();
        }
        _normalImage.loadBitmap(filename);
    }

    /**
     * 用指定的資源ID，載入按鈕被按下時顯示的圖片。
     *
     * @param id 圖片資源ID
     */
    public void loadPressedBitmap(int id) {
        if (_pressedImage == null) {
            _pressedImage = new MovingBitmap();
        }
        _pressedImage.loadBitmap(id);
    }

    /**
     * 用指定的檔案路徑，載入按鈕被按下時顯示的圖片。
     *
     * @param filename 圖片檔案路徑
     */
    public void loadPressedBitmap(String filename) {
        if (_pressedImage == null) {
            _pressedImage = new MovingBitmap();
        }
        _pressedImage.loadBitmap(filename);
    }

    /**
     * 用指定的資源ID，載入當指標停留在按鈕上方但未按下時的圖片。
     *
     * @param id 圖片資源ID
     */
    public void loadHoverdBitmap(int id) {
        if (_hoveredImage == null) {
            _hoveredImage = new MovingBitmap();
        }
        _hoveredImage.loadBitmap(id);
    }

    /**
     * 用指定的檔案路徑，載入當指標停留在按鈕上方但未按下時的圖片。
     *
     * @param filename 圖片檔案路徑
     */
    public void loadHoveredBitmap(String filename) {
        if (_hoveredImage == null) {
            _hoveredImage = new MovingBitmap();
        }
        _hoveredImage.loadBitmap(filename);
    }

    /**
     * 將指定的按鍵事件處理者加入通知清單。
     *
     * @param handler 指定的按鍵事件處理者
     */
    public void addButtonEventHandler(ButtonEventHandler handler) {
        if (!_handlers.contains(handler)) {
            _handlers.add(handler);
        }
    }

    /**
     * 將指定的按鍵事件處理者從通知清單移出。
     *
     * @param handler 指定的按鍵事件處理者
     */
    public void removeButtonEventHandler(ButtonEventHandler handler) {
        _handlers.remove(handler);
    }

    @Override
    public boolean pointerPressed(Pointer actionPointer, List<Pointer> pointers) {
        if (pointers.size() == 1 && _visible) {
            Pointer pointer = pointers.get(0);
            if (pointer.getType() == Pointer.LEFT_MOUSE_BUTTON && contains(pointer)) {
                _pressed = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean pointerMoved(Pointer actionPointer, List<Pointer> pointers) {
        if (pointers.size() == 1 && _visible) {
            Pointer pointer = pointers.get(0);
            if (pointer.getType() == Pointer.HOVER) {
                _hovered = contains(pointer);
            }
        }
        return false;
    }

    @Override
    public boolean pointerReleased(Pointer actionPointer, List<Pointer> pointers) {
        _pressed = false;
        if (pointers.size() == 1 && _visible) {
            Pointer pointer = pointers.get(0);
            if (pointer.getType() == Pointer.LEFT_MOUSE_BUTTON && contains(pointer)) {
                notifyButtonEventHandlers();
                return true;
            }
        }
        return false;
    }

    @Override
    public void release() {
        releaseImage(_normalImage);
        releaseImage(_hoveredImage);
        releaseImage(_pressedImage);
        _normalImage = null;
        _hoveredImage = null;
        _pressedImage = null;
    }

    @Override
    public void move() {
    }

    @Override
    public void show() {
        if (!_visible) {
            return;
        }
        if (_pressed && _pressedImage != null) {
            _pressedImage.show();
        } else if (_hovered && _hoveredImage != null) {
            _hoveredImage.show();
        } else if (_normalImage != null) {
            _normalImage.show();
        }
    }

    /**
     * 設定按鈕的位置。
     *
     * @param x 按鈕的X座標
     * @param y 按鈕的Y座標
     */
    public void setLocation(int x, int y) {
        _x = x;
        _y = y;
        setImageLocation(_normalImage, x, y);
        setImageLocation(_hoveredImage, x, y);
        setImageLocation(_pressedImage, x, y);
    }

    /**
     * 設定顯示或隱藏與否。
     *
     * @param visible true代表顯示，false代表隱藏
     */
    public void setVisible(boolean visible) {
        _visible = visible;
    }

    /**
     * 判斷指定的指標點是否在按鈕上方。
     *
     * @param pointer 指定的指標點
     * @return true代表指定的座標在按鈕上方，反之false
     */
    private boolean contains(Pointer pointer) {
        return contains(pointer.getX(), pointer.getY());
    }

    /**
     * 判斷指定的座標是否在按鈕的上方。
     *
     * @param x 指定的X座標
     * @param y 指定的Y座標
     * @return true代表指定的座標在按鈕上方，反之false
     */
    private boolean contains(int x, int y) {
        return (x > _x && x < (_x + _normalImage.getWidth()) &&
                y > _y && y < (_y + _normalImage.getHeight()));
    }

    /**
     * 替指定的圖片檢查是否為null並設定位置。
     *
     * @param image 指定的圖片
     * @param x     新的X座標
     * @param y     新的X座標
     */
    private void setImageLocation(MovingBitmap image, int x, int y) {
        if (image != null) {
            image.setLocation(x, y);
        }
    }

    /**
     * 替指定的圖片檢查是否為null並釋放其已分配的資源。
     *
     * @param image 指定的圖片
     */
    private void releaseImage(MovingBitmap image) {
        if (image != null) {
            image.release();
        }
    }

    /**
     * 通知所有已註冊的按鈕事件處理者。
     */
    private void notifyButtonEventHandlers() {
        for (ButtonEventHandler handler : _handlers) {
            handler.perform(this);
        }
    }
}
