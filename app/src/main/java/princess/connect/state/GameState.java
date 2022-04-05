package princess.connect.state;

import java.util.Map;

import princess.connect.GameObject;
import princess.connect.KeyEventHandler;
import princess.connect.PointerEventHandler;
import princess.connect.SensorEventHandler;
import princess.connect.engine.GameEngine;

/**
 * <code>GameState</code>定義遊戲中各種不同狀態所需要負責的行為，遊戲狀態
 * 必須繼承<code>GameState</code>並實作必要的行為，並註冊到遊戲引擎中。
 *
 * @author <a href="http://www.csie.ntut.edu.tw/labsdtl/">Lab SDT</a>
 * @version 2.0
 * @since 1.0
 */
public abstract class GameState implements GameObject, KeyEventHandler, SensorEventHandler, PointerEventHandler {

    protected GameEngine _engine;

    /**
     * 建構一個<code>GameState</code>實體。
     *
     * @param engine 執行狀態處理者的引擎
     */
    protected GameState(GameEngine engine) {
        _engine = engine;
    }

    /**
     * 更換狀態。
     *
     * @param state 新狀態的代碼
     */
    public void changeState(int state) {
        _engine.setGameState(state);
    }

    /**
     * 更換狀態。
     *
     * @param state      新狀態的代碼
     * @param parameter 給新狀態的參數
     */
    public void changeState(int state, Map<String, Object> parameter) {
        _engine.setGameState(state, parameter);
    }

    /**
     * 清除所有除錯資訊。
     */
    public void clearDebugInfo() {
        _engine.clearDebugInfo();
    }

    /**
     * 列印除錯資訊。
     *
     * @param info 除錯資訊
     */
    public void printDebugInfo(String info) {
        _engine.addDebugInfo(info);
    }

    /**
     * 初始化所有必要的資源。這個函式會在狀態處理者切換後立即被呼叫。
     */
    public abstract void initialize(Map<String, Object> data);

    /**
     * 通知遊戲引擎目前被暫停中。
     */
    public abstract void pause();

    /**
     * 通知遊戲引擎已回復並繼續處理畫面更新。
     */
    public abstract void resume();

}
