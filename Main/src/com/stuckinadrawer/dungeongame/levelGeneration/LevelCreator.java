package com.stuckinadrawer.dungeongame.levelGeneration;

import com.stuckinadrawer.dungeongame.Level;
import com.stuckinadrawer.dungeongame.util.Utils;
import com.stuckinadrawer.dungeongame.actors.Player;
import com.stuckinadrawer.dungeongame.actors.enemies.Enemy;
import com.stuckinadrawer.dungeongame.actors.enemies.Rat;
import com.stuckinadrawer.dungeongame.actors.enemies.Skeleton;
import com.stuckinadrawer.dungeongame.actors.enemies.Witch;
import com.stuckinadrawer.dungeongame.tiles.FloorTile;
import com.stuckinadrawer.dungeongame.tiles.Tile;
import com.stuckinadrawer.dungeongame.tiles.WallTile;

import java.util.ArrayList;

public class LevelCreator {

    private GeneratorScatterLayout scatter;
    private GeneratorBSP bsp;
    private GeneratorRogueAlgorithm rogue;
    private ArrayList<Enemy> enemies;

    TileEnum[][] levelEnum;


    public LevelCreator(){
        scatter = new GeneratorScatterLayout();
        bsp = new GeneratorBSP();
        rogue = new GeneratorRogueAlgorithm();
    }

    public Level getNewLevel(Player player){

        levelEnum = rogue.generate();

        Tile[][] data = new Tile[rogue.levelWidth][rogue.levelHeight];

        enemies = new ArrayList<Enemy>();



        for(int x = 0; x < data.length; x++){
            for(int y = 0; y < data[x].length; y++){
                Tile t = null;
                switch (levelEnum[x][y]) {
                    case EMPTY:
                        t = new FloorTile(x, y, "tile_empty");
                        break;
                    case WALL:
                        t = new WallTile(x, y, "tile_wall_plain");
                        if(Utils.random(9) >4){
                            t.setSpriteName("tile_wall_cracked");
                        }else if(Utils.random(7)>4){
                            t.setSpriteName("tile_wall_moss");
                        }

                        if(Utils.random(30) < 1){
                            t.object = "obj_torch";
                        }
                        break;
                    case ROOM:
                        t = new FloorTile(x, y, "tile_floor");
                        if(Utils.random(9) >4 ){
                            t.setSpriteName("tile_floor_moss");
                        }
                        if(Utils.random(50) < 1){
                            placeObject(t);
                        }else if(Utils.random(50) < 1){
                            placeEnemy(x, y);
                        }
                        break;
                    case CORRIDOR:
                        t = new FloorTile(x, y, "tile_floor");
                        break;
                    case ENTRANCE:
                        player.setPosition(x, y);
                        t = new FloorTile(x, y, "tile_floor");
                        t.object = "tile_stairs_up";
                        break;
                    case EXIT:
                        t = new FloorTile(x, y, "tile_floor");
                        t.object = "tile_stairs_down";
                        break;

                }
                data[x][y] = t;

            }
        }

        Level level = new Level(rogue.levelWidth, rogue.levelHeight, data);
        level.setPlayer(player);
        level.addEnemies(enemies);

        return level;
    }


    private void placeEnemy(int x, int y) {
        int rnd = Utils.random(3);
        switch (rnd){
            case 0:
                enemies.add(new Skeleton(x, y));
                break;
            case 1:
                enemies.add(new Witch(x, y));
                break;
            case 2:
                enemies.add(new Rat(x, y));
                break;
            case 3:
                enemies.add(new Rat(x, y));
                break;
        }
    }

    private void placeObject(Tile t) {
        int rnd = Utils.random(3);
        switch (rnd){
            case 0:
                t.object = "obj_coffin";
                break;
            case 1:
                t.object = "obj_rip";
                break;
            case 2:
                t.object = "obj_bookshelf";
                break;
            case 3:
                t.object = "obj_urn";
                break;
        }

    }

    public int getNeighbourValueForWall(Tile tile, TileEnum[][] levelEnum){
        int val = 0;
        if(isWall(tile.x-1, tile.y)){
            val += 8;
        }
        if(isWall(tile.x+1, tile.y)){
            val += 2;
        }
        if(isWall(tile.x, tile.y-1)){
            val += 4;
        }
        if(isWall(tile.x, tile.y+1)){
            val += 1;
        }

        switch(val){
            case 7:
                if(isWall(tile.x+1, tile.y-1) && isWall(tile.x+1, tile.y+1)){
                    val = 5;
                }
                break;
            case 11:
                if(isWall(tile.x-1, tile.y+1) && isWall(tile.x+1, tile.y+1)){
                    val = 10;
                }
                break;
            case 13:
                if(isWall(tile.x-1, tile.y-1) && isWall(tile.x-1, tile.y+1)){
                    val = 5;
                }
                break;
            case 14:
                if(isWall(tile.x-1, tile.y-1) && isWall(tile.x+1, tile.y-1)){
                    val = 10;
                }
                break;
        }


        WallTile wall = (WallTile) tile;
        wall.neighbourValue = val;

        return 0;
    }

    public boolean isWall(int x, int y){
        return isInLevelArea(x, y) && levelEnum[x][y] == TileEnum.WALL;

    }

    public boolean isInLevelArea(int x, int y){
        return !(x < 0 || y < 0 || x >= levelEnum.length || y >= levelEnum[1].length);
    }


}
