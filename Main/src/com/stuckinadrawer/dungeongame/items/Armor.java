package com.stuckinadrawer.dungeongame.items;

public abstract class Armor extends Item implements Equipable{
    private int defense;
    private int slot;

    public Armor(String name) {
        super(name);
    }
}
