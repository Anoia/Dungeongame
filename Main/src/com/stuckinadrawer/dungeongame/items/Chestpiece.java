package com.stuckinadrawer.dungeongame.items;

import com.stuckinadrawer.dungeongame.actors.Player;

public class Chestpiece extends Armor{
    public Chestpiece(String name) {
        super(name);
        setSpriteName("item_chest");
    }

    @Override
    public String getDescription() {
        return "Chestpiece";
    }

    @Override
    public void equip(Player player) {
        if(player.getEquippedChestpiece() != null){
            player.addToInventory(player.getEquippedChestpiece());
        }
        player.setEquippedChestpiece(this);
        player.removeFromInventory(this);
    }

    @Override
    public void unEquip(Player player) {
        player.addToInventory(this);
        player.setEquippedChestpiece(null);
    }
}
