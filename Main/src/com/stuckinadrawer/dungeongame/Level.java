package com.stuckinadrawer.dungeongame;

import com.stuckinadrawer.dungeongame.actors.enemies.Enemy;
import com.stuckinadrawer.dungeongame.tiles.Tile;

import java.util.ArrayList;

public class Level {
    private Tile[][] tiles;
    private ArrayList<Enemy> enemies;
    private int width;
    private int height;

    public Level(int width, int height, Tile[][] tiles){
        this.width = width;
        this.height = height;
        this.tiles = tiles;
        enemies = new ArrayList<Enemy>();
    }

    public Tile[][] getLevelData(){
        return tiles;
    }

    public void addEnemy(Enemy e){
        enemies.add(e);
    }

    public void addEnemies(ArrayList<Enemy> enemies){
        this.enemies.addAll(enemies);
    }

    public ArrayList<Enemy> getEnemies(){
        return enemies;
    }



}
