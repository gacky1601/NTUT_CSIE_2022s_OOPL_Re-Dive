package princess.connect.state;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import princess.connect.Game;
import princeconnect.game.R;
import princess.connect.baseClass.Character;
import princess.connect.character.Arisa;
import princess.connect.character.Kokoro;
import princess.connect.character.Kyaru;
import princess.connect.character.Pecorine;
import princess.connect.core.MovingBitmap;
import princess.connect.engine.GameEngine;
import princess.connect.extend.BitmapButton;
import princess.connect.extend.ButtonEventHandler;

public class InitPage extends AbstractGameState{

    public InitPage(GameEngine engine) {
        super(engine);
    }
    private MovingBitmap _background,_icon;
    private BitmapButton _startButton;


    @Override
    public void initialize(Map<String, Object> data) {
        _background = new MovingBitmap(R.drawable.background);
        _background.resize((int) (_background.getWidth() * 0.85), (int) (_background.getHeight() * 0.85));
        _background.setLocation(-300,-300);
        _startButton = new BitmapButton(R.drawable.start, R.drawable.start_pressed, 0, 0);
        _icon=new MovingBitmap(R.drawable.icon_init,430,500);
        _icon.resize((int)(_icon.getWidth()*1.3),(int)(_icon.getHeight()*1.3));
        addGameObject(_background);
        addGameObject(_icon);
        addGameObject(_startButton);
        data = new HashMap<>();
        List<Character> charsList = Arrays.asList(new Pecorine(), new Kokoro(), new Arisa(), new Kyaru(), new Pecorine(),
                new Kokoro(), new Arisa(), new Kyaru(), new Pecorine(), new Kokoro(), new Arisa(), new Kyaru(),
                new Pecorine(), new Kokoro(), new Arisa(), new Kyaru(), new Pecorine(), new Kokoro(), new Arisa(),
                new Kyaru());
        Collections.sort(charsList, new CharacterComparator());
        data.put("charsList", charsList);
        Map<String, Object> finalData = data;
        _startButton.addButtonEventHandler(button -> changeState(Game.PLAYER_MENU, finalData));
        addPointerEventHandler(_startButton);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    private static class CharacterComparator implements Comparator<Character> {
        @Override
        public int compare(Character char1, Character char2) {
            return -(char1.id() - char2.id());
        }
    }
}

