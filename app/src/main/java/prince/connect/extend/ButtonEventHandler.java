package prince.connect.extend;

/**
 * 任何對{@link BitmapButton}的事件有興趣的類別，可以實作此類別並向
 * {@link BitmapButton}註冊，就可以接收並處理按鈕被按下又放開的事件。
 *
 * @author <a href="http://www.csie.ntut.edu.tw/labsdtl/">Lab SDT</a>
 * @version 2.0
 * @since 2.0
 */
public interface ButtonEventHandler {

    /**
     * 當按鈕被按下又放開後會被執行的函式。
     *
     * @param button 被按下又放開的按鈕
     */
    public void perform(BitmapButton button);
}
