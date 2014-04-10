package com.stuckinadrawer.dungeongame.items;

import com.stuckinadrawer.dungeongame.actors.Player;

public interface Equipable {
    public abstract void equip(Player player);
    public abstract void unEquip(Player player);
}
