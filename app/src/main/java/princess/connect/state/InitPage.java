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
import princess.connect.area.Area1;
import princess.connect.baseClass.Area;
import princess.connect.baseClass.Character;
import princess.connect.character.Akari;
import princess.connect.character.Akino;
import princess.connect.character.Anna;
import princess.connect.character.Aoi;
import princess.connect.character.Arisa;
import princess.connect.character.Chika;
import princess.connect.character.Djeeta;
import princess.connect.character.Eriko;
import princess.connect.character.Hatsune;
import princess.connect.character.Hiyori;
import princess.connect.character.Io;
import princess.connect.character.Kaori;
import princess.connect.character.Kokoro;
import princess.connect.character.Kurumi;
import princess.connect.character.Kuuka;
import princess.connect.character.Kyaru;
import princess.connect.character.Mahiru;
import princess.connect.character.Maho;
import princess.connect.character.Makoto;
import princess.connect.character.Mifuyu;
import princess.connect.character.Mimi;
import princess.connect.character.Misaki;
import princess.connect.character.Misogi;
import princess.connect.character.Mitsuki;
import princess.connect.character.Miyako;
import princess.connect.character.Monika;
import princess.connect.character.Ninon;
import princess.connect.character.Nozomi;
import princess.connect.character.Pecorine;
import princess.connect.character.Rei;
import princess.connect.character.Rima;
import princess.connect.character.Rino;
import princess.connect.character.Saren;
import princess.connect.character.Shinobu;
import princess.connect.character.Shiori;
import princess.connect.character.Shizuru;
import princess.connect.character.Suzume;
import princess.connect.character.Suzuna;
import princess.connect.character.Tamaki;
import princess.connect.character.Yori;
import princess.connect.character.Yui;
import princess.connect.character.Yukari;
import princess.connect.character.Yuki;
import princess.connect.core.MovingBitmap;
import princess.connect.engine.GameEngine;
import princess.connect.extend.BitmapButton;
import princess.connect.extend.ButtonEventHandler;

public class InitPage extends AbstractGameState{
    public static final String ALL_CHARACTER_LIST = "ALL_CHARACTER_LIST";
    public static final String ALL_AREA_LIST = "ALL_AREA_LIST";
    public static final String PLAYER_CHARACTER_LIST = "PLAYER_CHARACTER_LIST";

    public InitPage(GameEngine engine) {
        super(engine);
    }
    private MovingBitmap _background,_icon;
    private BitmapButton _startButton;


    @Override
    public void initialize(Map<String, Object> data) {
        _background = new MovingBitmap(R.drawable.background);
        _background.setLocation((Game.GAME_FRAME_WIDTH - _background.getWidth()) / 2, (Game.GAME_FRAME_HEIGHT - _background.getHeight()) / 2);
        _startButton = new BitmapButton(R.drawable.start, R.drawable.start_pressed, 0, 0);
        _icon=new MovingBitmap(R.drawable.icon_init,430,500);
        _icon.resize((int)(_icon.getWidth()*1.3),(int)(_icon.getHeight()*1.3));
        addGameObject(_background);
        addGameObject(_icon);
        addGameObject(_startButton);

        data = new HashMap<>();
        List<Character> allCharsList = Arrays.asList(new Hiyori(), new Rei(), new Yui(), new Misogi(), new Akari(), new Miyako(), new Yuki(), new Anna(), new Maho(), new Rino(), new Hatsune(), new Suzuna(), new Kaori(), new Io(), new Mimi(), new Kurumi(), new Yori(), new Suzume(), new Eriko(), new Saren(), new Nozomi(), new Ninon(), new Shinobu(), new Akino(), new Mahiru(), new Yukari(), new Shiori(), new Aoi(), new Chika(), new Makoto(), new Kuuka(), new Tamaki(), new Mifuyu(), new Shizuru(), new Misaki(), new Mitsuki(), new Rima(), new Monika(), new Djeeta(), new Pecorine(), new Kokoro(), new Kyaru(), new Arisa());
        List<Area> allAreasList = Arrays.asList(new Area1());
        List<Character> playerCharsList = Arrays.asList(new Pecorine(), new Kokoro(), new Kyaru(), new Yui());
        data.put(ALL_CHARACTER_LIST, allCharsList);
        data.put(ALL_AREA_LIST, allAreasList);
        data.put(PLAYER_CHARACTER_LIST, playerCharsList);
        Map<String, Object> finalData = data;

        _startButton.addButtonEventHandler(button -> changeState(Game.PLAYER_MENU, finalData));
        addPointerEventHandler(_startButton);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }
}

