package com.stuckinadrawer.dungeongame.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.stuckinadrawer.dungeongame.actors.Player;

public class CharacterScreen extends Window {

    public CharacterScreen(Skin skin, Player player) {
        super("CharacterName", skin);
        int pad = 10;
        add(new Label("You're SPECIAL!", skin, "big")).pad(20);
        row();

        Label s = new Label(player.getStrength()+"", skin);
        add("Strength: ");
        add(s).pad(pad);
        row();

        Label p = new Label(player.getPerception()+"", skin);
        add("Perception: ");
        add(p).pad(pad);
        row();

        Label e = new Label(player.getEndurance()+"", skin);
        add("Endurance: ");
        add(e).pad(pad);
        row();

        Label c = new Label(player.getCharisma()+"", skin);
        add("Charisma: ");
        add(c).pad(pad);
        row();

        Label i = new Label(player.getIntelligence()+"", skin);
        add("Intelligence: ");
        add(i).pad(pad);
        row();

        Label a = new Label(player.getAgility()+"", skin);
        add("Agility: ");
        add(a).pad(pad);
        row();

        Label l = new Label(player.getLuck()+"", skin);
        add("Luck: ");
        add(l).pad(pad);
        row();

        pack();
        setModal(true);
        setMovable(false);
        setPosition(Gdx.graphics.getWidth()/2-getWidth()/2, Gdx.graphics.getHeight()/2-getHeight()/2);

        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(x < 0 || y < 0 || x > getWidth() || y > getHeight()){
                    remove();
                }
            }
        });

    }

}
