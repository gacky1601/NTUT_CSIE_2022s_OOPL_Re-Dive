package princess.connect;

/**
 * 任何無法自動歸還的系統資源都應該實作本介面，讓遊戲引擎在必要的時候
 * 呼叫本介面以釋放系統資源。
 *
 * @author <a href="http://www.csie.ntut.edu.tw/labsdtl/">Lab SDT</a>
 * @version 2.0
 * @since 2.0
 */
public interface ReleasableResource {

    /**
     * 釋放所有已分配的資源。
     */
    public void release();
}
