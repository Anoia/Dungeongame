package com.stuckinadrawer.dungeongame.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.stuckinadrawer.dungeongame.actors.Player;
import com.stuckinadrawer.dungeongame.items.Item;
import com.stuckinadrawer.dungeongame.items.Weapon;
import com.stuckinadrawer.dungeongame.render.Renderer;

import java.util.ArrayList;

public class Inventory extends Window {


    private final Skin skin;
    private final Stage stage;
    private final Player player;
    private final Renderer renderer;

    public Inventory(Skin skin, Stage stage, Player player, Renderer renderer) {
        super("Inventory", skin);
        this.skin = skin;
        this.stage = stage;
        this.player = player;
        this.renderer = renderer;
        init();
    }

    private void init() {
        int pad = 5;

        Weapon equippedWeapon = player.getEquippedWeapon();

        //ImageButton b = new ImageButton(skin, "slot-red");
        // ImageButton.ImageButtonStyle style = b.getStyle();
        //style.imageUp = new TextureRegionDrawable(game.getRenderer().getSprite(equippedWeapon.getSpriteName()));

        Stack stack = new Stack();
        Image slot = new Image(skin, "inv-highlight");
        stack.add(slot);
        Image item = new Image(new TextureRegionDrawable(renderer.getSprite(equippedWeapon.getSpriteName())));
        stack.add(item);
        add(stack).pad(pad).width(32).height(32);
        row();

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
                    item = new Image(new TextureRegionDrawable(renderer.getSprite(spriteName)));
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
                add(stack).pad(pad).width(32).height(32);
            }
            row();
        }


        pack();
        setMovable(false);
        setModal(true);
        setPosition(Gdx.graphics.getWidth() / 2 - getWidth() / 2, Gdx.graphics.getHeight() / 2 - getHeight() / 2);

        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                remove();
            }
        });
    }

}
