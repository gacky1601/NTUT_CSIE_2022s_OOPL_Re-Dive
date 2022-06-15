package princess.connect.state.PlayerMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import princeconnect.game.R;
import princess.connect.Game;
import princess.connect.GameObject;
import princess.connect.Pointer;
import princess.connect.PointerEventHandler;
import princess.connect.baseClass.Character;
import princess.connect.character.Pecorine;
import princess.connect.core.MovingBitmap;
import princess.connect.engine.GameEngine;
import princess.connect.extend.CharacterButton;
import princess.connect.state.InitPage;

public class CharaterPage extends PlayerMenu {
    public CharaterPage(GameEngine engine) { super(engine);}

    public void initialize(Map<String, Object> data) {
        super.initialize(data);
        _background.loadBitmap(new MovingBitmap(R.drawable.bg_530011));
        _background.resize(1920, 1080).setLocation((Game.GAME_FRAME_WIDTH - _background.getWidth()) / 2, (Game.GAME_FRAME_HEIGHT - _background.getHeight()) / 2);
        CharacterViewer viewer = new CharacterViewer((List<Character>) data.get(InitPage.ALL_CHARACTER_LIST),(List<Character>) data.get(InitPage.PLAYER_CHARACTER_LIST));
        addGameObject(viewer);
        addPointerEventHandler(viewer);
        super.initButton();
    }

    private class CharacterViewer implements GameObject, PointerEventHandler {
        private List<MovingBitmap> _frames;
        private AdventurePage.GraySquare _background;
        private List<CharacterBigButton> _charBtns;
        private Pointer _pointer;
        private boolean _isInGarySquare;
        private int _charsNum;

        public CharacterViewer(List<Character> allCharsList, List<Character> playerCharsList) {
            _charBtns = new ArrayList<>();
            Collections.sort(allCharsList, new AdventurePage.CharacterComparatorID());
            Collections.sort(playerCharsList, new AdventurePage.CharacterComparatorID());
            _charsNum = 0;
            for (Character chara : allCharsList) {
                if (_charsNum < playerCharsList.size() && chara.id() == playerCharsList.get(_charsNum).id())
                    _charBtns.add(_charsNum, new CharacterBigButton(playerCharsList.get(_charsNum++)));
                else {
                    _charBtns.add(new CharacterBigButton(chara));
                    _charBtns.get(_charBtns.size() - 1).setGray(true);
                }
            }
            _frames = new ArrayList<>();
            for (int i = 0; i < 9; i++)
                _frames.add(new MovingBitmap("interface/white/" + i + ".png"));
            _frames.get(1).resize(_frames.get(1).getWidth() * 16, _frames.get(1).getHeight());
            _frames.get(7).resize(_frames.get(1).getWidth(), _frames.get(1).getHeight());
            _frames.get(3).resize(_frames.get(3).getWidth(), _frames.get(3).getHeight() * 18);
            _frames.get(5).resize(_frames.get(3).getWidth(), _frames.get(3).getHeight());
            _frames.get(4).resize(_frames.get(1).getWidth(), _frames.get(3).getHeight());
            _background = new AdventurePage.GraySquare(_frames.get(1).getWidth(), _frames.get(3).getHeight());
            initLocation();
            MovingBitmap tmp = CharaterPage.this._background;
            _frames.add(tmp.crop(0, 1, tmp.getWidth(), getY() - tmp.getY() + 2).setLocation(tmp.getX(), tmp.getY() + 1));
            _frames.get(9).resize(_frames.get(9).getWidth(), _frames.get(9).getHeight() - 2);
            _frames.add(tmp
                    .crop(0, getY() + getHeight() - tmp.getY() - 1, tmp.getWidth(),
                            tmp.getY() + tmp.getHeight() - getY() - getHeight())
                    .setLocation(tmp.getX(), getY() + getHeight() - 2));
        }

        private void initLocation() {
            int x = (Game.GAME_FRAME_WIDTH
                    - (_frames.get(0).getWidth() + _frames.get(1).getWidth() + _frames.get(2).getWidth())) / 2;
            int y = (int) (Game.GAME_FRAME_HEIGHT * 0.05);
            int spacing = 25;
            MovingBitmap bitmap;
            for (int i = 0; i < 9; i++) {
                bitmap = _frames.get(i);
                bitmap.setLocation(x, y);
                if (i % 3 == 2) {
                    x = _frames.get(0).getX();
                    y += bitmap.getHeight();
                } else
                    x += bitmap.getWidth();
            }
            x = _frames.get(0).getX() + _frames.get(0).getWidth();
            y = _frames.get(0).getY() + _frames.get(0).getHeight();
            _background.setLocation(x, y);
            x += spacing + 20;
            y += spacing;
            for (int i = 0; i < _charBtns.size(); i++) {
                _charBtns.get(i).setLocation(x, y);
                x += _charBtns.get(i).getWidth() + spacing;
                if ((i < _charsNum && i % 3 == 2) || (i == _charsNum - 1)) {
                    x -= (i % 3 + 1) * (_charBtns.get(i).getWidth() + spacing);
                    y += _charBtns.get(i).getHeight() + spacing;
                } else if (i > _charsNum && (i - _charsNum) % 3 == 2) {
                    x -= ((i - _charsNum) % 3 + 1) * (_charBtns.get(i).getWidth() + spacing);
                    y += _charBtns.get(i).getHeight() + spacing;
                }
            }
        }

        public int getX() {
            return _frames.get(0).getX();
        }

        public int getY() {
            return _frames.get(0).getY();
        }

        public int getWidth() {
            return _frames.get(0).getWidth() + _frames.get(1).getWidth() + _frames.get(2).getWidth();
        }

        public int getHeight() {
            return _frames.get(0).getHeight() + _frames.get(3).getHeight() + _frames.get(6).getHeight();
        }

        private boolean contains(Pointer pointer, int x, int y, int width, int height) {
            return pointer.getX() >= x && pointer.getX() < (x + width) && pointer.getY() >= y
                    && pointer.getY() < (y + height);
        }

        @Override
        public void move() {
            for (MovingBitmap bitmap : _frames)
                bitmap.move();
            for (CharacterBigButton btn : _charBtns)
                btn.move();
            _background.move();
        }

        @Override
        public void show() {
            _frames.get(4).show();
            _background.show();
            for (CharacterBigButton btn : _charBtns)
                btn.show();
            for (int i = 9; i < 11; i++)
                _frames.get(i).show();
            for (int i = 0; i < 9; i++)
                if (i != 4)
                    _frames.get(i).show();
        }

        @Override
        public void release() {
            for (MovingBitmap bitmap : _frames)
                bitmap.release();
            for (CharacterBigButton btn : _charBtns)
                btn.release();
            _background.release();
            _frames.clear();
            _charBtns.clear();
            _frames = null;
            _charBtns = null;
            _background = null;
        }

        @Override
        public boolean pointerPressed(Pointer actionPointer, List<Pointer> pointers) {
            if (pointers.size() == 1) {
                _pointer = actionPointer;
                _isInGarySquare = contains(_pointer, _background.getX(), _background.getY(),
                        _background.getWidth(), _background.getHeight());
                return true;
            }
            return false;
        }

        @Override
        public boolean pointerMoved(Pointer actionPointer, List<Pointer> pointers) {
            if (pointers.size() == 1 && _isInGarySquare) {
                int y = actionPointer.getY() - _pointer.getY();
                for (CharacterBigButton btn : _charBtns)
                    btn.setLocation(btn.getX(), btn.getY() + y);
                _pointer = actionPointer;
            }
            return false;
        }

        @Override
        public boolean pointerReleased(Pointer actionPointer, List<Pointer> pointers) {
            if (pointers.size() == 1 && _isInGarySquare) {
                if (_charBtns.get(0).getY() > _background.getY() + 25 || _charBtns.size() <= 6) {
                    int y = _background.getY() + 25 - _charBtns.get(0).getY();
                    for (CharacterBigButton btn : _charBtns)
                        btn.setLocation(btn.getX(), btn.getY() + y);
                }
                else if (_charBtns.get(_charBtns.size() - 1).getY() + _charBtns.get(_charBtns.size() - 1).getHeight()
                        < _background.getY() + _background.getHeight() - 25 && _charBtns.size() > 6) {
                    int y = _background.getY() + _background.getHeight() - 25
                            - _charBtns.get(_charBtns.size() - 1).getY()
                            - _charBtns.get(_charBtns.size() - 1).getHeight();
                    for (CharacterBigButton btn : _charBtns)
                        btn.setLocation(btn.getX(), btn.getY() + y);
                }
                _isInGarySquare = false;
                return true;
            }
            return false;
        }
    }

    private class CharacterBigButton implements GameObject {
        private Character _chara;
        private MovingBitmap _icon, _iconFrame, _gray, _frame;
        private List<MovingBitmap> _stars;
        private int _x, _y;
        private boolean _isGray, _isVisible;

        public CharacterBigButton(Character chara) {
            _chara = chara;
            _icon = new MovingBitmap("character/unit_plate_" + _chara.id() + "11.png").resize(0.65);
            _iconFrame = new MovingBitmap("character/unit_plate_frame.png").resize(_icon.getWidth(), _icon.getHeight());
            _gray = new MovingBitmap("character/unit_plate_gray.png");
            _gray.resize(_icon.getWidth(), _icon.getHeight());
            _gray.setVisible(false);
            if (_chara.rank() <= 1)
                _frame = new MovingBitmap("frame/blueRect.png");
            else if (_chara.rank() <= 3)
                _frame = new MovingBitmap("frame/brownRect.png");
            else if (_chara.rank() <= 6)
                _frame = new MovingBitmap("frame/greyRect.png");
            else if (_chara.rank() <= 10)
                _frame = new MovingBitmap("frame/yellowRect.png");
            _frame.resize(_icon.getWidth() + 12, _icon.getHeight() + 2);
            _stars = new ArrayList<>();
            for(int i = 0; i < 5; i++) {
                if (i < _chara.star())
                    _stars.add(new MovingBitmap("frame/star.png").resize(0.85));
                else
                    _stars.add(new MovingBitmap("frame/grayStar.png").resize(0.85));
            }
            setVisible(true);
        }

        public int id() {
            return _chara.id();
        }

        public Character chara() {
            return _chara;
        }

        public boolean isGray() {
            return _isGray;
        }

        public void setGray(boolean isGray) {
            _isGray = isGray;
            _gray.setVisible(_isGray);
        }

        public boolean isVisible() {
            return _isVisible;
        }

        public int getX() {
            return _x;
        }

        public int getY() {
            return _y;
        }

        public int getWidth() {
            return _frame.getWidth();
        }

        public int getHeight() {
            return _frame.getHeight();
        }

        public void setVisible(boolean isVisible) {
            _isVisible = isVisible;
            _icon.setVisible(_isVisible);
            _gray.setVisible(_isVisible && _isGray);
            _frame.setVisible(_isVisible);
        }

        public void setLocation(int x, int y) {
            _x = x;
            _y = y;
        }

        @Override
        public void move() {
            _icon.setLocation(_x + 1, _y + 1);
            _iconFrame.setLocation(_icon.getX(), _icon.getY());
            for (int i = 0; i < _stars.size(); i++)
                _stars.get(i).setLocation(_icon.getX() + i * _stars.get(i).getWidth() + 10 , _icon.getY() + _icon.getHeight() - _stars.get(i).getHeight() - 10);
            _gray.setLocation(_icon.getX(), _icon.getY());
            _frame.setLocation(_x, _y);
        }

        @Override
        public void show() {
            _icon.show();
            _iconFrame.show();
            for (MovingBitmap bitmap : _stars)
                bitmap.show();
            _gray.show();
            _frame.show();
        }

        @Override
        public void release() {
            _icon.release();
            _iconFrame.release();
            for (MovingBitmap bitmap : _stars)
                bitmap.release();
            _gray.release();
            _frame.release();
            _icon = null;
            _iconFrame = null;
            _stars = null;
            _gray = null;
            _frame = null;
        }
    }
}
