package com.stuckinadrawer.dungeongame.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.stuckinadrawer.dungeongame.Level;
import com.stuckinadrawer.dungeongame.actors.Player;
import com.stuckinadrawer.dungeongame.actors.enemies.Enemy;
import com.stuckinadrawer.dungeongame.screen.GameScreen;
import com.stuckinadrawer.dungeongame.tiles.Tile;

import static java.lang.Math.abs;

public class GestureDetection implements GestureDetector.GestureListener{
    private GameScreen game;

    public GestureDetection(GameScreen game) {
        this.game = game;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        //Gdx.app.log("hallo", "TAP: X: " + x + " Y: "+ y);

        Vector3 v = new Vector3(x, y, 0);
        game.getCamera().unproject(v);

        handleClickOnTile((int) v.x/ Constants.TILE_SIZE, (int) v.y/ Constants.TILE_SIZE);


        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        game.getCamera().translate(-deltaX, deltaY);
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    private void handleClickOnTile(int x, int y){
        Level level = game.getLevel();
        Tile t  = level.getTile(x, y);
        if(isNeighbourTile(x, y) && level.isOccupiedByActor(x, y)){
            //attack!
            Enemy e = level.getEnemyOnPos(new Position(x,y));
            level.getPlayer().attack(e);
            game.processTurn();
        }else if(t!=null && !level.isSolid(x, y) && !level.isOccupiedByObject(x, y)&& t.hasSeen){
                    if(x == level.getPlayer().getPosition().getX() && y == level.getPlayer().getPosition().getY()){
                        level.waitTurn();
                    }else{
                        level.findPath(level.getPlayer(), new Position(x, y));
                    }

        }
    }

    private boolean isNeighbourTile(int x, int y){
        Player p = game.getPlayer();
        int distance = abs(p.getPosition().getX() - x) + abs(p.getPosition().getY()-y);
        return (distance == 1);
    }
}
