package com.stuckinadrawer.dungeongame.levelGeneration;

import com.stuckinadrawer.dungeongame.tiles.FloorTile;
import com.stuckinadrawer.dungeongame.tiles.Tile;
import com.stuckinadrawer.dungeongame.tiles.WallTile;

public class Generator {

    GeneratorScatterLayout scatter;

    public Generator(){
        scatter = new GeneratorScatterLayout();
    }

    public Tile[][] getNewLevel(){

        TileEnum[][] levelEnum = scatter.generate();

        Tile[][] level = new Tile[scatter.levelWidth][scatter.levelHeight];

        for(int x = 0; x < level.length; x++){
            for(int y = 0; y < level[x].length; y++){
                Tile t = null;
                switch (levelEnum[x][y]) {
                    case EMPTY:
                        t = new WallTile(x, y, "wall2");
                        break;
                    case WALL:
                        t = new WallTile(x, y, "wall1");
                        break;
                    case ROOM:
                        t = new FloorTile(x, y, "floor1");
                        break;
                    case CORRIDOR:
                        t = new FloorTile(x, y, "floor2");
                        break;
                }
                level[x][y] = t;

            }
        }
        return level;
    }


}
