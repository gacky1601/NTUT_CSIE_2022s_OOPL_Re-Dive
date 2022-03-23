package prince.connect.character;

public abstract class Skill {
    protected double _castTime;
    protected int _level = 0;

    protected abstract void cast();
}
