package com.stuckinadrawer.dungeongame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.stuckinadrawer.dungeongame.actors.Player;
import com.stuckinadrawer.dungeongame.screen.GameScreen;

public class HUD {
    private Stage stage;
    private GameScreen game;
    private Skin skin;
    private Player player;


    boolean playerMenuOpen = false;

    Table playerMenu;
    Label s;
    Label p;
    Label e;
    Label c;
    Label i;
    Label a;
    Label l;


    public HUD(GameScreen game){
        this.game =  game;
        this.stage = game.getStage();
        this.skin = game.getSkin();
        player = game.getPlayer();

        init();
    }

    private void init(){
        createCheatButton();
        createPlayerButton();
        createHealthbar();
        createXPBar();
        createPlayerMenu();
    }

    private void createCheatButton(){
        // TESTBUTTON
        final TextButton button = new TextButton("Heal Me!", skin);
        button.setPosition(10, 10);
        button.setSize(100, 25);

        stage.addActor(button);

        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                player.currentHP = player.maxHP;
                player.healthbar.setValue(player.currentHP);
            }
        });
    }

    private void createPlayerButton(){
        final TextButton playerButton = new TextButton("Player Info", skin);
        playerButton.setSize(100, 25);
        playerButton.setPosition(Gdx.graphics.getWidth()-110, 25);
        stage.addActor(playerButton);
        playerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(playerMenuOpen){
                    playerMenu.remove();

                }else{
                    s.setText(player.getStrength()+"");
                    p.setText(player.getPerception()+"");
                    e.setText(player.getEndurance()+"");
                    c.setText(player.getCharisma()+"");
                    i.setText(player.getIntelligence()+"");
                    a.setText(player.getAgility()+"");
                    l.setText(player.getLuck()+"");



                    stage.addActor(playerMenu);
                    // playerMenu.setWidth(400);
                    // playerMenu.setHeight(400);
                    System.out.println("TABLESIZE!"+playerMenu.getWidth() + " " + playerMenu.getHeight());

                }
                playerMenuOpen = !playerMenuOpen;
            }
        });
    }

    private void createHealthbar(){
        //HEALTHBAR
        Slider healthbar = new Slider(0, player.maxHP, 1, false, skin, "healthbar");
        healthbar.setSize(Gdx.graphics.getWidth()/3, 50);
        healthbar.setPosition(5, Gdx.graphics.getHeight() - 55);
        healthbar.setValue(player.maxHP);
        healthbar.setAnimateDuration(.5f);
        healthbar.setTouchable(Touchable.disabled);

        stage.addActor(healthbar);

        player.healthbar = healthbar;
    }

    private void createXPBar(){
        //XP BAR
        Slider XPBar = new Slider(0, player.XPToNextLevel, 1, false, skin, "XPBar");
        XPBar.setSize(Gdx.graphics.getWidth()-10, 50);
        XPBar.setPosition(5, Gdx.graphics.getHeight()-30);
        XPBar.setValue(player.currentXP);
        XPBar.setAnimateDuration(.5f);
        XPBar.setTouchable(Touchable.disabled);
        stage.addActor(XPBar);
        player.XPBar = XPBar;
    }

    private void createPlayerMenu(){
        playerMenu = new Table(skin);

        playerMenu.add("You're SPECIAL!");
        playerMenu.row();

        s = new Label(player.getStrength()+"", skin);
        playerMenu.add("Strength: ");
        playerMenu.add(s);
        playerMenu.row();

        p = new Label(player.getPerception()+"", skin);
        playerMenu.add("Perception: ");
        playerMenu.add(p);
        playerMenu.row();

        e = new Label(player.getEndurance()+"", skin);
        playerMenu.add("Endurance: ");
        playerMenu.add(e);
        playerMenu.row();

        c = new Label(player.getCharisma()+"", skin);
        playerMenu.add("Charisma: ");
        playerMenu.add(c);
        playerMenu.row();

        i = new Label(player.getIntelligence()+"", skin);
        playerMenu.add("Intelligence: ");
        playerMenu.add(i);
        playerMenu.row();

        a = new Label(player.getAgility()+"", skin);
        playerMenu.add("Agility: ");
        playerMenu.add(a);
        playerMenu.row();

        l = new Label(player.getLuck()+"", skin);
        playerMenu.add("Luck: ");
        playerMenu.add(l);
        playerMenu.row();


        //playerMenu.setFillParent(true);
        //stage.addActor(playerMenu);


        // playerMenu.setBackground(new TiledDrawable(skin.getRegion("default-round")));
        playerMenu.setBackground(skin.getDrawable("textfield"));

        System.out.println("TABLESIZE!"+playerMenu.getWidth() + " " + playerMenu.getHeight());
        //playerMenu.debug();
        playerMenu.pack();
        playerMenu.setPosition(Gdx.graphics.getWidth()/2-playerMenu.getWidth()/2, Gdx.graphics.getHeight()/2-playerMenu.getHeight()/2);

    }


}
