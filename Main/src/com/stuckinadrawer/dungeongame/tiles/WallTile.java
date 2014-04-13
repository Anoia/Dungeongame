package com.stuckinadrawer.dungeongame.tiles;

public class WallTile extends Tile {

    public int neighbourValue = 0;


    public WallTile(int x, int y, String spriteName) {
        super(x, y, spriteName, true);
    }



}
