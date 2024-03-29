package princess.connect.state;

import static android.view.View.AUTOFILL_FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;
import static princess.connect.GameView.runtime;

import android.graphics.BitmapFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import princeconnect.game.R;
import princess.connect.Game;
import princess.connect.GameObject;
import princess.connect.baseClass.Area;
import princess.connect.core.Audio;
import princess.connect.core.MovingBitmap;
import princess.connect.engine.GameEngine;
import princess.connect.baseClass.BattleGround;
import princess.connect.baseClass.Character;
import princess.connect.extend.Animation;
import princess.connect.extend.BitmapButton;
import princess.connect.extend.CharacterButton;
import princess.connect.state.PlayerMenu.AdventurePage;

public class BattleState extends AbstractGameState {
    public BattleState(GameEngine engine) {
        super(engine);
    }

    private final int SPEED = 2;

    private Map<String, Object> _data;
    private BattleGround _ground;
    private Timer _timer;
    private TimerTask _timeTask;
    private List<List<CharacterAnimation>> _charAnimations;
    private List<Integer> _valueAnimationNums;
    private List<CharacterButton> _charBtns;
    private List<BarAnimation> _barAnimations;
    private BitmapButton _menuBtn, _confirmBtn;
    private MovingBitmap _menuText, _confirmText;
    private boolean _isFinish;
    private Audio _bgm;

    @Override
    public void initialize(Map<String, Object> data) {
        _data = data;
        _isFinish = false;
        ((Audio) _data.get(InitPage.BGM)).stop();
        _bgm = new Audio(R.raw.bgm_battle);
        _bgm.setRepeating(true);
        _bgm.play();
        Area.AreaLevel level = (Area.AreaLevel) _data.get(AdventurePage.SELECTED_LEVEL);
        addGameObject(new MovingBitmap("map/bg_" + level.map() + ".png", -200, -200).resize(1920, 1080));
        _ground = new BattleGround((List<Character>) _data.get(AdventurePage.SELECTED_CHARACTER), level.chars());

        _ground.initialize();
        initCharacterAnimation();
        initValueAnimationNums();
        initStatusBar();
        initButtons();

        addGameObject(new Loading());

        main();
    }

    @Override
    public void move() {
        super.move();
        valueDisplay();
        updateIcon();
        if (_ground.isEnd() && _ground.isIdle())
            finish();
    }

    private void main() {
        _timer = new Timer();
        _timeTask = new TimerTask() {
            @Override
            public void run() {
                _ground.main();
                try {
                    changeAction();
                    nextFrame();
                } catch (NullPointerException ignored) {}
            }
        };
        _timer.scheduleAtFixedRate(_timeTask, 0, 1000 / SPEED / BattleGround.FRAME);
    }

    private List<Character> getSortedCharacter() {
        List<Character> chars = _ground.characters();
        Collections.sort(chars, new CharacterComparator());
        return chars;
    }

    private void initButtons() {
        _menuBtn = new BitmapButton("interface/button/menu.png");
        _menuBtn.setLocation(Game.GAME_FRAME_WIDTH - _menuBtn.getWidth() - 30, 15);
        _menuBtn.addButtonEventHandler(button -> changeState(Game.ADV_PAGE, _data));
        _menuText = new MovingBitmap(R.drawable.text_back).resize(0.2);
        _menuText.setLocation(_menuBtn.getX() + _menuBtn.getWidth() / 2 - _menuText.getWidth() / 2,
                _menuBtn.getY() + _menuBtn.getHeight() / 2 - _menuText.getHeight() / 2);
        _confirmBtn = new BitmapButton("interface/button/blue.png").resize(1.5);
        _confirmBtn.setLocation(Game.GAME_FRAME_WIDTH - _confirmBtn.getWidth() - 60, Game.GAME_FRAME_HEIGHT - _confirmBtn.getHeight() - 30);
        _confirmBtn.addButtonEventHandler(button -> {
            if (_ground.isWin()) {
                int[] levelProgress = (int[]) _data.get(InitPage.LEVEL_PROGRESS), selectedLevel = (int[]) _data.get(AdventurePage.SELECTED_LEVEL_ID), levelID = new int[2];
                if (levelProgress[0] == selectedLevel[0] && levelProgress[1] == selectedLevel[1]) {
                    levelID[0] = selectedLevel[0];
                    levelID[1] = selectedLevel[1] + 1;
                    _data.put(InitPage.LEVEL_PROGRESS, levelID);
                }
            }
            changeState(Game.ADV_PAGE, _data);
        });
        _confirmBtn.setVisible(false);
        _confirmText = new MovingBitmap(R.drawable.text_next).resize(0.25);
        _confirmText.setLocation(_confirmBtn.getX() + _confirmBtn.getWidth() / 2 - _confirmText.getWidth() / 2,
                _confirmBtn.getY() + _confirmBtn.getHeight() / 2 - _confirmText.getHeight() / 2);
        _confirmText.setVisible(false);
        addPointerEventHandler(_menuBtn);
        addPointerEventHandler(_confirmBtn);
        addGameObject(_menuBtn);
        addGameObject(_menuText);
        addGameObject(_confirmBtn);
        addGameObject(_confirmText);
    }

    private void initValueAnimationNums() {
        _valueAnimationNums = new ArrayList<>();
        for (int i = 0; i < getSortedCharacter().size(); i++)
            _valueAnimationNums.add(0);
    }

    private void initStatusBar() {
        _barAnimations = new ArrayList<>();
        _charBtns = new ArrayList<>();
        int x, width = 0, spacing = 50;
        BarAnimation barAnimation = null;
        List<Character> chars = getSortedCharacter();
        for (Character chara : chars) {
            switch (chara.direction()) {
                case LEFT:
                    barAnimation = new BarAnimation(BarType.GREEN, chara);
                    break;
                case RIGHT:
                    barAnimation = new BarAnimation(BarType.RED, chara);
                    break;
            }
            width = barAnimation.getWidth();
            addGameObject(barAnimation);
        }
        x = Game.GAME_FRAME_WIDTH / 2 + 2 * (width + spacing);
        chars = _ground.characters();
        for (Character chara : chars) {
            if (chara.direction() == Character.Direction.LEFT) {
                _barAnimations.add(new BarAnimation(BarType.BLUE, x, (int) (Game.GAME_FRAME_HEIGHT * 0.95), chara));
                _barAnimations.add(new BarAnimation(BarType.GREEN, x, (int) (Game.GAME_FRAME_HEIGHT * 0.95) - 25, chara));
                _charBtns.add(new CharacterButton(chara, x - width / 2 + 1, (int) (Game.GAME_FRAME_HEIGHT * 0.95) - width - 30));
                addGameObject(_barAnimations.get(_barAnimations.size() - 2));
                addGameObject(_barAnimations.get(_barAnimations.size() - 1));
                addGameObject(_charBtns.get(_charBtns.size() - 1));
                x -= width + spacing;
            }
        }
    }

    private void initCharacterAnimation() {
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
            for (Character.ValueDisplay valueDisplay : valueDisplays)
                addGameObject(new ValueDisplayAnimation(valueDisplay, i));
        }
    }

    private void updateIcon() {
        for (Character chara : getSortedCharacter())
            if (chara.direction() == Character.Direction.LEFT && !chara.isAlive())
                for (CharacterButton btn : _charBtns)
                    if (btn.id() == chara.id())
                        btn.setGray(true);
    }

    private void finish() {
        for (CharacterButton btn : _charBtns)
            btn.setVisible(false);
        for (BarAnimation animation : _barAnimations)
            animation.setVisible(false);
        _menuBtn.setVisible(false);
        _menuText.setVisible(false);
        if (!_isFinish) {
            _isFinish = true;
            addGameObject(new Result(_ground.isWin() ? "win" : "lose"));
        }
    }

    private static int convertX(int x) {
        return (int) (Game.GAME_FRAME_WIDTH * 2 * x / BattleGround.WIDTH - Game.GAME_FRAME_WIDTH * 0.5);
    }

    private static int convertY(int y) {
        return (int) (Game.GAME_FRAME_HEIGHT * 0.2 * y / BattleGround.HEIGHT + Game.GAME_FRAME_HEIGHT * 0.4);
    }

    private static class CharacterAnimation extends Animation {
        final int ANIMATION_FRAME = 10;
        final int SAMPLE_SIZE = 2;

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
            x = convertX(x);
            y = convertY(y);
            super.setLocation(x - (int) (getWidth() * 0.5), y - (int) (getHeight() * 0.75));
        }

        public void nextFrame() {
            super.move();
        }
    }

    private class ValueDisplayAnimation implements GameObject {
        final int DELAY = 20;

        private final int _charaIndex;
        private int _valueIndex, _count;
        private final Character.ValueDisplay _valueDisplay;
        private ValueAnimation _text;
        private List<ValueAnimation> _values;

        public ValueDisplayAnimation(Character.ValueDisplay valueDisplay, int index) {
            _charaIndex = index;
            _valueIndex = 0;
            _count = 0;
            _valueDisplay = valueDisplay;
            _values = new ArrayList<>();
            if (_valueDisplay.isMiss)
                _text = new ValueAnimation("miss");
            else {
                if (_valueDisplay.isCritical)
                    _text = new ValueAnimation("critical");
                for (char value : String.valueOf(_valueDisplay.value).toCharArray())
                    _values.add(new ValueAnimation(String.valueOf(value)));
            }
            switch (_valueDisplay.valueType) {
                case PHYSICAL:
                case MAGIC:
                    _valueAnimationNums.set(_charaIndex, _valueAnimationNums.get(_charaIndex) + 1);
                    break;
            }
            Character chara = getSortedCharacter().get(_charaIndex);
            setLocation(chara.x(), chara.y());
        }

        public void setLocation(int x, int y) {
            int width = 0;
            int height = _valueAnimationNums.get(_charaIndex) * -30;
            for (ValueAnimation valueAnimation : _values)
                width -= valueAnimation.getWidth() / 2;
            x = convertX(x);
            y = convertY(y);
            switch (_valueDisplay.valueType) {
                case HP:
                case TP:
                    for (ValueAnimation valueAnimation : _values) {
                        valueAnimation.setLocation(x + width, y - 130);
                        width += valueAnimation.getWidth();
                    }
                    break;
                case PHYSICAL:
                    if (_valueDisplay.isMiss) {
                        _text.setLocation(x - _text.getWidth() / 2, y + height - 50);
                        break;
                    }
                case MAGIC:
                    if (_valueDisplay.isCritical)
                        _text.setLocation(x + width, y + height - 70);
                    for (ValueAnimation valueAnimation : _values) {
                        valueAnimation.setLocation(x + width, y + height - 50);
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
                        if (_valueIndex < _values.size())
                            _values.get(_valueIndex++).setVisible(true);
                    if (_valueIndex == _values.size() && ++_count == DELAY / SPEED) {
                        _valueAnimationNums.set(_charaIndex, _valueAnimationNums.get(_charaIndex) - 1);
                        setVisible(false);
                    }
                    break;
            }
            if (_text != null)
                _text.move();
            for (ValueAnimation valueAnimation : _values)
                valueAnimation.move();
        }

        @Override
        public void show() {
            if (_valueDisplay.isMiss) {
                _text.show();
                return;
            }
            if (_text != null)
                _text.show();
            for (ValueAnimation valueAnimation : _values)
                valueAnimation.show();
        }

        @Override
        public void release() {
            if (_text != null)
                _text.release();
            for (ValueAnimation valueAnimation : _values)
                valueAnimation.release();
            _text = null;
            _values.clear();
            _values = null;
        }

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
                if (_valueDisplay.isCritical || _valueDisplay.isMiss)
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
                                setLocation(getX(), getY() - (int) (2 * MOVE_SPEED * SPEED
                                        * Math.cos(Math.PI / 10 * _count / MOVE_TIME * SPEED)));
                            break;
                        case PHYSICAL:
                            if (_valueDisplay.isMiss) {
                                if (_count < 2 * MOVE_TIME / SPEED) {
                                    setLocation(getX(), getY() - (int) (MOVE_SPEED * SPEED
                                            * Math.cos(Math.PI / 2 * _count++ / MOVE_TIME * SPEED)));
                                }
                                break;
                            }
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
    }

    private enum BarType {
        BG, GREEN, RED, YELLOW, BLUE
    }

    private class BarAnimation implements GameObject {
        final int DELAY = 10;
        final int MOVE_TIME = 4;
        final double DELTA = 0.000001;

        private final Character _chara;
        private final BarType _barType;
        private Bar _frame, _background, _main;
        private double _value;
        private int _count;
        private boolean _isAlwaysShow;

        public BarAnimation(BarType barType, Character chara) {
            _chara = chara;
            _barType = barType;
            _frame = new Bar(BarType.BG);
            _main = new Bar(_barType);
            switch (_barType) {
                case GREEN:
                case RED:
                    _background = new Bar(BarType.YELLOW);
                    _background._value = 1;
                    setValue(1);
                    break;
                case BLUE:
                    setValue(0);
                    break;
            }
            _count = MOVE_TIME / SPEED;
            _isAlwaysShow = false;
            setVisible(false);
        }

        public BarAnimation(BarType barType, int x, int y, Character chara) {
            this(barType, chara);
            _isAlwaysShow = true;
            setLocation(x, y);
            setVisible(true);
        }

        public void setLocation(int x, int y) {
            _frame.setLocation(x - _frame.getWidth() / 2, y);
            if (_background != null)
                _background.setLocation(_frame.getX() + 6, y + 6);
            _main.setLocation(_frame.getX() + 6, y + 6);
        }

        public void setVisible(boolean isVisible) {
            _frame.setVisible(isVisible);
            if (_background != null && _value > DELTA)
                _background.setVisible(isVisible);
            if (_value > DELTA)
                _main.setVisible(isVisible);
        }

        public void setValue(double value) {
            _value = value;
            _count = 0;
            _main.setValue(_value);
            setVisible(true);
        }

        public int getWidth() {
            return _frame.getWidth();
        }

        @Override
        public void move() {
            double value;
            if (!_isAlwaysShow)
                setLocation(convertX(_chara.x()), convertY(_chara.y()) - 225);
            switch (_barType) {
                case GREEN:
                case RED:
                    value = _chara.hp() / (double) _chara.hitpoints();
                    if (Math.abs(_value - value) > DELTA)
                        setValue(value);
                    else if (_count < MOVE_TIME / SPEED)
                        _background.setValue(_value + (_background.getValue() - _value)
                                * Math.cos(Math.PI / 2 * ++_count / MOVE_TIME * SPEED));
                    else if (++_count == (DELAY + MOVE_TIME) / SPEED)
                        setVisible(_isAlwaysShow);
                    break;
                case BLUE:
                    value = _chara.tp() / 1000.0;
                    if (Math.abs(_value - value) > DELTA)
                        setValue(value);
                    break;
            }
        }

        @Override
        public void show() {
            _frame.show();
            if (_background != null)
                _background.show();
            _main.show();
        }

        @Override
        public void release() {
            _frame.release();
            if (_background != null)
                _background.release();
            _main.release();
            _frame = null;
            _background = null;
            _main = null;
        }

        private class Bar implements GameObject {
            final int LENGTH = 110;

            private MovingBitmap _left, _center, _right;
            private double _value;

            public Bar(BarType barType) {
                _left = new MovingBitmap("bar/" + barType.name().toLowerCase() + "Rnd.png");
                _center = new MovingBitmap("bar/" + barType.name().toLowerCase() + "Sq.png").resize(LENGTH,
                        _left.getHeight());
                _right = new MovingBitmap("bar/" + barType.name().toLowerCase() + "Rnd.png").inversion();
                setLocation(0, 0);
            }

            public int getWidth() {
                return _right.getX() + _right.getWidth() - _left.getX();
            }

            public int getX() {
                return _left.getX();
            }

            public void setLocation(int x, int y) {
                _left.setLocation(x, y);
                _center.setLocation(_left.getX() + _left.getWidth(), y);
                _right.setLocation(_center.getX() + _center.getWidth() + 1, y);
            }

            public void setVisible(boolean isVisible) {
                _left.setVisible(isVisible);
                _center.setVisible(isVisible);
                _right.setVisible(isVisible);
            }

            public void setValue(double value) {
                _value = value;
                if (_value > DELTA) {
                    _center.resize((int) (LENGTH * _value), _center.getHeight());
                    setLocation(_left.getX(), _left.getY());
                    setVisible(true);
                } else
                    setVisible(false);
            }

            public double getValue() {
                return _value;
            }

            @Override
            public void move() {
            }

            @Override
            public void show() {
                _left.show();
                _center.show();
                _right.show();
            }

            @Override
            public void release() {
                _left.release();
                _center.release();
                _right.release();
                _left = null;
                _center = null;
                _right = null;
            }
        }
    }

    private static class Loading extends Animation {
        public Loading() {
            for (int i = 4; i >= 0; i--)
                addFrame(new MovingBitmap("loading/" + i + ".png").resize(1920, 1080));
            setLocation((Game.GAME_FRAME_WIDTH - getWidth()) / 2, (Game.GAME_FRAME_HEIGHT - getHeight()) / 2);
            setRepeating(false);
        }
    }

    private class Result extends MovingBitmap {
        final int MOVE_SPEED = 30;
        final int MOVE_TIME = 4;
        final double MAG = 1.2;

        private int _count;

        public Result(String result) {
            super("value/result/" + result.toLowerCase() + ".png");
            setLocation((Game.GAME_FRAME_WIDTH - getWidth()) / 2, (Game.GAME_FRAME_HEIGHT - getHeight()) / 2 - 150);
            _count = 0;
        }

        @Override
        public void move() {
            if (!_visible)
                return;
            if (_count < 4 * MOVE_TIME / SPEED) {
                resize(MAG);
                setLocation((Game.GAME_FRAME_WIDTH - getWidth()) / 2, getY() - (getHeight() - (int) ((double) getHeight() / MAG)) / 2);
                setLocation(
                        getX(), getY() - (int) (MOVE_SPEED * SPEED
                                * Math.cos(Math.PI / 2 * _count / MOVE_TIME * SPEED)));
                _count += _count < 2 * MOVE_TIME / SPEED ? 1 : 2;
            }
            else {
                _confirmBtn.setVisible(true);
                _confirmText.setVisible(true);
            }
        }
    }

    private static class CharacterComparator implements Comparator<Character> {
        @Override
        public int compare(Character char1, Character char2) {
            return char1.y() - char2.y() - 1;
        }
    }

    @Override
    public void release() {
        _timeTask.cancel();
        super.release();
        _ground.release();
        _bgm.release();
        _charAnimations.clear();
        _valueAnimationNums.clear();
        _ground = null;
        _timer = null;
        _timeTask = null;
        _charAnimations = null;
        _valueAnimationNums = null;
        _bgm = null;
        ((Audio) _data.get(InitPage.BGM)).play();
    }

    @Override
    public void pause() {
        _bgm.pause();
    }

    @Override
    public void resume() {
        _bgm.resume();
    }
}
