package princess.connect.state;

import static princess.connect.GameView.runtime;

import android.graphics.BitmapFactory;

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

    private final int SPEED = 2;

    private BattleGround _ground;
    private Timer _timer;
    private TimerTask _timeTask;
    private List<List<CharacterAnimation>> _charAnimations;

    @Override
    public void initialize(Map<String, Object> data) {
        addGameObject(new MovingBitmap(R.drawable.bg_100021, -200, -200).resize(1920, 1080));
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
        valueDisplay();
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

    private void valueDisplay() {
        Character chara;
        List<Character.ValueDisplay> valueDisplays;
        List<Character> chars = getSortedCharacter();
        for (int i = 0; i < chars.size(); i++) {
            chara = chars.get(i);
            valueDisplays = chara.valueDisplays();
            for (Character.ValueDisplay valueDisplay : valueDisplays) {
                addGameObject(new ValueDisplayAnimation(valueDisplay, chara.x(), chara.y()));
            }
        }
    }

    private static class CharacterAnimation extends Animation {
        int ANIMATION_FRAME = 10;
        int SAMPLE_SIZE = 2;

        public CharacterAnimation(String charName, String action) {
            String path = "action/" + charName + "/" + action;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            options.inTargetDensity = 1;
            options.inSampleSize = SAMPLE_SIZE;
            try {
                for (String asset : runtime.getAssets().list(path)) {
                    MovingBitmap bitmap = new MovingBitmap(path + "/" + asset, options);
                    bitmap.resize(bitmap.getWidth() * SAMPLE_SIZE, bitmap.getHeight() * SAMPLE_SIZE);
                    addFrame(bitmap);
                }
            } catch (IOException ignored) {
            }
            setDelay(BattleGround.FRAME / ANIMATION_FRAME);
            setVisible(false);
        }

        @Override
        public void move() {
        }

        @Override
        public void setLocation(int x, int y) {
            if (getCurrentFrameIndex() == -1)
                return;
            x = (int) (Game.GAME_FRAME_WIDTH * 2 * x / BattleGround.WIDTH - Game.GAME_FRAME_WIDTH * 0.5);
            y = (int) (Game.GAME_FRAME_HEIGHT * 0.2 * y / BattleGround.HEIGHT + Game.GAME_FRAME_HEIGHT * 0.4);
            super.setLocation(x - (int) (getWidth() * 0.5), y - (int) (getHeight() * 0.75));
        }

        public void nextFrame() {
            super.move();
        }
    }

    private class ValueDisplayAnimation implements GameObject {
        final int DELAY = 20;

        private int _index;
        private int _count;
        private final Character.ValueDisplay _valueDisplay;
        private ValueAnimation _text;
        private List<ValueAnimation> _values;

        private class ValueAnimation extends Animation {
            final int MOVE_SPEED = 3;
            final int MOVE_TIME = 3;

            private int _count;

            public ValueAnimation(String value) {
                _count = 0;
                MovingBitmap bitmap;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = false;
                options.inTargetDensity = 1;
                options.inSampleSize = 2;
                if (_valueDisplay.isCritical) {
                    bitmap = new MovingBitmap("value/white/" + value + ".png", options);
                    addFrame(bitmap.resize((int) (bitmap.getWidth() * 1.2), (int) (bitmap.getHeight() * 1.2)));
                }
                bitmap = new MovingBitmap(
                        "value/" + _valueDisplay.valueType.name().toLowerCase() + "/" + value + ".png", options);
                if (_valueDisplay.isCritical)
                    addFrame(bitmap.resize((int) (bitmap.getWidth() * 1.2), (int) (bitmap.getHeight() * 1.2)));
                else
                    addFrame(bitmap);
                switch (_valueDisplay.valueType) {
                    case PHYSICAL:
                    case MAGIC:
                        try {
                            Double.parseDouble(value);
                            setVisible(false);
                        } catch (NumberFormatException ignored) {
                        }
                }
            }

            @Override
            public void move() {
                if (!_visible)
                    return;
                if (_frameIndex == 0 && getFrameCount() != 1)
                    _frameIndex++;
                else {
                    switch (_valueDisplay.valueType) {
                        case HP:
                        case TP:
                            if (_count++ < 5 * MOVE_TIME / SPEED)
                                setLocation(getX(),
                                        getY() - (int) (2 * MOVE_SPEED * SPEED
                                                * Math.cos(Math.PI / 10 * _count / MOVE_TIME * SPEED)));
                            break;
                        case PHYSICAL:
                        case MAGIC:
                            if (_count < 4 * MOVE_TIME / SPEED) {
                                setLocation(
                                        getX() + (int) (0.5 * MOVE_SPEED * SPEED
                                                * Math.cos(Math.PI / 2 * _count / MOVE_TIME * SPEED)),
                                        getY() - (int) (MOVE_SPEED * SPEED
                                                * Math.cos(Math.PI / 2 * _count / MOVE_TIME * SPEED)));
                                _count += _count < 2 * MOVE_TIME / SPEED ? 1 : 2;
                            }
                            break;
                    }
                }
            }
        }

        public ValueDisplayAnimation(Character.ValueDisplay valueDisplay, int x, int y) {
            _index = 0;
            _count = 0;
            _valueDisplay = valueDisplay;
            _values = new ArrayList<>();
            if (_valueDisplay.isCritical)
                _text = new ValueAnimation("critical");
            for (char value : String.valueOf(_valueDisplay.value).toCharArray())
                _values.add(new ValueAnimation(String.valueOf(value)));
            setLocation(x, y);
        }

        public void setLocation(int x, int y) {
            int width = 0;
            for (ValueAnimation valueAnimation : _values)
                width -= valueAnimation.getWidth() / 2;
            x = (int) (Game.GAME_FRAME_WIDTH * 2 * x / BattleGround.WIDTH - Game.GAME_FRAME_WIDTH * 0.5);
            y = (int) (Game.GAME_FRAME_HEIGHT * 0.2 * y / BattleGround.HEIGHT + Game.GAME_FRAME_HEIGHT * 0.4);
            switch (_valueDisplay.valueType) {
                case HP:
                case TP:
                    for (ValueAnimation valueAnimation : _values) {
                        valueAnimation.setLocation(x + width, y - 130);
                        width += valueAnimation.getWidth();
                    }
                    break;
                case PHYSICAL:
                case MAGIC:
                    if (_text != null)
                        _text.setLocation(x + width, y - 120);
                    for (ValueAnimation valueAnimation : _values) {
                        valueAnimation.setLocation(x + width, y - 100);
                        width += valueAnimation.getWidth();
                    }
                    break;
            }
        }

        public void setVisible(boolean isVisible) {
            if (_text != null)
                _text.setVisible(isVisible);
            for (ValueAnimation valueAnimation : _values)
                valueAnimation.setVisible(isVisible);
        }

        @Override
        public void move() {
            switch (_valueDisplay.valueType) {
                case HP:
                case TP:
                    if (++_count > 0.75 * DELAY / SPEED)
                        setVisible(false);
                    break;
                case PHYSICAL:
                case MAGIC:
                    for (int i = 0; i < SPEED; i++)
                        if (_index < _values.size())
                            _values.get(_index++).setVisible(true);
                    if (_index == _values.size() && ++_count == DELAY / SPEED)
                        setVisible(false);
                    break;
            }
            if (_text != null)
                _text.move();
            for (ValueAnimation valueAnimation : _values)
                valueAnimation.move();
        }

        @Override
        public void show() {
            if (_text != null)
                _text.show();
            for (ValueAnimation valueAnimation : _values)
                valueAnimation.show();
        }

        @Override
        public void release() {
            _text = null;
            _values.clear();
            _values = null;
        }
    }

    private static class CharacterComparator implements Comparator<Character> {
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
