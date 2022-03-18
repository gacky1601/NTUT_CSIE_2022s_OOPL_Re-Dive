package tw.edu.ntut.csie.game;

import java.util.List;

/**
 * 對指標裝置(滑鼠或觸控螢幕)事件有興趣的類別，可以實作<code>PointerEventHandler</code>
 * 介面來接收及處理遊戲引擎轉送的指標裝置事件。
 *
 * @author <a href="http://www.csie.ntut.edu.tw/labsdtl/">Lab SDT</a>
 * @version 2.0
 * @since 2.0
 */
public interface PointerEventHandler {

    /**
     * 處理滑鼠被按下或是手指按在觸控螢幕上的事件。
     *
     * @param actionPointer 手指觸碰點
     * @param pointers 所有在螢幕上手指所觸碰的點
     * @return true表示該事件已經被處理完畢，不用再繼續傳遞下去。
     */
    public boolean pointerPressed(Pointer actionPointer, List<Pointer> pointers);

    /**
     * 處理滑鼠移動或是手指按在觸控螢幕移動的事件。
     *
     * @param actionPointer 手指移動的點
     * @param pointers 所有在螢幕上手指所觸碰的點
     * @return true表示該事件已經被處理完畢，不用再繼續傳遞下去。
     */
    public boolean pointerMoved(Pointer actionPointer, List<Pointer> pointers);

    /**
     * 處理滑鼠被放開或是手指離開螢幕的事件。
     *
     * @param pointers 手指放開的點
     * @param pointers 所有在螢幕上手指所觸碰的點
     * @return true表示該事件已經被處理完畢，不用再繼續傳遞下去。
     */
    public boolean pointerReleased(Pointer actionPointer, List<Pointer> pointers);
}
