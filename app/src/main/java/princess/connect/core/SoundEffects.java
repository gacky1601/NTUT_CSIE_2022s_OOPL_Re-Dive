package princess.connect.core;

import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;
import java.util.HashMap;

import princess.connect.ReleasableResource;

import static princess.connect.GameView.runtime;

/**
 * <code>SoundEffects</code>可以將多軌音效混和後一起同步播放(混音)，
 * <code>SoundEffects</code>適合用在播放短暫的音效，例如爆炸聲、子彈
 * 發射的聲音，但比較不適合用在長的音樂，例如背景音樂。
 * 注意，一個<code>SoundEffects</code>同時只能混和10筆音效，若要混和
 * 更多音效，請修改{@link #DEFAULT_MAXIMUM_STREAMS}。
 *
 * @author <a href="http://www.csie.ntut.edu.tw/labsdtl/">Lab SDT</a>
 * @version 2.0
 * @since 1.0
 */
public class SoundEffects implements ReleasableResource {

    /**
     * 預設音效載入時所使用的採樣率：100。
     */
    public static final int DEFAULT_SOUND_QUALITY = 100;

    /**
     * 預設最大混和音效比數：10筆。
     */
    public static final int DEFAULT_MAXIMUM_STREAMS = 10;

    /**
     * 預設的左聲道音量。
     */
    public static final float DEFAULT_LEFT_VOLUME = 0.8f;

    /**
     * 預設的右聲道音量。
     */
    public static final float DEFAULT_RIGHT_VOLUME = 0.8f;

    /**
     * 預設的音效載入優先權。
     */
    public static final int DEFAULT_LOAD_PRIORITY = 1;

    /**
     * 預設的播放優先權。
     */
    public static final int DEFAULT_PLAY_PRIORITY = 1;

    /**
     * 預設的播放重複次數：0 (不重複播放)。
     */
    public static final int DEFAULT_REPEAT_TIMES = 0;

    /**
     * 預設的播放速度。
     */
    public static final float DEFAULT_PLAYBACK_RATE = 1.0f;

    /**
     * 支援的最大播放速度。
     */
    public static final float MAXIMUM_PLAYBACK_RATE = 2.0f;

    /**
     * 支援的最小播放速度。
     */
    public static final float MINIMUM_PLAYBACK_RATE = 0.5f;

    private SoundPool _sounds;
    private HashMap<Integer, Integer> _soundsMap;
    private HashMap<Integer, Integer> _playedSounds;

    /**
     * 建立一個<code>SoundEffects</code>物件用來混合音軌。
     */
    public SoundEffects() {
        _soundsMap = new HashMap<Integer, Integer>();
        _playedSounds = new HashMap<Integer, Integer>();
        _sounds = new SoundPool(DEFAULT_MAXIMUM_STREAMS, AudioManager.STREAM_MUSIC, DEFAULT_SOUND_QUALITY);
    }

    /**
     * 從指定的資源載入音效，加入混音並將載入的音效指定邏輯ID。之後可以使用邏輯ID來
     * 播放、暫停、停止或是繼續播放指定的音效。
     *
     * @param soundId 音效邏輯ID
     * @param resId   音效資源ID
     */
    public void addSoundEffect(int soundId, int resId) {
        // 從指定的資源中載入音效並記錄邏輯音效ID與實際音效ID的配對。
        _soundsMap.put(soundId, _sounds.load(runtime, resId, DEFAULT_LOAD_PRIORITY));
    }

    /**
     * 從指定的檔名載入音效，加入混音並將載入的音效指定邏輯ID。之後可以使用邏輯ID來
     * 播放、暫停、停止或是繼續播放指定的音效。
     *
     * @param soundId  音效邏輯ID
     * @param filename 音效檔名
     */
    public void addSoundEffect(int soundId, String filename) {
        try {
            // 從檔案載入音效並記錄邏輯音效ID與實際音效ID的配對。
            _soundsMap.put(soundId, _sounds.load(runtime.getAssets().openFd(filename), DEFAULT_LOAD_PRIORITY));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 暫時停止播放指定的音效。
     *
     * @param soundId 欲暫停播放的音效邏輯ID
     */
    public void pause(int soundId) {
        if (_playedSounds.containsKey(soundId)) {
            _sounds.pause(_playedSounds.get(soundId));
        }
    }

    /**
     * 使用預設的音量和速度，播放指定的音效ID。
     *
     * @param soundId 欲播放的音效邏輯ID
     * @return true表示成功，false表示失敗
     */
    public boolean play(int soundId) {
        return play(soundId, DEFAULT_LEFT_VOLUME, DEFAULT_RIGHT_VOLUME, DEFAULT_PLAYBACK_RATE);
    }

    /**
     * 使用預設的音量和指定的速度，播放指定的音效ID。播放速度最小可以設到{@value #MINIMUM_PLAYBACK_RATE}，
     * 最大則可以設到{@value #MAXIMUM_PLAYBACK_RATE}。
     *
     * @param soundId 欲播放的音效邏輯ID
     * @param rate    播放速度
     * @return true表示成功，false表示失敗
     */
    public boolean play(int soundId, float rate) {
        return play(soundId, DEFAULT_LEFT_VOLUME, DEFAULT_RIGHT_VOLUME, rate);
    }

    /**
     * 根據指定的音效ID、左/佑聲道音量和速度播放音效。播放速度最小可以設到{@value #MINIMUM_PLAYBACK_RATE}，
     * 最大則可以設到{@value #MAXIMUM_PLAYBACK_RATE}。
     *
     * @param soundId     預撥放的音效邏輯ID
     * @param leftVolume  左聲道音量
     * @param rightVolume 右聲道音量
     * @param rate        播放速度
     * @return true表示成功，false表示失敗
     */
    public boolean play(int soundId, float leftVolume, float rightVolume, float rate) {
        int playedId = _sounds.play(_soundsMap.get(soundId), leftVolume, rightVolume, DEFAULT_PLAY_PRIORITY, DEFAULT_REPEAT_TIMES, rate);
        if (playedId > 0) {
            // 記錄邏輯音效ID與實際播放音軌ID的配對
            _playedSounds.put(soundId, playedId);
        }
        return playedId > 0;
    }

    @Override
    public void release() {
        _sounds.release();
        _soundsMap.clear();
        _playedSounds.clear();

        _sounds = null;
        _soundsMap = null;
        _playedSounds = null;
    }

    /**
     * 從上次暫停的地方開始繼續播放指定的音效。
     *
     * @param soundId 欲繼續播放的音效邏輯ID
     */
    public void resume(int soundId) {
        if (_playedSounds.containsKey(soundId)) {
            _sounds.resume(_playedSounds.get(soundId));
        }
    }

    /**
     * 停止播放指定的音效。
     *
     * @param soundId 欲停止播放的音效邏輯ID
     */
    public void stop(int soundId) {
        if (_playedSounds.containsKey(soundId)) {
            _sounds.stop(_playedSounds.get(soundId));
        }
    }
}
