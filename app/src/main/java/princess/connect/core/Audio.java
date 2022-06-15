package princess.connect.core;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

import princess.connect.GameView;
import princess.connect.ReleasableResource;

/**
 * <code>Audio</code>用來載入並播放指定的單一聲音。
 *
 * @author <a href="http://www.csie.ntut.edu.tw/labsdtl/">Lab SDT</a>
 * @version 2.0
 * @since 1.0
 */
public class Audio implements ReleasableResource {

    /**
     * 預設的左聲道音量。
     */
    public static final int DEFAULT_LEFT_VOLUME = 2;

    /**
     * 預設的右聲道音量。
     */
    public static final int DEFAULT_RIGHT_VOLUME = 2;

    /**
     * 用來播放聲音的播放器
     */
    private MediaPlayer _player;

    /**
     * 建立一個空的<code>Audio</code>物件，注意，在{@link #play()}播放前，需呼叫{@link #loadAudio(int)}
     * 或{@link #loadAudio(String)}載入欲播放的聲音，否則會擲出<code>NullPointException</code>例外。
     */
    public Audio() {
    }

    /**
     * 建立<code>Audio</code>物件並立即載入指定的聲音。
     *
     * @param resId 聲音的來源
     */
    public Audio(int resId) {
        loadAudio(resId);
    }

    /**
     * 從指定的Android資源ID載入聲音。
     *
     * @param resId 聲音來源
     */
    public void loadAudio(int resId) {
        _player = MediaPlayer.create(GameView.runtime, resId);
        _player.setVolume(DEFAULT_LEFT_VOLUME, DEFAULT_RIGHT_VOLUME);
    }

    /**
     * 從指定的檔名載入聲音。
     *
     * @param filename 聲音來源
     */
    public void loadAudio(String filename) {
        try {
            AssetFileDescriptor descriptor = GameView.runtime.getAssets().openFd(filename);
            _player = new MediaPlayer();
            _player.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            _player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 暫時停止播放聲音。
     */
    public void pause() {
        _player.pause();
    }

    /**
     * 重頭開始播放聲音。
     */
    public void play() {
        _player.seekTo(0);
        _player.start();
    }

    @Override
    public void release() {
        if (_player != null) {
            _player.stop();
            _player.release();
        }
        _player = null;
    }

    /**
     * 從上次暫停的地方開始繼續播放聲音。
     */
    public void resume() {
        _player.start();
    }

    /**
     * 立即停止播放聲音。
     */
    public void stop() {
        _player.pause();
    }

    /**
     * 設定是否不斷地重複播放聲音。
     *
     * @param repeating true表示重複播放；false表示只播放一次
     */
    public void setRepeating(boolean repeating) {
        _player.setLooping(repeating);
    }

    public boolean isPlauing() {
        return _player.isPlaying();
    }
}
