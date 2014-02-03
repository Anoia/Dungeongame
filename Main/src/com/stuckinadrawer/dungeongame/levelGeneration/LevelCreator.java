package com.stuckinadrawer.dungeongame.levelGeneration;

import com.stuckinadrawer.dungeongame.Utils;
import com.stuckinadrawer.dungeongame.actors.enemies.Enemy;
import com.stuckinadrawer.dungeongame.actors.enemies.Rat;
import com.stuckinadrawer.dungeongame.actors.enemies.Skeleton;
import com.stuckinadrawer.dungeongame.actors.enemies.Witch;
import com.stuckinadrawer.dungeongame.tiles.FloorTile;
import com.stuckinadrawer.dungeongame.tiles.Tile;
import com.stuckinadrawer.dungeongame.tiles.WallTile;

import java.util.ArrayList;

public class LevelCreator {

    GeneratorScatterLayout scatter;
    ArrayList<Enemy> enemies;


    public LevelCreator(){
        scatter = new GeneratorScatterLayout();
    }

    public Tile[][] getNewLevel(){

        TileEnum[][] levelEnum = scatter.generate();

        Tile[][] level = new Tile[scatter.levelWidth][scatter.levelHeight];

        enemies = new ArrayList<Enemy>();

        for(int x = 0; x < level.length; x++){
            for(int y = 0; y < level[x].length; y++){
                Tile t = null;
                switch (levelEnum[x][y]) {
                    case EMPTY:
                        t = new WallTile(x, y, "tile_empty");
                        break;
                    case WALL:
                        t = new WallTile(x, y, "tile_wall_plain");
                        if(Utils.random(9) >4){
                            t.setSpriteName("tile_wall_cracked");
                        }else if(Utils.random(7)>4){
                            t.setSpriteName("tile_wall");
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
                }
                level[x][y] = t;

            }
        }
        return level;
    }

    public ArrayList<Enemy> getEnemies(){
        return enemies;
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


}
