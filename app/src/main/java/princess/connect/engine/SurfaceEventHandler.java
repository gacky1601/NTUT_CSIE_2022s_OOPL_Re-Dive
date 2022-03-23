package princess.connect.engine;

import android.view.SurfaceHolder;

/**
 * <code>SurfaceEventHandler</code>是{@link GameEngine}的輔助類別，用以接收
 * {@link SurfaceHolder.Callback}事件，在適當的時機啟動或終止遊戲引擎執行緒。
 *
 * @author <a href="http://www.csie.ntut.edu.tw/labsdtl/">Lab SDT</a>
 * @version 2.0
 * @since 2.0
 */
class SurfaceEventHandler implements SurfaceHolder.Callback {

    /**
     * 用來執行遊戲引擎的執行緒。
     */
    private Thread _engineThread;

    /**
     * 用來處理事件與更新螢幕的遊戲引擎。
     */
    private GameEngine _engine;

    SurfaceEventHandler(GameEngine engine) {
        _engine = engine;
    }

    @Override
    public void surfaceChanged(SurfaceHolder surface, int format, int width, int height) {
        _engine.setSurfaceSize(width, height);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surface) {
        // 當畫面完成初始化後再啟動執行緒，
        // 如此執行緒就不需要不斷地詢問並等待畫面初始化。
        _engine.setRunning(true);
        _engineThread = new Thread(_engine);
        _engineThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surface) {
        boolean needRetry = true;
        _engine.setRunning(false);
        while (needRetry) {
            try {
                _engineThread.join();
                needRetry = false;
            } catch (InterruptedException e) {
            }
        }
    }
}
