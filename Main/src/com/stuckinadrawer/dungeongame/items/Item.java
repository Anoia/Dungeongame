package com.stuckinadrawer.dungeongame.items;

public abstract class Item {
    private String name;
    private String spriteName;

    protected Item(String name) {
        this.name = name;
    }


    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }


    public String getSpriteName() {
        return spriteName;
    }

    public void setSpriteName(String spriteName) {
        this.spriteName = spriteName;
    }
}
