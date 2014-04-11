package com.stuckinadrawer.dungeongame.items;

import com.stuckinadrawer.dungeongame.actors.Player;

public class Ring extends Item implements Equipable {
    protected Ring(String name) {
        super(name);
        setSpriteName("item_ring");
    }

    @Override
    public void equip(Player player) {
        if(player.getEquippedRing() != null){
            player.addToInventory(player.getEquippedRing());
        }
        player.setEquippedRing(this);
        player.removeFromInventory(this);
    }

    @Override
    public void unEquip(Player player) {
        player.addToInventory(this);
        player.setEquippedRing(null);
    }

    @Override
    public String getDescription() {
        return "Ring";
    }
}
