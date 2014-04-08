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
        Slot slot = new Slot("inv-highlight");
        slot.setItem(equippedWeapon);
        add(slot).pad(pad).width(32).height(32);
        row();

        ArrayList<Item> inventoryList = player.getInventory();
        System.out.println("inv size: "+inventoryList.size());
        int index = 0;
        for(int x = 0; x < 4; x++){
            for(int y = 0; y < 3; y++){
                slot = new Slot();
                if(index < inventoryList.size()){
                    slot.setItem(inventoryList.get(index));
                }
                index++;
                add(slot).pad(pad).width(32).height(32);
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
                if(x < 0 || y < 0 || x > getWidth() || y > getHeight()){
                    remove();
                }
            }
        });
    }

    private class Slot extends Stack{
        private Item item = null;

        public Slot(){
            add(new Image(skin, "inv"));
        }

        public Slot(String type){
            add(new Image(skin, type));
        }

        public void setItem(Item i){
            this.item = i;
            Image image = new Image(new TextureRegionDrawable(renderer.getSprite(item.getSpriteName())));
            image.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Dialog d = new Dialog(item.getName(), skin);
                    d.text(item.getDescription());

                    d.button("Close");
                    d.show(stage);
                }

            });
            add(image);
        }

    }
}
