package com.stuckinadrawer.dungeongame.tiles;

import com.stuckinadrawer.dungeongame.Position;

public abstract class Tile {
    int x;
    int y;
    String spriteName;
    boolean solid;


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

    public boolean getSolid(){
        return solid;
    }

    public Position getPosition(){
        return new Position(x, y);
    }


}
