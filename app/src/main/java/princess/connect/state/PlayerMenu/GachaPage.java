package princess.connect.state.PlayerMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import princeconnect.game.R;
import princess.connect.Game;
import princess.connect.GameObject;
import princess.connect.baseClass.Character;
import princess.connect.baseClass.Gacha;
import princess.connect.core.MovingBitmap;
import princess.connect.engine.GameEngine;
import princess.connect.extend.BitmapButton;
import princess.connect.extend.CharacterButton;
import princess.connect.state.InitPage;

public class GachaPage extends PlayerMenu{
    public GachaPage(GameEngine engine) {
        super(engine);
    }

    private Map<String, Object> _data;
    private List<CharacterButton> _charBtns;

    public void initialize(Map<String, Object> data) {
        super.initialize(data);
        _data = data;
        _charBtns = new ArrayList<>();
        _background.loadBitmap(new MovingBitmap(R.drawable.bg_530041));
        _background.resize(1920, 1080).setLocation((Game.GAME_FRAME_WIDTH - _background.getWidth()) / 2, (Game.GAME_FRAME_HEIGHT - _background.getHeight()) / 2);
        TrnWhiteSquare square = new TrnWhiteSquare(1000,400);
        square.setLocation((Game.GAME_FRAME_WIDTH - square.getWidth()) / 2, (Game.GAME_FRAME_HEIGHT - square.getHeight()) / 2 - 100);
        addGameObject(square);
        Gacha gacha = new Gacha((List<princess.connect.baseClass.Character>) data.get(InitPage.ALL_CHARACTER_LIST));
        BitmapButton confirm = new BitmapButton("interface/button/blue.png").resize(1.5);
        confirm.setLocation((Game.GAME_FRAME_WIDTH - confirm.getWidth()) / 2 + 150, (Game.GAME_FRAME_HEIGHT - confirm.getHeight()) / 2 + 175);
        confirm.addButtonEventHandler(button -> {
            List<Character> chars = gacha.draw10();
            displayResult(chars);
            updatePlayerCharacter(chars);
        });
        MovingBitmap confirmText = new MovingBitmap(R.drawable.text_tryagain).resize(0.25);
        confirmText.setLocation(confirm.getX() + confirm.getWidth() / 2 - confirmText.getWidth() / 2,
                confirm.getY() + confirm.getHeight() / 2 - confirmText.getHeight() / 2);
        BitmapButton cancle = new BitmapButton("interface/button/white.png").resize(1.5);
        cancle.setLocation((Game.GAME_FRAME_WIDTH - cancle.getWidth()) / 2 - 150, (Game.GAME_FRAME_HEIGHT - cancle.getHeight()) / 2 + 175);
        cancle.addButtonEventHandler(button -> clearResult());
        MovingBitmap cancleText = new MovingBitmap(R.drawable.text_cancle).resize(0.25);
        cancleText.setLocation(cancle.getX() + cancle.getWidth() / 2 - cancleText.getWidth() / 2,
                cancle.getY() + cancle.getHeight() / 2 - cancleText.getHeight() / 2);
        addPointerEventHandler(confirm);
        addPointerEventHandler(cancle);
        addGameObject(confirm);
        addGameObject(confirmText);
        addGameObject(cancle);
        addGameObject(cancleText);
        super.initButton();
    }

    @Override
    public void move() {
        super.move();
        for (CharacterButton btn : _charBtns)
            btn.move();
    }

    @Override
    public void show() {
        super.show();
        for (CharacterButton btn : _charBtns)
            btn.show();
    }

    private void displayResult(List<Character> chars) {
        clearResult();
        for (int i = 0; i < chars.size(); i++) {
            Character chara = chars.get(i);
            CharacterButton btn = new CharacterButton(chara, 1);
            btn.setLocation(btn.getWidth() + 175 * (i % 5) + 175, btn.getHeight() + 175 * (i / 5) - 20);
            _charBtns.add(btn);
        }
    }

    private void updatePlayerCharacter(List<Character> chars) {
        List<Character> playerCharsList = (List<Character>) _data.get(InitPage.PLAYER_CHARACTER_LIST);
        List<Character> newPlayerCharsList = new ArrayList<>();
        newPlayerCharsList.addAll(playerCharsList);
        for (Character chara : chars) {
            boolean isHave = false;
            for (Character playerChara : newPlayerCharsList)
                if (chara.id() == playerChara.id()) {
                    playerChara.setRank(playerChara.rank() * 2);
                    if (playerChara.rank() > 7) {
                        playerChara.setRank(7);
                    }
                    isHave = true;
                    break;
                }
            if (!isHave) {
                try {
                    newPlayerCharsList.add(chara.getClass().newInstance());
                } catch (Exception ignore) {}
            }
        }
        _data.put(InitPage.PLAYER_CHARACTER_LIST, newPlayerCharsList);
    }

    private void clearResult() {
        if (!_charBtns.isEmpty()) {
            for (CharacterButton btn : _charBtns) {
                btn.setVisible(false);
                btn.release();
            }
            _charBtns.clear();
        }
    }

    private class TrnWhiteSquare implements GameObject {
        private List<MovingBitmap> _bitmaps;

        public TrnWhiteSquare(int width, int height) {
            _bitmaps = new ArrayList<>();
            for (int i = 0; i < 9; i++)
                _bitmaps.add(new MovingBitmap("interface/trnWhite/" + i + ".png"));
            _bitmaps.get(1).resize(width - 2 * _bitmaps.get(0).getWidth(), _bitmaps.get(1).getHeight());
            _bitmaps.get(7).resize(_bitmaps.get(1).getWidth(), _bitmaps.get(1).getHeight());
            _bitmaps.get(3).resize(_bitmaps.get(3).getWidth(), height - 2 * _bitmaps.get(0).getHeight());
            _bitmaps.get(5).resize(_bitmaps.get(3).getWidth(), _bitmaps.get(3).getHeight());
            _bitmaps.get(4).resize(_bitmaps.get(1).getWidth(), _bitmaps.get(3).getHeight());
        }

        public void setLocation(int x, int y) {
            MovingBitmap bitmap;
            for (int i = 0; i < 9; i++) {
                bitmap = _bitmaps.get(i);
                bitmap.setLocation(x, y);
                bitmap.show();
                if (i % 3 == 2) {
                    x = _bitmaps.get(0).getX();
                    y += bitmap.getHeight();
                } else
                    x += bitmap.getWidth();
            }
        }

        public int getX() {
            return _bitmaps.get(0).getX();
        }

        public int getY() {
            return _bitmaps.get(0).getY();
        }

        public int getWidth() {
            return _bitmaps.get(0).getWidth() + _bitmaps.get(1).getWidth() + _bitmaps.get(2).getWidth();
        }

        public int getHeight() {
            return _bitmaps.get(0).getHeight() + _bitmaps.get(3).getHeight() + _bitmaps.get(6).getHeight();
        }

        @Override
        public void move() {
        }

        @Override
        public void show() {
            for (MovingBitmap bitmap : _bitmaps)
                bitmap.show();
        }

        @Override
        public void release() {
            for (MovingBitmap bitmap : _bitmaps)
                bitmap.release();
            _bitmaps.clear();
            _bitmaps = null;
        }
    }
}
