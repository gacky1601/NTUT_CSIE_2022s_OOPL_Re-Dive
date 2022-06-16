package princess.connect.state.PlayerMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import princeconnect.game.R;
import princess.connect.Game;
import princess.connect.GameObject;
import princess.connect.Pointer;
import princess.connect.PointerEventHandler;
import princess.connect.baseClass.Area;
import princess.connect.baseClass.Character;
import princess.connect.core.MovingBitmap;
import princess.connect.engine.GameEngine;
import princess.connect.extend.Animation;
import princess.connect.extend.BitmapButton;
import princess.connect.extend.CharacterButton;
import princess.connect.state.InitPage;

public class AdventurePage extends PlayerMenu {
    public AdventurePage(GameEngine engine) {
        super(engine);
    }

    public static final String SELECTED_CHARACTER = "SELECTED_CHARACTER";
    public static final String SELECTED_LEVEL = "SELECTED_LEVEL";
    public static final String SELECTED_LEVEL_ID = "SELECTED_LEVEL_ID";

    private Map<String, Object> _data;
    private CharacterSelector _selector;
    private List<Character> _selectChars;
    private List<Area> _areaList;
    private boolean _isSelect;
    private Loading _loading;

    public void initialize(Map<String, Object> data) {
        super.initialize(data);
        _selectChars = new ArrayList<>();
        _data = data;
        _data.put(SELECTED_CHARACTER, _selectChars);
        _areaList = (List<Area>) _data.get(InitPage.ALL_AREA_LIST);
        initArea();
        _selector = new CharacterSelector((List<Character>) _data.get(InitPage.PLAYER_CHARACTER_LIST));
        _selector.setVisible(false);
        addGameObject(_selector);
        addPointerEventHandler(_selector);
        addBtnsToGameObject();
        _loading = new Loading();
        _loading.setVisible(false);
        addGameObject(_loading);
    }

    private double distance(Area.AreaLevel level1, Area.AreaLevel level2) {
        return Math.sqrt(Math.pow(convertX(level1.x()) - convertX(level2.x()), 2) + Math.pow(convertY(level1.y()) - convertY(level2.y()), 2));
    }

    private double slope(Area.AreaLevel level1, Area.AreaLevel level2) {
        return (double) (convertY(level1.y()) - convertY(level2.y())) / (convertX(level1.x()) - convertX(level2.x()));
    }
    
    private void initArea() {
        Area area = _areaList.get(((int[]) _data.get(InitPage.LEVEL_PROGRESS))[0] - 1);
        Area.AreaLevel level, preLevel = null;
        MovingBitmap worldmap = new MovingBitmap("map/worldmap.png");
        MovingBitmap road = new MovingBitmap("map/road.png");
        _background.loadBitmap(worldmap.crop(area.mapX() - 1024 / 2, area.mapY() - 1024 / 2, 1024, 1024));
        _background.resize(1920, 1080).setLocation(-200,-200);
        for (int i = 0; i < area.levels().size(); i++) {
            if (i > ((int[]) _data.get(InitPage.LEVEL_PROGRESS))[1] - 1)
                break;
            level = area.levels().get(i);
            if (preLevel != null) {
                int length = (int) distance(level, preLevel);
                double radians = Math.atan(slope(level, preLevel));
                MovingBitmap newRoad = road.crop(0, (road.getHeight() - length) / 2, length, length);
                newRoad.rotate((int) Math.toDegrees(radians));
                newRoad.setLocation(convertX(preLevel.x()) - length / 2 + (int) (length / 2 * Math.cos(radians)), convertY(preLevel.y()) - length / 2 + (int) (length / 2 * Math.sin(radians)));
                addGameObject(newRoad);
            }
            preLevel = level;
        }
        for (int i = 0; i < area.levels().size(); i++) {
            if (i > ((int[]) _data.get(InitPage.LEVEL_PROGRESS))[1] - 1)
                break;
            level = area.levels().get(i);
            LevelButton btn = new LevelButton(level);
            btn.setID(((int[]) _data.get(InitPage.LEVEL_PROGRESS))[0], i + 1);
            btn.addButtonEventHandler(button -> {
                _data.put(SELECTED_LEVEL, btn._level);
                _data.put(SELECTED_LEVEL_ID, btn._id);
                _selector.setVisible(true);
            });
            addPointerEventHandler(btn);
            addGameObject(btn);
        }
    }

    private int convertX(int x) {
        return (int) (Game.GAME_FRAME_WIDTH * 0.8 * x / Area.AreaLevel.WIDTH + Game.GAME_FRAME_WIDTH * 0.1);
    }

    private int convertY(int y) {
        return (int) (Game.GAME_FRAME_HEIGHT * 0.7 * y / Area.AreaLevel.HEIGHT + Game.GAME_FRAME_HEIGHT * 0.05);
    }

    private class LevelButton extends BitmapButton {
        private final Area.AreaLevel _level;
        private int[] _id;
        private MovingBitmap _icon, _base;

        public LevelButton(Area.AreaLevel level) {
            super(R.drawable.none);
            _level = level;
            _id = new int[2];
            _base = new MovingBitmap("map/base.png");
            _icon = new MovingBitmap("map/icon_map_" + level.icon() + ".png");
            setLocation(level.x(), level.y());
        }

        public void setID(int a, int b) {
            _id[0] = a;
            _id[1] = b;
        }

        @Override
        public void setLocation(int x, int y) {
            x = convertX(x);
            y = convertY(y);
            _base.setLocation(x - _base.getWidth() / 2, y - _base.getHeight() / 2);
            _icon.setLocation(x - _icon.getWidth() / 2, y - _icon.getHeight() / 2 - 40);
        }

        public boolean contains(Pointer pointer, MovingBitmap bitmap) {
            return pointer.getX() >= bitmap.getX() && pointer.getX() < bitmap.getX() + bitmap.getWidth()
                    && pointer.getY() >= bitmap.getY() && pointer.getY() < bitmap.getY() + bitmap.getHeight();
        }

        @Override
        public boolean contains(Pointer pointer) {
            if (_isSelect)
                return false;
            return contains(pointer, _base) || contains(pointer, _icon);
        }

        @Override
        public void show() {
            _base.show();
            _icon.show();
        }

        @Override
        public void release() {
            super.release();
            _base.release();
            _icon.release();
            _base = null;
            _icon = null;
        }
    }

    private class CharacterSelector implements GameObject, PointerEventHandler {
        final int MAG = 18;

        private List<MovingBitmap> _frames;
        private List<GraySquare> _background;
        private List<CharacterButton> _charBtns;
        private List<CharacterButton> _selectedBtns;
        private Pointer _pointer;
        private boolean _isInGarySquare, _isMoved, _isPress, _isVisible;
        private BitmapButton _confirm, _cancle;
        private MovingBitmap _confirmtext,_canceltext;

        private CharacterSelector(List<Character> chars) {
            _charBtns = new ArrayList<>();
            _frames = new ArrayList<>();
            _background = new ArrayList<>();
            _selectedBtns = new ArrayList<>();
            Collections.sort(chars, new CharacterComparatorID());
            for (Character chara : chars) {
                CharacterButton btn = new CharacterButton(chara);
                btn.addButtonEventHandler(button -> {
                    if (!btn.isGray())
                        _selectChars.add(btn.chara());
                    else
                        _selectChars.remove(btn.chara());
                    btn.setGray(!btn.isGray());
                });
                _charBtns.add(btn);
            }
            for (int i = 0; i < 9; i++)
                _frames.add(new MovingBitmap("interface/white/" + i + ".png"));
            _frames.get(1).resize(_frames.get(1).getWidth() * MAG, _frames.get(1).getHeight());
            _frames.get(7).resize(_frames.get(1).getWidth(), _frames.get(1).getHeight());
            _frames.get(3).resize(_frames.get(3).getWidth(), _frames.get(3).getHeight() * MAG);
            _frames.get(5).resize(_frames.get(3).getWidth(), _frames.get(3).getHeight());
            _frames.get(4).resize(_frames.get(1).getWidth(), _frames.get(3).getHeight());
            _background.add(new GraySquare(_frames.get(1).getWidth(),
                    _frames.get(3).getHeight() - _charBtns.get(0).getHeight() - 25));
            for (int i = 0; i < 5; i++)
                _background.add(new GraySquare(_charBtns.get(0).getWidth(), _charBtns.get(0).getHeight()));
            for (Character chara : chars) {
                CharacterButton btn = new CharacterButton(chara);
                btn.addButtonEventHandler(button -> {
                    for (CharacterButton charBtn : _charBtns)
                        if (charBtn.id() == btn.id() && charBtn.isGray()) {
                            charBtn.notifyButtonEventHandlers();
                            break;
                        }
                });
                _selectedBtns.add(btn);
            }
            _confirm = new BitmapButton("interface/button/blue.png").resize(1.5);
            _confirmtext=new MovingBitmap(R.drawable.text_battlestart).resize(0.25);
            _confirm.addButtonEventHandler(button -> _loading.loading());
            _cancle = new BitmapButton("interface/button/white.png").resize(1.5);
            _canceltext=new MovingBitmap(R.drawable.text_cancle).resize(0.25);
            _cancle.addButtonEventHandler(button -> setVisible(false));
            initLoaction();
            MovingBitmap tmp = AdventurePage.this._background;
            _frames.add(new MovingBitmap("interface/white/4.png")
                    .resize(_frames.get(1).getWidth(),
                            getY() + getHeight() - _frames.get(6).getHeight() - _background.get(0).getY()
                                    - _background.get(0).getHeight())
                    .setLocation(_background.get(0).getX(),
                            _background.get(0).getY() + _background.get(0).getHeight()));
            _frames.add(tmp.crop(0, 1, tmp.getWidth(), getY() - tmp.getY() + 2).setLocation(tmp.getX(), tmp.getY() + 1));
            _frames.get(10).resize(_frames.get(10).getWidth(), _frames.get(10).getHeight() - 2);
            _frames.add(tmp
                    .crop(0, getY() + getHeight() - tmp.getY() - 1, tmp.getWidth(),
                            tmp.getY() + tmp.getHeight() - getY() - getHeight())
                    .setLocation(tmp.getX(), getY() + getHeight() - 2));
            setVisible(false);
        }

        private void initLoaction() {
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
            _background.get(0).setLocation(x, y);
            for (int i = 0; i < 5; i++)
                _background.get(i + 1).setLocation(x + i * (_background.get(i).getWidth() + spacing),
                        y + _background.get(0).getHeight() + spacing);
            _confirm.setLocation(getY() + getWidth() - _frames.get(2).getWidth() - _confirm.getWidth() - spacing,
                    _background.get(5).getY() + (_background.get(5).getWidth() - _cancle.getHeight()) / 2);
            _confirmtext.setLocation(_confirm.getX() + _confirm.getWidth() / 2 - _confirmtext.getWidth() / 2,
                    _confirm.getY() + _confirm.getHeight() / 2 - _confirmtext.getHeight() / 2);
            _cancle.setLocation(_confirm.getX() - _confirm.getWidth() - spacing, _confirm.getY());
            _canceltext.setLocation(_cancle.getX() + _cancle.getWidth() / 2 - _canceltext.getWidth() / 2,
                    _cancle.getY() + _cancle.getHeight() / 2 - _canceltext.getHeight() / 2);
            x += spacing + 30;
            y += spacing;
            for (int i = 0; i < _charBtns.size(); i++) {
                _charBtns.get(i).setLocation(x, y);
                x += _charBtns.get(i).getWidth() + spacing;
                if (i % 8 == 7) {
                    x -= 8 * (_charBtns.get(i).getWidth() + spacing);
                    y += _charBtns.get(i).getHeight() + spacing;
                }
            }
        }

        public void setVisible(boolean isVisible) {
            _isVisible = isVisible;
            for (MovingBitmap bitmap : _frames)
                bitmap.setVisible(isVisible);
            for (GraySquare square : _background)
                square.setVisible(isVisible);
            for (CharacterButton btn : _charBtns)
                btn.setVisible(isVisible);
            if (!isVisible)
                for (CharacterButton btn : _charBtns)
                    btn.setGray(false);
            _confirm.setVisible(isVisible);
            _confirmtext.setVisible(isVisible);
            _cancle.setVisible(isVisible);
            _canceltext.setVisible(isVisible);
            _selectChars.clear();
            _isSelect = isVisible;
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
            _background.get(0).move();
            for (CharacterButton btn : _charBtns)
                btn.move();
            Collections.sort(_selectChars, new CharacterComparatorAttackRange());
            for (CharacterButton btn : _selectedBtns)
                btn.setVisible(false);
            for (int i = 0; i < _selectChars.size(); i++)
                for (CharacterButton btn : _selectedBtns)
                    if (!btn.isVisible() && btn.id() == _selectChars.get(i).id()) {
                        btn.setLocation(_background.get(0).getX() + (4 - i) * (btn.getWidth() + 25),
                                _background.get(0).getY() + _background.get(0).getHeight() + 25);
                        btn.setVisible(true);
                        break;
                    }
            for (CharacterButton btn : _selectedBtns)
                btn.move();
        }

        @Override
        public void show() {
            _frames.get(4).show();
            _background.get(0).show();
            for (CharacterButton btn : _charBtns)
                btn.show();
            for (int i = 9; i < 12; i++)
                _frames.get(i).show();
            for (int i = 0; i < 5; i++)
                _background.get(i + 1).show();
            for (int i = 0; i < 9; i++)
                if (i != 4)
                    _frames.get(i).show();
            for (CharacterButton btn : _selectedBtns)
                btn.show();
            _confirm.show();
            _cancle.show();
            _confirmtext.show();
            _canceltext.show();
        }

        @Override
        public boolean pointerPressed(Pointer actionPointer, List<Pointer> pointers) {
            if (pointers.size() == 1 && _isVisible) {
                _pointer = actionPointer;
                _isPress = contains(_pointer, getX(), getY(), getWidth(), getHeight());
                _isInGarySquare = contains(_pointer, _background.get(0).getX(), _background.get(0).getY(),
                        _background.get(0).getWidth(), _background.get(0).getHeight());
                _isMoved = false;
                return true;
            }
            return false;
        }

        private double distance(Pointer p1, Pointer p2) {
            return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
        }

        @Override
        public boolean pointerMoved(Pointer actionPointer, List<Pointer> pointers) {
            if (pointers.size() == 1 && _isInGarySquare && _isVisible) {
                _isMoved = distance(_pointer, actionPointer) > 1;
                int y = actionPointer.getY() - _pointer.getY();
                for (CharacterButton btn : _charBtns)
                    btn.setLocation(btn.getX(), btn.getY() + y);
                _pointer = actionPointer;
            }
            return false;
        }

        @Override
        public boolean pointerReleased(Pointer actionPointer, List<Pointer> pointers) {
            if (pointers.size() == 1 && _isPress && _isVisible) {
                if (_isInGarySquare) {
                    if (_charBtns.get(0).getY() > _background.get(0).getY() + 25 || _charBtns.size() <= 16) {
                        int y = _background.get(0).getY() + 25 - _charBtns.get(0).getY();
                        for (CharacterButton btn : _charBtns)
                            btn.setLocation(btn.getX(), btn.getY() + y);
                    } else if (_charBtns.get(_charBtns.size() - 1).getY() + _charBtns.get(_charBtns.size() - 1).getHeight()
                            < _background.get(0).getY() + _background.get(0).getHeight() - 25 && _charBtns.size() > 16) {
                        int y = _background.get(0).getY() + _background.get(0).getHeight() - 25
                                - _charBtns.get(_charBtns.size() - 1).getY()
                                - _charBtns.get(_charBtns.size() - 1).getHeight();
                        for (CharacterButton btn : _charBtns)
                            btn.setLocation(btn.getX(), btn.getY() + y);
                    }
                    if (!_isMoved && contains(actionPointer, _background.get(0).getX(), _background.get(0).getY(),
                            _background.get(0).getWidth(), _background.get(0).getHeight()))
                        for (CharacterButton btn : _charBtns)
                            if (btn.contains(_pointer) && (_selectChars.size() != 5 || btn.isGray()))
                                btn.notifyButtonEventHandlers();
                } else {
                    for (CharacterButton btn : _selectedBtns)
                        if (btn.contains(_pointer) && btn.contains(actionPointer))
                            btn.notifyButtonEventHandlersWithoutSound();
                    if (_confirm.contains(_pointer) && _confirm.contains(actionPointer) && _selectChars.size() != 0)
                        _confirm.notifyButtonEventHandlers();
                    else if (_cancle.contains(_pointer) && _cancle.contains(actionPointer))
                        _cancle.notifyButtonEventHandlers();
                }
                return true;
            }
            return false;
        }

        @Override
        public void release() {
            for (MovingBitmap bitmap : _frames)
                bitmap.release();
            for (GraySquare gray : _background)
                gray.release();
            for (CharacterButton btn : _charBtns)
                btn.release();
            for (CharacterButton btn : _selectedBtns)
                btn.release();
            _confirm.release();
            _confirmtext.release();
            _cancle.release();
            _canceltext.release();
            _frames.clear();
            _background.clear();
            _charBtns.clear();
            _selectedBtns.clear();
            _frames = null;
            _background = null;
            _charBtns = null;
            _selectedBtns = null;
            _confirm = null;
            _cancle = null;
            _confirmtext=null;
            _canceltext=null;
        }
    }

    public static class GraySquare implements GameObject {
        private List<MovingBitmap> _bitmaps;

        public GraySquare(int width, int height) {
            _bitmaps = new ArrayList<>();
            for (int i = 0; i < 9; i++)
                _bitmaps.add(new MovingBitmap("interface/gray/" + i + ".png"));
            _bitmaps.get(1).resize(width - 2 * _bitmaps.get(0).getWidth(), _bitmaps.get(1).getHeight());
            _bitmaps.get(7).resize(_bitmaps.get(1).getWidth(), _bitmaps.get(1).getHeight());
            _bitmaps.get(3).resize(_bitmaps.get(3).getWidth(), height - 2 * _bitmaps.get(0).getHeight());
            _bitmaps.get(5).resize(_bitmaps.get(3).getWidth(), _bitmaps.get(3).getHeight());
            _bitmaps.get(4).resize(_bitmaps.get(1).getWidth(), _bitmaps.get(3).getHeight());
        }

        public void setVisible(boolean isVisible) {
            for (MovingBitmap bitmap : _bitmaps)
                bitmap.setVisible(isVisible);
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

    private class Loading extends Animation {
        public Loading() {
            for (int i = 0; i <= 4; i++)
                addFrame(new MovingBitmap("loading/" + i + ".png").resize(1920, 1080));
            setLocation((Game.GAME_FRAME_WIDTH - getWidth()) / 2, (Game.GAME_FRAME_HEIGHT - getHeight()) / 2);
        }

        public void loading() {
            removeAllPointerEventHandler();
            setVisible(true);
        }

        @Override
        public void move() {
            if (getCurrentFrameIndex() != getFrameCount() - 1)
                super.move();
            else
                changeState(Game.ADV_STATE, _data);
        }
    }

    public static class CharacterComparatorAttackRange implements Comparator<Character> {
        @Override
        public int compare(Character char1, Character char2) {
            return char1.attackRange() - char2.attackRange();
        }
    }

    public static class CharacterComparatorID implements Comparator<Character> {
        @Override
        public int compare(Character char1, Character char2) {
            return -(char1.id() - char2.id());
        }
    }
}
