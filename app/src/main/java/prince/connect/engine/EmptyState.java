package prince.connect.engine;

import java.util.Map;

import prince.connect.state.AbstractGameState;

/**
 * 一個空的狀態處理者，若遊戲開發者未指定狀態處理者工廠，當遊戲啟動時，
 * 為了避免{@link NullPointerException}，{@link GameEngine}會建立一個
 * 空的狀態處理者實體(Null Object Pattern)。這僅限Game Framework內部使
 * 用，遊戲開發者請勿使用此類別。
 *
 * @author <a href="http://www.csie.ntut.edu.tw/labsdtl/">Lab SDT</a>
 * @version 2.0
 * @since 2.0
 */
class EmptyState extends AbstractGameState {

    /**
     * 建立一個空的狀態處理者。
     *
     * @param engine 遊戲引擎
     */
    EmptyState(GameEngine engine) {
        super(engine);
    }

    @Override
    public void initialize(Map<String, Object> data) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
