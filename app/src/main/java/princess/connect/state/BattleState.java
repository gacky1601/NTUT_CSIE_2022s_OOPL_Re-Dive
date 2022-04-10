package princess.connect.state;

import static princess.connect.GameView.runtime;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    private BattleGround _ground;
    private Timer _timer;
    private TimerTask _timeTask;
    private List<List<CharacterAnimation>> _charAnimaLeft;
    private List<List<CharacterAnimation>> _charAnimaRight;

    public BattleState(GameEngine engine) {
        super(engine);
    }

    private class CharacterAnimation extends Animation {
        public static final int ANIMATION_FRAME = 20;

        public CharacterAnimation(String path) {
            try {
                for (String asset : runtime.getAssets().list(path))
                    addFrame(new MovingBitmap(path + "/" + asset));
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
            x = (int) (Game.GAME_FRAME_WIDTH * 2 * x / BattleGround.WIDTH - Game.GAME_FRAME_WIDTH / 2);
            y = (int) (Game.GAME_FRAME_HEIGHT / 8 * y / BattleGround.HEIGHT + Game.GAME_FRAME_HEIGHT / 2);
            super.setLocation(x - (int) (getWidth() / 2), y - (int) (getHeight() / 2));
        }

        public void nextFrame() {
            super.move();
        }
    }

    private void loadAllCharacterAnimation(List<Character> left, List<Character> right) {
        _charAnimaLeft = new ArrayList<>();
        _charAnimaRight = new ArrayList<>();
        CharacterAnimation tmp = null;
        for (Character chara : left) {
            List<CharacterAnimation> charAnimaList = new ArrayList<>();
            for (Character.Action action : Character.Action.values()) {
                tmp = new CharacterAnimation(
                        "action/" + chara.name() + "/" + action.name().toLowerCase());
                charAnimaList.add(tmp);
                addGameObject(tmp);
            }
            _charAnimaLeft.add(charAnimaList);
        }
        for (Character chara : right) {
            List<CharacterAnimation> charAnimaList = new ArrayList<>();
            for (Character.Action action : Character.Action.values()) {
                tmp = new CharacterAnimation(
                        "action/" + chara.name() + "/" + action.name().toLowerCase());
                tmp.inversion();
                charAnimaList.add(tmp);
                addGameObject(tmp);
            }
            _charAnimaRight.add(charAnimaList);
        }
    }

    @Override
    public void initialize(Map<String, Object> data) {
        MovingBitmap background = new MovingBitmap(R.drawable.bg_100021, -200, -200);
        background.resize(1920, 1080);
        addGameObject(background);
        List<Character> characterLeft = Arrays.asList(new Pecorine(), new Kokoro());
        List<Character> characterRight = Arrays.asList(new Pecorine());
        _ground = new BattleGround(characterLeft, characterRight);
        _ground.initialize();
        loadAllCharacterAnimation(characterLeft, characterRight);
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
        _timer.scheduleAtFixedRate(_timeTask, 0, 1000 / BattleGround.FRAME);
    }

    private void changeAction() {
        List<Character> left = _ground.character(Character.Direction.LEFT);
        List<Character> right = _ground.character(Character.Direction.RIGHT);
        for (int i = 0; i < left.size(); i++) {
            Character chara = left.get(i);
            List<CharacterAnimation> charAnimaList = _charAnimaLeft.get(i);
            if (chara.isChangeAction()) {
                if (chara.preAction() != null)
                    charAnimaList.get(chara.preAction().ordinal()).setVisible(false);
                charAnimaList.get(chara.action().ordinal())
                        .setCurrentFrameIndex(charAnimaList.get(chara.action().ordinal()).getFrameCount() - 1);
                charAnimaList.get(chara.action().ordinal()).setLocation(chara.x(), chara.y());
                charAnimaList.get(chara.action().ordinal()).setVisible(true);
            }
        }
        for (int i = 0; i < right.size(); i++) {
            Character chara = right.get(i);
            List<CharacterAnimation> charAnimaList = _charAnimaRight.get(i);
            if (chara.isChangeAction()) {
                if (chara.preAction() != null)
                    charAnimaList.get(chara.preAction().ordinal()).setVisible(false);
                charAnimaList.get(chara.action().ordinal())
                        .setCurrentFrameIndex(charAnimaList.get(chara.action().ordinal()).getFrameCount() - 1);
                charAnimaList.get(chara.action().ordinal()).setLocation(chara.x(), chara.y());
                charAnimaList.get(chara.action().ordinal()).setVisible(true);
            }
        }
    }

    private void nextFrame() {
        List<Character> left = _ground.character(Character.Direction.LEFT);
        List<Character> right = _ground.character(Character.Direction.RIGHT);
        for (int i = 0; i < left.size(); i++) {
            Character chara = left.get(i);
            CharacterAnimation charAnimaion = _charAnimaLeft.get(i).get(chara.action().ordinal());
            charAnimaion.nextFrame();
            charAnimaion.setLocation(chara.x(), chara.y());
        }
        for (int i = 0; i < right.size(); i++) {
            Character chara = right.get(i);
            CharacterAnimation charAnimaion = _charAnimaRight.get(i).get(chara.action().ordinal());
            charAnimaion.nextFrame();
            charAnimaion.setLocation(chara.x(), chara.y());
        }
    }

    @Override
    public void release() {
        super.release();
        _ground.release();
        _timer = null;
        _timeTask.cancel();
        _timeTask = null;
        for (List<CharacterAnimation> charAnimaList : _charAnimaLeft) {
            for (CharacterAnimation charAnima : charAnimaList)
                if (!charAnima.isNull())
                    charAnima.release();
            charAnimaList.clear();
        }
        _charAnimaLeft = null;
        for (List<CharacterAnimation> charAnimaList : _charAnimaRight) {
            for (CharacterAnimation charAnima : charAnimaList)
                if (!charAnima.isNull())
                    charAnima.release();
            charAnimaList.clear();
        }
        _charAnimaRight = null;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void move() {
        super.move();

        List<Character> left = _ground.character(Character.Direction.LEFT);
        List<Character> right = _ground.character(Character.Direction.RIGHT);
        for (int i = 0; i < left.size(); i++) {
            Log.d("TAG", "move: " + left.get(i).action() + " "
                    + _charAnimaLeft.get(i).get(left.get(i).action().ordinal()).getCurrentFrameIndex());
        }
    }
}
