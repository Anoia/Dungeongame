package com.stuckinadrawer.dungeongame.items;

public abstract class Item {
    private String name;

    protected Item(String name) {
        this.name = name;
    }


    public String getName(){
        return name;
    }
}
