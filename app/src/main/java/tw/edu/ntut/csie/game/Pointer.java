package tw.edu.ntut.csie.game;

/**
 * <code>Pointer</code>用來記錄指標裝置(滑鼠或觸控螢幕)觸發的指標點座標。
 * 若裝置支援多重指標裝置，事件會帶有多個指標點，且每個指標點都帶有ID，在動
 * 作進行中，相同的指標點會有相同的ID，因此可以用來判斷手勢。
 *
 * @author <a href="http://www.csie.ntut.edu.tw/labsdtl/">Lab SDT</a>
 * @version 2.0
 * @since 2.0
 */
public class Pointer {

    /**
     * 滑鼠左鍵引起的指標點
     */
    public static final int LEFT_MOUSE_BUTTON = 0;

    /**
     * 滑鼠又鍵引起的指標點
     */
    public static final int RIGHT_MOUSE_BUTTON = 1;

    /**
     * 滑鼠中間鍵引起的指標點
     */
    public static final int MIDDLE_MOUSE_BUTTON = 2;

    /**
     * 滑鼠在沒有任何按鍵按下時移動引起的指標點。
     */
    public static final int HOVER = 3;

    /**
     * 觸控引起的指標點，值同{@link #LEFT_MOUSE_BUTTON}。
     */
    public static final int TOUCH = LEFT_MOUSE_BUTTON;

    /**
     * 指標點的ID。
     */
    private int _id;

    /**
     * 指標點的類型。
     */
    private int _type;

    /**
     * 指標點的X座標。
     */
    private int _x;

    /**
     * 指標點的Y座標。
     */
    private int _y;

    /**
     * 建立{@link #TOUCH}類型的指標點。
     *
     * @param id 指標點ID
     * @param x  指標點的X座標
     * @param y  指標點的Y座標
     */
    public Pointer(int id, int x, int y) {
        this(id, TOUCH, x, y);
    }

    /**
     * 建立指標點。
     *
     * @param id   指標點ID
     * @param type 指標點的類型
     * @param x    指標點的X座標
     * @param y    指標點的Y座標
     */
    public Pointer(int id, int type, int x, int y) {
        _id = id;
        _type = type;
        _x = x;
        _y = y;
    }

    /**
     * 取得指標點的ID，相同的指標點在移動過程中，都會帶有相同的ID。
     *
     * @return 指標點ID
     */
    public int getID() {
        return _id;
    }

    /**
     * 取得指標點的類型，根據不同的裝置，指標點可能有下列方式引起：
     * <ul>
     * <li>{@link #LEFT_MOUSE_BUTTON}：滑鼠左鍵
     * <li>{@link #RIGHT_MOUSE_BUTTON}：滑鼠右鍵
     * <li>{@link #MIDDLE_MOUSE_BUTTON}：滑鼠中間建
     * <li>{@link #TOUCH}：手指觸控
     * </ul>
     *
     * @return 指標點的類型
     */
    public int getType() {
        return _type;
    }

    /**
     * 取得指標點於遊戲畫面中的X座標，此座標是<code>GameEnginer</code>根據
     * 實際裝置的螢幕尺寸和遊戲畫面尺寸換算在預期的遊戲畫面中的座標位置。
     *
     * @return 遊戲畫面中的X座標
     */
    public int getX() {
        return _x;
    }

    /**
     * 取得指標點於遊戲畫面中的Y座標，此座標是<code>GameEnginer</code>根據
     * 實際裝置的螢幕尺寸和遊戲畫面尺寸換算在預期的遊戲畫面中的座標位置。
     *
     * @return 遊戲畫面中的Y座標
     */
    public int getY() {
        return _y;
    }
}
