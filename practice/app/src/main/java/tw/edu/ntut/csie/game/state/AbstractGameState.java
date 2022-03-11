package tw.edu.ntut.csie.game.state;

import java.util.ArrayList;
import java.util.List;

import tw.edu.ntut.csie.game.GameObject;
import tw.edu.ntut.csie.game.KeyEventHandler;
import tw.edu.ntut.csie.game.Pointer;
import tw.edu.ntut.csie.game.PointerEventHandler;
import tw.edu.ntut.csie.game.ReleasableResource;
import tw.edu.ntut.csie.game.engine.GameEngine;

/**
 * <code>AbstractGameState</code>實作{@link GameState}大部分的介面，提供簡單的
 * 預設實作和輔助函式，可作為遊戲開發者擴充遊戲狀態的起點。
 *
 * @author <a href="http://www.csie.ntut.edu.tw/labsdtl/">Lab SDT</a>
 * @version 2.0
 * @since 2.0
 */
public abstract class AbstractGameState extends GameState {

    /**
     * 管理{@link GameObject}、{@link KeyEventHandler}、{@link ReleasableResource}和
     * {@link PointerEventHandler}。
     */
    private List<GameObject> _objects;
    private List<KeyEventHandler> _keyHandlers;
    private List<ReleasableResource> _resources;
    private List<PointerEventHandler> _pointerHandlers;

    /**
     * 建構一個<code>AbstractGameState</code>實體。
     *
     * @param engine 執行狀態處理者的引擎
     */
    public AbstractGameState(GameEngine engine) {
        super(engine);
        _objects = new ArrayList<GameObject>();
        _keyHandlers = new ArrayList<KeyEventHandler>();
        _resources = new ArrayList<ReleasableResource>();
        _pointerHandlers = new ArrayList<PointerEventHandler>();
    }

    /**
     * 更換狀態處理者。
     *
     * @param state 新狀態處理者的代碼
     */
    public void changeState(int state) {
        _engine.setGameState(state);
    }

    public void keyPressed(int keyCode) {
        notifyKeyPressedEvent(keyCode);
    }

    public void keyReleased(int keyCode) {
        notifyKeyReleasedEvent(keyCode);
    }

    @Override
    public void move() {
        moveAllGameObjects();
    }

    @Override
    public void show() {
        showAllGameObjects();
    }

    @Override
    public void release() {
        releaseAllResources();
    }

    @Override
    public boolean pointerPressed(Pointer actionPointer, List<Pointer> pointers) {
        return notifyPointerPressedEvent(actionPointer, pointers);
    }

    @Override
    public boolean pointerMoved(Pointer actionPointer, List<Pointer> pointers) {
        return notifyPointerMovedEvent(actionPointer, pointers);
    }

    @Override
    public boolean pointerReleased(Pointer actionPointer, List<Pointer> pointers) {
        return notifyPointerReleasedEvent(actionPointer, pointers);
    }

    @Override
    public void orientationChanged(float pitch, float azimuth, float roll) {
    }

    @Override
    public void accelerationChanged(float dX, float dY, float dZ) {
    }

    /**
     * 將指定的{@link ReleasableResource}加到清理清單中，當狀態處理
     * 者的{@link #release()}被呼叫時，會依序呼叫清理清單中所有物件的
     * {@link #release()}函式。
     *
     * @param resource 指定的資源物件
     */
    protected void addReleasableResource(ReleasableResource resource) {
        if (!_resources.contains(resource)) {
            _resources.add(resource);
        }
    }

    /**
     * 對清理清單中的所有資源呼叫{@link ReleasableResource#release()}
     * 函式以釋放所有已分配的資源。
     */
    protected void releaseAllResources() {
        for (ReleasableResource resource : _resources) {
            resource.release();
        }
        _objects.clear();
        _resources.clear();
        _keyHandlers.clear();
        _pointerHandlers.clear();
    }

    /**
     * 將指定的{@link GameObject}加到清單中，當狀態處理者的{@link #move()}或
     * {@link #show()}被呼叫時，會依序呼叫清理清單中所有物件的{@link #move()}
     * 或{@link #show()}函式。注意，當一個{@link GameObject}實體被加入時，亦會
     * 自動加到自動清理清單中。
     *
     * @param object 可以在畫面上顯示和移動的物件
     */
    protected void addGameObject(GameObject object) {
        if (!_objects.contains(object)) {
            _objects.add(object);
        }
        addReleasableResource(object);
    }

    /**
     * 將指定的{@link GameObject}從清單中移除。
     *
     * @param object 可以在畫面上顯示和移動的物件
     */
    protected void removeGameObject(GameObject object) {
        _objects.remove(object);
    }

    /**
     * 移動清單中所有的物件。
     */
    protected void moveAllGameObjects() {
        for (GameObject object : _objects) {
            object.move();
        }
    }

    /**
     * 顯示清單中所有的物件。
     */
    protected void showAllGameObjects() {
        for (GameObject object : _objects) {
            object.show();
        }
    }

    /**
     * 將指定的{@link PointerEventHandler}處理者加到通知清單中。
     *
     * @param handler 指定的處理者
     */
    protected void addPointerEventHandler(PointerEventHandler handler) {
        if (!_pointerHandlers.contains(handler)) {
            _pointerHandlers.add(handler);
        }
    }

    /**
     * 將指定的{@link PointerEventHandler}處理者從通知清單中移除。
     *
     * @param handler 指定的處理者
     */
    protected void removePointerEventHandler(PointerEventHandler handler) {
        _pointerHandlers.remove(handler);
    }

    /**
     * 通知所有已註冊的{@link PointerEventHandler}指標點被按下的事件。
     *
     * @param pointers 指標點按下的位置
     * @return true表示該事件已經被處理完畢，不用再繼續傳遞下去。
     */
    protected boolean notifyPointerPressedEvent(Pointer actionPointer, List<Pointer> pointers) {
        for (PointerEventHandler handler : _pointerHandlers) {
            if (handler.pointerPressed(actionPointer, pointers)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 通知所有已註冊的{@link PointerEventHandler}指標點已移動的事件。
     *
     * @param pointers 指標點移動後的位置
     * @return true表示該事件已經被處理完畢，不用再繼續傳遞下去。
     */
    protected boolean notifyPointerMovedEvent(Pointer actionPointer, List<Pointer> pointers) {
        for (PointerEventHandler handler : _pointerHandlers) {
            if (handler.pointerMoved(actionPointer, pointers)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 通知所有已註冊的{@link PointerEventHandler}指標點放開的事件。
     *
     * @param pointers 指標點放開的位置
     * @return true表示該事件已經被處理完畢，不用再繼續傳遞下去。
     */
    protected boolean notifyPointerReleasedEvent(Pointer actionPointer, List<Pointer> pointers) {
        for (PointerEventHandler handler : _pointerHandlers) {
            if (handler.pointerReleased(actionPointer, pointers)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 將指定的{@link KeyEventHandler}處理者加到通知清單中。
     *
     * @param handler 指定的處理者
     */
    protected void addKeyEventHandler(KeyEventHandler handler) {
        if (!_keyHandlers.contains(handler)) {
            _keyHandlers.add(handler);
        }
    }

    /**
     * 將指定的{@link KeyEventHandler}處理者從通知清單中移除。
     *
     * @param handler 指定的處理者
     */
    protected void removeKeyEventHandler(KeyEventHandler handler) {
        _keyHandlers.remove(handler);
    }

    /**
     * 通知所有已註冊的{@link KeyEventHandler}按鍵被按下的事件。
     *
     * @param keyCode 被按下的鍵碼
     */
    protected void notifyKeyPressedEvent(int keyCode) {
        for (KeyEventHandler handler : _keyHandlers) {
            handler.keyPressed(keyCode);
        }
    }

    /**
     * 通知所有已註冊的{@link KeyEventHandler}按鍵被放開的事件。
     *
     * @param keyCode 被放開的鍵碼
     */
    protected void notifyKeyReleasedEvent(int keyCode) {
        for (KeyEventHandler handler : _keyHandlers) {
            handler.keyReleased(keyCode);
        }
    }
}
