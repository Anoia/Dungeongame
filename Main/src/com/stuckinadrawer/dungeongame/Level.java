package com.stuckinadrawer.dungeongame;

import com.stuckinadrawer.dungeongame.actors.Actor;
import com.stuckinadrawer.dungeongame.actors.Player;
import com.stuckinadrawer.dungeongame.actors.enemies.Enemy;
import com.stuckinadrawer.dungeongame.render.TextAnimation;
import com.stuckinadrawer.dungeongame.tiles.Tile;

import java.util.ArrayList;

public class Level {
    private Tile[][] tiles;
    private ArrayList<Enemy> enemies;
    private Player player;
    private int width;

    public ArrayList<TextAnimation> textAnimations;



    private int height;

    private Pathfinder pathfinder;
    private RayTracer rayTracer;

    public Level(int width, int height, Tile[][] tiles){
        this.width = width;
        this.height = height;
        this.tiles = tiles;
        enemies = new ArrayList<Enemy>();
        player = null;
        pathfinder = new Pathfinder(this);
        rayTracer = new RayTracer(this);
        textAnimations = new ArrayList<TextAnimation>();
    }

    public Tile[][] getLevelData(){
        return tiles;
    }
    public Tile getTile(int x, int y){
        if(x < 0 || y < 0 || x >= width || y >= height){
            return null;
        }else{
            return getLevelData()[x][y];
        }

    }

    public boolean isSolid(int x, int y){
        Tile t = getTile(x, y);
        return t.isSolid();
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

    public boolean isWalkable(int x, int y){
        Tile t = tiles[x][y];
        boolean walkable = (!t.isSolid() && !isOccupied(x, y));
        return walkable;
    }

    public boolean isOccupied(int x, int y){
        Tile t = tiles[x][y];
        boolean occupied = !(t.object == null);

        return occupied;
    }


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        player.setMap(this);
    }

    public void findPath(Actor a, Position goal){
        a.setMovementPath(pathfinder.findPath(a.getPosition(), goal));
    }

    public void removeEnemy(Enemy e) {
        enemies.remove(e);

    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void updateFOV() {
        rayTracer.calculatePlayerFOV();
    }

    public boolean isInLOS(Position start, Position goal, int range){
        return rayTracer.castRay(start, goal, range, false);
    }
}
