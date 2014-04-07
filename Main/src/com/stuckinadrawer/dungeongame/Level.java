package com.stuckinadrawer.dungeongame;

import com.stuckinadrawer.dungeongame.actors.Actor;
import com.stuckinadrawer.dungeongame.actors.Player;
import com.stuckinadrawer.dungeongame.actors.enemies.Enemy;
import com.stuckinadrawer.dungeongame.render.TextAnimation;
import com.stuckinadrawer.dungeongame.tiles.Tile;
import com.stuckinadrawer.dungeongame.util.Pathfinder;
import com.stuckinadrawer.dungeongame.util.Position;
import com.stuckinadrawer.dungeongame.util.RayTracer;

import java.util.ArrayList;
import java.util.LinkedList;

import static java.lang.Math.abs;

public class Level {
    private Tile[][] tiles;
    private ArrayList<Enemy> enemies;
    private Player player;
    private int width;
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
        return (!t.isSolid() && !isOccupied(x, y));
    }

    public boolean isOccupied(int x, int y){
        return (isOccupiedByObject(x, y) || isOccupiedByActor(x,y));
    }

    public boolean isOccupiedByActor(int x, int y){
        return getEnemyOnPos(new Position(x, y)) != null;
    }

    public boolean isOccupiedByObject(int x, int y){
        Tile t = tiles[x][y];
        return !(t.object == null);
    }

    public Enemy getEnemyOnPos(Position pos) {
        for(Enemy e: getEnemies()){
            if(!e.dead && pos.equals(e.getPosition())){
                return e;
            }
        }
        return null;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void findPathForActor(Actor a, Position goal){
        a.setMovementPath(pathfinder.findPath(a.getPosition(), goal));
    }

    public void removeEnemy(Enemy e) {
        Tile t = getTile(e.getPosition().getX(), e.getPosition().getY());
        t.effect = "effect_blood";
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

    /**
     * this shouldn't be here!
     */
    public void waitTurn() {
        player.movementPath = new LinkedList<Position>();
        player.movementPath.add(player.getPosition());

    }

    public boolean isTileNextToPlayer(int x, int y){
        int distance = abs(player.getPosition().getX() - x) + abs(player.getPosition().getY()-y);
        return (distance == 1);
    }
}
