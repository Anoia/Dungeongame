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
    private ArrayList<Enemy> enemies;


    public LevelCreator(){
        scatter = new GeneratorScatterLayout();
    }

    public Level getNewLevel(Player player){

        TileEnum[][] levelEnum = scatter.generate();

        Tile[][] data = new Tile[scatter.levelWidth][scatter.levelHeight];

        enemies = new ArrayList<Enemy>();



        for(int x = 0; x < data.length; x++){
            for(int y = 0; y < data[x].length; y++){
                Tile t = null;
                switch (levelEnum[x][y]) {
                    case EMPTY:
                        t = new WallTile(x, y, "tile_empty");
                        break;
                    case WALL:
                        t = new WallTile(x, y, "tile_wall_plain");
                        int val = getNeighbourValueForWall(t, levelEnum);
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
                        if(player.getPosition().getX() == 0 && player.getPosition().getY() == 0){
                            player.setPosition(x, y);
                        }
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
                data[x][y] = t;

            }
        }

        Level level = new Level(scatter.levelWidth, scatter.levelHeight, data);
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
        if(isInLevelArea(tile.x-1, tile.y, levelEnum) && levelEnum[tile.x-1][tile.y] == TileEnum.WALL){
            val += 8;
        }
        if(isInLevelArea(tile.x+1, tile.y, levelEnum) && levelEnum[tile.x+1][tile.y] == TileEnum.WALL){
            val += 2;
        }
        if(isInLevelArea(tile.x, tile.y-1, levelEnum) && levelEnum[tile.x][tile.y-1] == TileEnum.WALL){
            val += 4;
        }
        if(isInLevelArea(tile.x, tile.y+1, levelEnum) && levelEnum[tile.x][tile.y+1] == TileEnum.WALL){
            val += 1;
        }
        WallTile wall = (WallTile) tile;
        wall.neighbourValue = val;

        return 0;
    }

    public boolean isInLevelArea(int x, int y, TileEnum[][] tileEnum){
        return !(x < 0 || y < 0 || x >= tileEnum.length || y >= tileEnum[1].length);
    }


}
