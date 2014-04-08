package com.stuckinadrawer.dungeongame.tiles;

import com.stuckinadrawer.dungeongame.items.Item;
import com.stuckinadrawer.dungeongame.util.Position;

public abstract class Tile {
    int x;
    int y;
    String spriteName;
    boolean solid;
    public String object = null;
    public String effect = null;
    public Item item = null;

    public boolean inLOS = false;
    public boolean hasSeen = false;


    public Tile(int x, int y, String spriteName, boolean solid){
        this.x = x;
        this.y = y;
        this.spriteName = spriteName;
        this.solid = solid;
    }

    public String getSpriteName(){
        return spriteName;
    }

    public void setSpriteName(String spriteName){
        this.spriteName = spriteName;
    }

    public void setSolid(boolean solid){
        this.solid = solid;
    }

    public boolean isSolid(){
        return solid;
    }

    public Position getPosition(){
        return new Position(x, y);
    }

    public void setItem(Item item){
        this.item = item;
    }

    public Item getItem(){
        return item;
    }

    public Item pickUpItem(){
        Item i = item;
        item = null;
        return i;
    }


}
