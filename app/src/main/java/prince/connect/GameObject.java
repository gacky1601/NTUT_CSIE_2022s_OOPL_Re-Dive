package prince.connect;

/**
 * <code>GameObject</code>代表所有可以在畫面上顯示和移動的物件。
 *
 * @author <a href="http://www.csie.ntut.edu.tw/labsdtl/">Lab SDT</a>
 * @version 2.0
 * @since 2.0
 */
public interface GameObject extends ReleasableResource {

    /**
     * 根據使用者觸發的事件將物件移動至指定的地方。
     */
    public void move();

    /**
     * 將物件顯示在遊戲畫面上{@link #move()}指定的位置。
     */
    public void show();
}
