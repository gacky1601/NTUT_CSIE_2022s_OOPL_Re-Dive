package princess.connect.extend;

import princeconnect.game.R;
import princess.connect.baseClass.Character;
import princess.connect.core.MovingBitmap;

public class CharacterButton extends BitmapButton{
    protected Character _chara;
    private MovingBitmap _icon, _gray, _frame;
    private int _x, _y;
    private boolean _isGray, _isVisible;

    public CharacterButton(Character chara) {
        this(chara, chara.rank());
    }

    public CharacterButton(Character chara, int rank) {
        super(R.drawable.none);
        _chara = chara;
        _icon = new MovingBitmap("character/icon_unit_" + _chara.id() + "11.png");
        _icon.resize(_icon.getWidth() - 2, _icon.getHeight() - 2);
        _gray = new MovingBitmap("character/icon_unit_gray.png");
        _gray.resize(_icon.getWidth(), _icon.getHeight());
        _gray.setVisible(false);
        if (rank <= 1)
            _frame = new MovingBitmap("frame/blueSq.png");
        else if (rank <= 3)
            _frame = new MovingBitmap("frame/brownSq.png");
        else if (rank <= 6)
            _frame = new MovingBitmap("frame/greySq.png");
        else if (rank <= 10)
            _frame = new MovingBitmap("frame/yellowSq.png");
        setVisible(true);
    }

    public CharacterButton(Character chara, int x, int y) {
        this(chara);
        setLocation(x, y);
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

    @Override
    public int getX() {
        return _x;
    }

    @Override
    public int getY() {
        return _y;
    }

    @Override
    public int getWidth() {
        return _frame.getWidth();
    }

    @Override
    public int getHeight() {
        return _frame.getHeight();
    }

    @Override
    public boolean contains(int x, int y) {
        return x >= getX() && x < (getX() + getWidth()) && y >= getY()
                && y < (getY() + getHeight());
    }

    @Override
    public void setVisible(boolean isVisible) {
        _isVisible = isVisible;
        _icon.setVisible(_isVisible);
        _gray.setVisible(_isVisible && _isGray);
        _frame.setVisible(_isVisible);
    }

    @Override
    public void setLocation(int x, int y) {
        _x = x;
        _y = y;
    }

    @Override
    public void move() {
        _icon.setLocation(_x + 1, _y + 1);
        _gray.setLocation(_icon.getX(), _icon.getY());
        _frame.setLocation(_x, _y);
    }

    @Override
    public void show() {
        _icon.show();
        _gray.show();
        _frame.show();
    }

    @Override
    public void release() {
        _icon.release();
        _gray.release();
        _frame.release();
        _icon = null;
        _gray = null;
        _frame = null;
    }
}
