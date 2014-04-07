package com.stuckinadrawer.dungeongame;

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


    boolean playerMenuOpen = false;
    boolean inventoryOpen = false;

    Table playerMenu;
    Label s;
    Label p;
    Label e;
    Label c;
    Label i;
    Label a;
    Label l;

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
        createPlayerMenu();
        createInventory();
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

    private void createPlayerMenu(){
        playerMenu = new Table(skin);
        int pad = 10;
        playerMenu.add(new Label("You're SPECIAL!", skin, "big")).pad(20);
        playerMenu.row();

        s = new Label(player.getStrength()+"", skin);
        playerMenu.add("Strength: ");
        playerMenu.add(s).pad(pad);
        playerMenu.row();

        p = new Label(player.getPerception()+"", skin);
        playerMenu.add("Perception: ");
        playerMenu.add(p).pad(pad);
        playerMenu.row();

        e = new Label(player.getEndurance()+"", skin);
        playerMenu.add("Endurance: ");
        playerMenu.add(e).pad(pad);
        playerMenu.row();

        c = new Label(player.getCharisma()+"", skin);
        playerMenu.add("Charisma: ");
        playerMenu.add(c).pad(pad);
        playerMenu.row();

        i = new Label(player.getIntelligence()+"", skin);
        playerMenu.add("Intelligence: ");
        playerMenu.add(i).pad(pad);
        playerMenu.row();

        a = new Label(player.getAgility()+"", skin);
        playerMenu.add("Agility: ");
        playerMenu.add(a).pad(pad);
        playerMenu.row();

        l = new Label(player.getLuck()+"", skin);
        playerMenu.add("Luck: ");
        playerMenu.add(l).pad(pad);
        playerMenu.row();


        playerMenu.setBackground(skin.getDrawable("textfield"));

        playerMenu.pack();
        playerMenu.setPosition(Gdx.graphics.getWidth()/2-playerMenu.getWidth()/2, Gdx.graphics.getHeight()/2-playerMenu.getHeight()/2);

    }

    public void createInventoryButton(){
        final TextButton invButton = new TextButton("Inventory", skin);
        invButton.setSize(120, 50);
        invButton.setPosition(Gdx.graphics.getWidth()-130, 70);
        stage.addActor(invButton);
        invButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(inventoryOpen){
                    inventory.remove();
                }else{
                    stage.addActor(inventory);
                }
                inventoryOpen = !inventoryOpen;
            }
        });
    }

    public void createInventory(){
        inventory = new Table(skin);
        int pad = 5;

        Weapon equippedWeapon = player.getEquippedWeapon();

        //ImageButton b = new ImageButton(skin, "slot-red");
       // ImageButton.ImageButtonStyle style = b.getStyle();
        //style.imageUp = new TextureRegionDrawable(game.getRenderer().getSprite(equippedWeapon.getSpriteName()));

        Stack stack = new Stack();
        Image slot = new Image(skin, "inv-highlight");
        stack.add(slot);
        Image item = new Image(new TextureRegionDrawable(game.getRenderer().getSprite(equippedWeapon.getSpriteName())));
        stack.add(item);
        inventory.add(stack).pad(pad).width(32).height(32);
        inventory.row();

        ArrayList<Item> inventoryList = player.getInventory();
        System.out.println("inv size: "+inventoryList.size());
        int index = 0;
        for(int x = 0; x < 4; x++){
            for(int y = 0; y < 3; y++){
                stack = new Stack();
                slot = new Image(skin, "inv");
                stack.add(slot);
                    if(index < inventoryList.size()){
                        String spriteName = inventoryList.get(index).getSpriteName();
                        item = new Image(new TextureRegionDrawable(game.getRenderer().getSprite(spriteName)));
                        item.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                Dialog d = new Dialog("Item", skin);
                                d.text("Hello! I'm a dialog!");
                                d.button("Close");
                                d.show(stage);
                            }

                        });
                        stack.add(item);
                    }
                index++;
                inventory.add(stack).pad(pad).width(32).height(32);
            }
            inventory.row();
        }

        inventory.setBackground(skin.getDrawable("textfield"));

        inventory.pack();
        inventory.setPosition(Gdx.graphics.getWidth()/2-inventory.getWidth()/2, Gdx.graphics.getHeight()/2-inventory.getHeight()/2);

    }


    public void updateHUD() {
        healthbar.setRange(0, player.maxHP);
        healthbar.setValue(player.currentHP);
        XPBar.setRange(0, player.XPToNextLevel);
        XPBar.setValue(player.currentXP);
    }
}
