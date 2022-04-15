package princess.connect.state;

import static princess.connect.GameView.runtime;

import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import princeconnect.game.R;
import princess.connect.Game;
import princess.connect.core.MovingBitmap;
import princess.connect.engine.GameEngine;
import princess.connect.baseClass.BattleGround;
import princess.connect.baseClass.Character;
import princess.connect.character.*;
import princess.connect.extend.Animation;

public class BattleState extends AbstractGameState {
    private final int SPEED = 2;

    private BattleGround _ground;
    private Timer _timer;
    private TimerTask _timeTask = null;
    private List<List<CharacterAnimation>> _charAnimations;

    public BattleState(GameEngine engine) {
        super(engine);
    }

    private class CharacterAnimation extends Animation {
        public static final int ANIMATION_FRAME = 10;
        public static final int SAMPLE_SIZE = 2;

        public CharacterAnimation(String path) {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = false;
                options.inTargetDensity = 1;
                options.inSampleSize = SAMPLE_SIZE;
                for (String asset : runtime.getAssets().list(path)) {
                    MovingBitmap movingBitmap = new MovingBitmap(path + "/" + asset, options);
                    movingBitmap.resize(movingBitmap.getWidth() * SAMPLE_SIZE, movingBitmap.getHeight() * SAMPLE_SIZE);
                    addFrame(movingBitmap);
                }
            } catch (IOException e) {
            }
            setDelay(BattleGround.FRAME / CharacterAnimation.ANIMATION_FRAME);
            setVisible(false);
        }

        @Override
        public void move() {
        }

        @Override
        public void setLocation(int x, int y) {
            if (getCurrentFrameIndex() == -1 || this == null)
                return;
            x = (int) (Game.GAME_FRAME_WIDTH * 2 * x / BattleGround.WIDTH - Game.GAME_FRAME_WIDTH * 0.5);
            y = (int) (Game.GAME_FRAME_HEIGHT * 0.2 * y / BattleGround.HEIGHT + Game.GAME_FRAME_HEIGHT * 0.4);
            super.setLocation(x - (int) (getWidth() * 0.5), y - (int) (getHeight() * 0.75));
        }

        public void nextFrame() {
            super.move();
        }
    }

    class CharacterComparator implements Comparator<Character> {
        @Override
        public int compare(Character char1, Character char2) {
            return char1.y() - char2.y() - 1;
        }
    }

    @Override
    public void initialize(Map<String, Object> data) {
        MovingBitmap background = new MovingBitmap(R.drawable.bg_100021, -200, -200);
        background.resize(1920, 1080);
        addGameObject(background);
        List<Character> characterLeft = Arrays.asList(new Pecorine(), new Kokoro(), new Kyaru());
        List<Character> characterRight = Arrays.asList(new Pecorine(), new Kokoro(), new Kyaru());
        _ground = new BattleGround(characterLeft, characterRight);

        _ground.initialize();
        loadAllCharacterAnimation();

        main();
    }

    private void main() {
        _timer = new Timer();
        _timeTask = new TimerTask() {
            @Override
            public void run() {
                _ground.main();
                changeAction();
                nextFrame();
            }
        };
        _timer.scheduleAtFixedRate(_timeTask, 0, 1000 / SPEED / BattleGround.FRAME);
    }

    private List<Character> getSortedCharacter() {
        List<Character> chars = _ground.characters();
        Collections.sort(chars, new CharacterComparator());
        return chars;
    }

    private void loadAllCharacterAnimation() {
        _charAnimations = new ArrayList<>();
        List<Character> chars = getSortedCharacter();
        CharacterAnimation tmp = null;
        for (Character chara : chars) {
            List<CharacterAnimation> charAnimaList = new ArrayList<>();
            for (Character.Action action : Character.Action.values()) {
                tmp = new CharacterAnimation(
                        "action/" + chara.name() + "/" + action.name().toLowerCase());
                if (chara.direction() == Character.Direction.RIGHT)
                    tmp.inversion();
                if (action == Character.Action.DIE)
                    tmp.setRepeating(false);
                charAnimaList.add(tmp);
                addGameObject(tmp);
            }
            _charAnimations.add(charAnimaList);
        }
    }

    private void changeAction() {
        List<Character> chars = getSortedCharacter();
        for (int i = 0; i < chars.size(); i++) {
            Character chara = chars.get(i);
            List<CharacterAnimation> charAnimaList = _charAnimations.get(i);
            if (chara.isChangeAction()) {
                if (chara.preAction() != null)
                    charAnimaList.get(chara.preAction().ordinal()).setVisible(false);
                charAnimaList.get(chara.action().ordinal())
                        .setCurrentFrameIndex(charAnimaList.get(chara.action().ordinal()).getFrameCount() - 1);
                if (chara.action() == Character.Action.DIE)
                    charAnimaList.get(chara.action().ordinal()).reset();
                charAnimaList.get(chara.action().ordinal()).setLocation(chara.x(), chara.y());
                charAnimaList.get(chara.action().ordinal()).setVisible(true);
            }
        }
    }

    private void nextFrame() {
        List<Character> chars = getSortedCharacter();
        for (int i = 0; i < chars.size(); i++) {
            Character chara = chars.get(i);
            CharacterAnimation charAnimaion = _charAnimations.get(i).get(chara.action().ordinal());
            charAnimaion.nextFrame();
            charAnimaion.setLocation(chara.x(), chara.y());
        }
    }

    @Override
    public void release() {
        if (_timeTask != null)
            _timeTask.cancel();
        _timeTask = null;
        _timer = null;
        super.release();
        _ground.release();
        _charAnimations.clear();
        _charAnimations = null;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
