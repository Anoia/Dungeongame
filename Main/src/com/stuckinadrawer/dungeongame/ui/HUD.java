package com.stuckinadrawer.dungeongame.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.stuckinadrawer.dungeongame.actors.Player;
import com.stuckinadrawer.dungeongame.items.Item;
import com.stuckinadrawer.dungeongame.items.Weapon;
import com.stuckinadrawer.dungeongame.screen.GameScreen;

import java.util.ArrayList;

public class HUD {
    private Stage stage;
    private GameScreen game;
    private Skin skin;
    private Player player;
    boolean inventoryOpen = false;

    Table inventory;

    Slider healthbar;
    Slider XPBar;


    public HUD(GameScreen game){
        this.game =  game;
        this.stage = game.getStage();
        this.skin = game.getSkin();
        this.player = game.getPlayer();

        init();
    }

    private void init(){
        createCheatButton();
        createPlayerButton();
        createInventoryButton();
        createHealthbar();
        createXPBar();
    }

    private void createCheatButton(){
        // TESTBUTTON
        final TextButton button = new TextButton("Heal Me!", skin);
        button.setPosition(10, 10);
        button.setSize(120, 50);

        stage.addActor(button);

        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                player.currentHP = player.maxHP;
                updateHUD();
            }
        });
    }

    private void createPlayerButton(){
        final TextButton playerButton = new TextButton("Player Info", skin);
        playerButton.setSize(120, 50);
        playerButton.setPosition(Gdx.graphics.getWidth()-130, 10);
        stage.addActor(playerButton);
        playerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.addActor(new CharacterScreen(skin, player));
            }
        });
    }

    private void createHealthbar(){
        //HEALTHBAR
        healthbar = new Slider(0, player.maxHP, 1, false, skin, "healthbar");
        healthbar.setSize(Gdx.graphics.getWidth()/3, 50);
        healthbar.setPosition(5, Gdx.graphics.getHeight() - 55);
        healthbar.setValue(player.maxHP);
        healthbar.setAnimateDuration(.5f);
        healthbar.setTouchable(Touchable.disabled);

        stage.addActor(healthbar);
    }

    private void createXPBar(){
        //XP BAR
        XPBar = new Slider(0, player.XPToNextLevel, 1, false, skin, "XPBar");
        XPBar.setSize(Gdx.graphics.getWidth()-10, 50);
        XPBar.setPosition(5, Gdx.graphics.getHeight()-30);
        XPBar.setValue(player.currentXP);
        XPBar.setAnimateDuration(.5f);
        XPBar.setTouchable(Touchable.disabled);
        stage.addActor(XPBar);
    }



    public void createInventoryButton(){
        final TextButton invButton = new TextButton("Inventory", skin);
        invButton.setSize(120, 50);
        invButton.setPosition(Gdx.graphics.getWidth()-130, 70);
        stage.addActor(invButton);
        invButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.addActor(new Inventory(skin, stage, player, game.getRenderer()));
            }
        });
    }



    public void updateHUD() {
        healthbar.setRange(0, player.maxHP);
        healthbar.setValue(player.currentHP);
        XPBar.setRange(0, player.XPToNextLevel);
        XPBar.setValue(player.currentXP);
    }
}
