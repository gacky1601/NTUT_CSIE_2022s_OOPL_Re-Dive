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
        public CharacterAnimation(String path) {
            try {
                for (String asset : runtime.getAssets().list(path))
                    addFrame(new MovingBitmap(path + "/" + asset));
            } catch (IOException e) {
            }
        }

        @Override
        public void setLocation(int x, int y) {
            super.setLocation(x - (int) (getWidth() / 2), y - (int) (getHeight() / 2));
        }
    }

    private void loadAllCharacterAnimation(List<Character> left, List<Character> right) {
        _charAnimaLeft = new ArrayList<>();
        _charAnimaRight = new ArrayList<>();
        CharacterAnimation tmp = null;
        for (Character chara : left) {
            List<CharacterAnimation> charAnimaList = new ArrayList<>();
            for (Character.Action action : Character.Action.values())
                charAnimaList.add(new CharacterAnimation(
                        "action/" + chara.name() + "/" + action.name().toLowerCase()));
            _charAnimaLeft.add(charAnimaList);
        }
        for (Character chara : right) {
            List<CharacterAnimation> charAnimaList = new ArrayList<>();
            for (Character.Action action : Character.Action.values())
            {
                tmp = new CharacterAnimation(
                        "action/" + chara.name() + "/" + action.name().toLowerCase());
                tmp.inversion();
                charAnimaList.add(tmp);
            }
            _charAnimaRight.add(charAnimaList);
        }
    }

    @Override
    public void initialize(Map<String, Object> data) {
        MovingBitmap background = new MovingBitmap(R.drawable.bg_100021, -200, -200);
        background.resize(1920, 1080);
        addGameObject(background);
        List<Character> characterLeft = Arrays.asList(new Pecorine());
        List<Character> characterRight = Arrays.asList(new Kokoro());
        _ground = new BattleGround(characterLeft, characterRight);
        _ground.initialize();
        _ground.main();
        loadAllCharacterAnimation(characterLeft, characterRight);
        main();
    }

    private void main() {
        final int[] x = {0, 1400};
        _charAnimaLeft.get(0).get(Character.Action.RUN.ordinal()).setLocation(x[0], 400);
        addGameObject(_charAnimaLeft.get(0).get(Character.Action.RUN.ordinal()));
        _charAnimaRight.get(0).get(Character.Action.RUN.ordinal()).setLocation(x[1], 400);
        addGameObject(_charAnimaRight.get(0).get(Character.Action.RUN.ordinal()));

        _timer = new Timer();
        _timeTask = new TimerTask() {
            @Override
            public void run() {
                if(x[0] < 500)
                {
                    _charAnimaLeft.get(0).get(Character.Action.RUN.ordinal()).setLocation(x[0], 400);
                    x[0]++;
                    _charAnimaRight.get(0).get(Character.Action.RUN.ordinal()).setLocation(x[1], 400);
                    x[1]--;
                }
                else
                {
                    removeGameObject(_charAnimaLeft.get(0).get(Character.Action.RUN.ordinal()));
                    _charAnimaLeft.get(0).get(Character.Action.IDLE.ordinal()).setLocation(x[0], 400);
                    addGameObject(_charAnimaLeft.get(0).get(Character.Action.IDLE.ordinal()));
                    removeGameObject(_charAnimaRight.get(0).get(Character.Action.RUN.ordinal()));
                    _charAnimaRight.get(0).get(Character.Action.IDLE.ordinal()).setLocation(x[1], 400);
                    addGameObject(_charAnimaRight.get(0).get(Character.Action.IDLE.ordinal()));
                }
            }
        };
        _timer.scheduleAtFixedRate(_timeTask, 0, 3);
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
}
