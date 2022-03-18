package tw.edu.ntut.csie.game;

/**
 * 對感應器(定位或加速度感應器)事件有興趣的類別，可以實作<code>SensorEventHandler</code>
 * 介面來接收及處理遊戲引擎轉送的感應器事件。
 *
 * @author <a href="http://www.csie.ntut.edu.tw/labsdtl/">Lab SDT</a>
 * @version 2.0
 * @since 2.0
 */
public interface SensorEventHandler {

    /**
     * 處理裝置在X、Y及Z軸上角度的變化，單位為度。X軸代表水平軸，朝右為正；
     * Y軸代表垂直軸，朝上為正；Z軸代表前後，螢幕朝使用者方向為正。當裝置的定
     * 位出現變化時，pitch代表繞X軸旋轉的角度變化(-180~180)，當裝置的螢幕面朝
     * 使用者旋轉，則變化的角度為負，反之為正。azimuth (方位角)代表與磁北極之
     * 間的角度變化，朝北為0度，朝東為90度，朝南為180度，朝西為270度。roll代表
     * 繞Z軸旋轉的角度變化(-90~90)，當裝置的螢幕平放時，順時針方向旋轉時角度為
     * 正，反之為負。
     *
     * @param pitch   以X為旋轉軸的角度
     * @param azimuth 以Y為旋轉軸的角度
     * @param roll    以Z為旋轉軸的角度
     */
    public void orientationChanged(float pitch, float azimuth, float roll);

    /**
     * 處理裝置在X、Y及Z軸上的加速度變化，單位為SI制的m/s<sup>2</sup>。由於加
     * 速度的座標系統會隨著裝置旋轉的角度變化，唯一不變的是X軸與Z軸；X軸永遠與螢
     * 幕的像素座標系統的X軸相同，正Z軸代表螢幕的正面，由右手定理可以找出Y軸。由
     * 於感應器會持續感測到重力加速度，所以當裝置平放於桌面上時，此時正X軸朝右，
     * 正Z軸朝上，dZ會得到9.8，代表桌子施以裝置一個力抵抗重力加速度。
     *
     * @param dX X軸上的加速度
     * @param dY Y軸上的加速度
     * @param dZ Z軸上的加速度
     */
    public void accelerationChanged(float dX, float dY, float dZ);
}
