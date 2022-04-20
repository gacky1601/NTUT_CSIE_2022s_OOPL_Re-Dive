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
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import princeconnect.game.R;
import princess.connect.Game;
import princess.connect.GameObject;
import princess.connect.core.MovingBitmap;
import princess.connect.engine.GameEngine;
import princess.connect.baseClass.BattleGround;
import princess.connect.baseClass.Character;
import princess.connect.character.*;
import princess.connect.extend.Animation;

public class BattleState extends AbstractGameState {
    public BattleState(GameEngine engine) {
        super(engine);
    }

    private final int SPEED = 1;

    private BattleGround _ground;
    private Timer _timer;
    private TimerTask _timeTask;
    private List<List<CharacterAnimation>> _charAnimations;

    @Override
    public void initialize(Map<String, Object> data) {
        MovingBitmap background = new MovingBitmap(R.drawable.bg_100021, -200, -200);
        background.resize(1920, 1080);
        addGameObject(background);
        List<Character> characterLeft = Arrays.asList(new Pecorine(), new Kokoro(), new Kyaru());
        List<Character> characterRight = Arrays.asList(new Pecorine(), new Kokoro(), new Kyaru());
        _ground = new BattleGround(characterLeft, characterRight);

        _ground.initialize();
        loadCharacterAnimation();

        main();
    }

    @Override
    public void move() {
        super.move();
        numberDisplay();
    }

    @Override
    public void release() {
        if (_timeTask != null)
            _timeTask.cancel();
        _timeTask = null;
        _timer = null;
        super.release();
        _ground.release();
        if (_charAnimations != null)
            _charAnimations.clear();
        _charAnimations = null;
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

    private void loadCharacterAnimation() {
        _charAnimations = new ArrayList<>();
        CharacterAnimation charAnimation;
        List<CharacterAnimation> charAnimaList;
        for (Character chara : getSortedCharacter()) {
            charAnimaList = new ArrayList<>();
            for (Character.Action action : Character.Action.values()) {
                charAnimation = new CharacterAnimation(chara.name(), action.name().toLowerCase());
                if (chara.direction() == Character.Direction.RIGHT)
                    charAnimation.inversion();
                if (action == Character.Action.DIE)
                    charAnimation.setRepeating(false);
                charAnimaList.add(charAnimation);
                addGameObject(charAnimation);
            }
            _charAnimations.add(charAnimaList);
        }
    }

    private void changeAction() {
        Character chara;
        CharacterAnimation charAnimation;
        CharacterAnimation preCharAnimation;
        List<Character> chars = getSortedCharacter();
        for (int i = 0; i < chars.size(); i++) {
            chara = chars.get(i);
            charAnimation = _charAnimations.get(i).get(chara.action().ordinal());
            if (chara.isChangeAction()) {
                if (chara.preAction() != null) {
                    preCharAnimation = _charAnimations.get(i).get(chara.preAction().ordinal());
                    preCharAnimation.setVisible(false);
                }
                charAnimation.setCurrentFrameIndex(charAnimation.getFrameCount() - 1);
                if (!chara.isAlive())
                    charAnimation.reset();
                charAnimation.setLocation(chara.x(), chara.y());
                charAnimation.setVisible(true);
            }
        }
    }

    private void nextFrame() {
        Character chara;
        CharacterAnimation charAnimaion;
        List<Character> chars = getSortedCharacter();
        for (int i = 0; i < chars.size(); i++) {
            chara = chars.get(i);
            charAnimaion = _charAnimations.get(i).get(chara.action().ordinal());
            charAnimaion.nextFrame();
            charAnimaion.setLocation(chara.x(), chara.y());
        }
    }

    private void numberDisplay() {
        Character chara;
        List<Character.NumberDisplay> numberDisplays;
        List<Character> chars = getSortedCharacter();
        for (int i = 0; i < chars.size(); i++) {
            chara = chars.get(i);
            numberDisplays = chara.numberDisplays();
            for (Character.NumberDisplay numberDisplay : numberDisplays)
                addGameObject(new NumDisplayAnimation(numberDisplay, chara.x(), chara.y()));
        }
    }

    private class CharacterAnimation extends Animation {
        private final int ANIMATION_FRAME = 10;
        private final int SAMPLE_SIZE = 2;

        public CharacterAnimation(String charName, String action) {
            String path = "action/" + charName + "/" + action;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            options.inTargetDensity = 1;
            options.inSampleSize = SAMPLE_SIZE;
            try {
                for (String asset : runtime.getAssets().list(path)) {
                    MovingBitmap movingBitmap = new MovingBitmap(path + "/" + asset, options);
                    movingBitmap.resize(movingBitmap.getWidth() * SAMPLE_SIZE, movingBitmap.getHeight() * SAMPLE_SIZE);
                    addFrame(movingBitmap);
                }
            } catch (IOException e) {
            }
            setDelay(BattleGround.FRAME / ANIMATION_FRAME);
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

    private class NumDisplayAnimation implements GameObject {
        private NumberAnimation _text;
        private List<NumberAnimation> _numbers;

        private class NumberAnimation extends Animation {
            private final int DELAY = 10;

            public NumberAnimation(String numberType, String number) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = false;
                options.inTargetDensity = 1;
                options.inSampleSize = 2;
                addFrame(new MovingBitmap("number/white/" + number + ".png", options));
                addFrame(new MovingBitmap("number/" + numberType + "/" + number + ".png", options));
                setDelay(DELAY / SPEED);
            }

            @Override
            public void move() {
                if (_frameIndex == 0)
                    _frameIndex++;
                else if (--_counter <= 0)
                    _frameIndex = -1;
            }
        }

        public NumDisplayAnimation(Character.NumberDisplay numberDisplay, int x, int y) {
            _numbers = new ArrayList<>();
            if (numberDisplay.isCritical)
                _text = new NumberAnimation(numberDisplay.numberType.name().toLowerCase(), "critical");
            for (char number : String.valueOf(numberDisplay.number).toCharArray())
                _numbers.add(
                        new NumberAnimation(numberDisplay.numberType.name().toLowerCase(), String.valueOf(number)));
            setLocation(x, y);
        }

        public void setLocation(int x, int y) {
            int width = 0;
            for (NumberAnimation numberAnimation : _numbers)
                width -= numberAnimation.getWidth() / 2;
            x = (int) (Game.GAME_FRAME_WIDTH * 2 * x / BattleGround.WIDTH - Game.GAME_FRAME_WIDTH * 0.5);
            y = (int) (Game.GAME_FRAME_HEIGHT * 0.2 * y / BattleGround.HEIGHT + Game.GAME_FRAME_HEIGHT * 0.4);
            if (_text != null)
                _text.setLocation(x + width, y - 120);
            for (NumberAnimation numberAnimation : _numbers) {
                numberAnimation.setLocation(x + width, y - 100);
                width += numberAnimation.getWidth();
            }
        }

        @Override
        public void move() {
            if (_text != null)
                _text.move();
            for (NumberAnimation numberAnimation : _numbers)
                numberAnimation.move();
        }

        @Override
        public void show() {
            if (_text != null)
                _text.show();
            for (NumberAnimation numberAnimation : _numbers)
                numberAnimation.show();
        }

        @Override
        public void release() {
            if (_text != null)
                _text.release();
            _text = null;
            for (NumberAnimation numberAnimation : _numbers)
                numberAnimation.release();
            _numbers.clear();
            _numbers = null;
        }
    }

    private class CharacterComparator implements Comparator<Character> {
        @Override
        public int compare(Character char1, Character char2) {
            return char1.y() - char2.y() - 1;
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
