package com.stuckinadrawer.dungeongame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.stuckinadrawer.dungeongame.tiles.Tile;

public class GestureDetection implements GestureDetector.GestureListener{
    private OrthographicCamera camera;
    private Level level;

    public GestureDetection(OrthographicCamera camera, Level level) {
        this.camera = camera;
        this.level = level;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        Gdx.app.log("hallo", "TAP: X: " + x + " Y: "+ y);

        Vector3 v = new Vector3(x, y, 0);
        camera.unproject(v);

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
        camera.translate(-deltaX, deltaY);
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

    }
}
