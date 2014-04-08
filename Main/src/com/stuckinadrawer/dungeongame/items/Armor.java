package com.stuckinadrawer.dungeongame.items;

public class Armor extends Item {
    private int defense;
    private int slot;

    public Armor(String name) {
        super(name);
    }

    @Override
    public String getDescription() {
        return "Armor";
    }
}
