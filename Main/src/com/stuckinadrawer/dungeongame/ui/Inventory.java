package com.stuckinadrawer.dungeongame.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.stuckinadrawer.dungeongame.actors.Player;
import com.stuckinadrawer.dungeongame.items.*;
import com.stuckinadrawer.dungeongame.render.Renderer;

import java.util.ArrayList;

public class Inventory extends Window {


    private final Skin skin;
    private final Stage stage;
    private final Player player;
    private final Renderer renderer;

    private Inventory theInventory = this;

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

        // Weapon
        Weapon equippedWeapon = player.getEquippedWeapon();
        Slot slot = new Slot("inv-highlight");
        if(equippedWeapon!=null){
            slot.setItem(equippedWeapon);
        }
        add(slot).pad(pad).width(32).height(32);


        // Ring
        Ring eqippedRing = player.getEquippedRing();
        slot = new Slot("inv-highlight");
        if(eqippedRing!=null){
            slot.setItem(eqippedRing);
        }
        add(slot).pad(pad).width(32).height(32);


        // Chest
        Chestpiece equippedChestpiece = player.getEquippedChestpiece();
        slot = new Slot("inv-highlight");
        if(equippedChestpiece!=null){
            slot.setItem(equippedChestpiece);
        }
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
        private boolean inInventory = true;

        public Slot(){
            add(new Image(skin, "inv"));
        }

        public Slot(String type){
            add(new Image(skin, type));
            if(type.equals("inv-highlight")) inInventory = false;
        }

        public void setItem(Item i){
            this.item = i;
            Image image = new Image(new TextureRegionDrawable(renderer.getSprite(item.getSpriteName())));
            image.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    InventoryDialog d = new InventoryDialog(item.getName(), skin);
                    d.text(item.getDescription());

                    if (item instanceof Equipable){
                        if(inInventory){
                            d.button("Equip", item);
                        }else{
                            d.button("Unequip", item);
                            d.unequip = true;
                        }

                    }
                    d.button("Close");
                    d.show(stage);
                }

            });
            add(image);
        }

    }

    private class InventoryDialog extends Dialog{

        private boolean unequip = false;

        public InventoryDialog(String title, Skin skin) {
            super(title, skin);
        }

        @Override
        public void result(Object object){
            if(object instanceof Equipable){
                Equipable i = (Equipable) object;
                if(unequip){
                    i.unEquip(player);
                    theInventory.clear();
                    theInventory.init();
                }else{
                    i.equip(player);
                    theInventory.clear();
                    theInventory.init();
                }
            }
        }
    }
}
