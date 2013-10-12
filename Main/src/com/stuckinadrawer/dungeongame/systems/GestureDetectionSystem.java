package com.stuckinadrawer.dungeongame.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.annotations.Mapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.stuckinadrawer.dungeongame.Constants;
import com.stuckinadrawer.dungeongame.EntityFactory;
import com.stuckinadrawer.dungeongame.components.Solid;

public class GestureDetectionSystem implements GestureDetector.GestureListener{

    private World world;
    private OrthographicCamera camera;
    @Mapper
    private
    ComponentMapper<Solid> solidComponentMapper;

    public GestureDetectionSystem(World world, OrthographicCamera camera, Entity[][] level){

        this.world = world;
        this.camera = camera;
        solidComponentMapper = world.getMapper(Solid.class);

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

        Entity goal = EntityFactory.createPathfindingGoal(world, (int) v.x/ Constants.TILE_SIZE, (int) v.y/Constants.TILE_SIZE);
        goal.addToWorld();
        world.getSystem(PathfindingSystem.class).processSystem();
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
}
