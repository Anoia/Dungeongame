package com.stuckinadrawer.dungeongame.systems;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.annotations.Mapper;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.stuckinadrawer.dungeongame.Constants;
import com.stuckinadrawer.dungeongame.components.Position;
import com.stuckinadrawer.dungeongame.components.Solid;

public class PlayerInputSystem implements InputProcessor{

    World world;
    OrthographicCamera camera;
    Entity[][] level;
    @Mapper
    ComponentMapper<Solid> solidComponentMapper;
    Vector3 oldScreenCoordsDragged;

    public PlayerInputSystem(World world, OrthographicCamera camera, Entity[][] level) {
        this.world = world;
        this.camera = camera;
        this.level = level;
        solidComponentMapper = world.getMapper(Solid.class);
        oldScreenCoordsDragged = new Vector3(camera.position);
    }

    @Override
    public boolean keyDown(int keycode) {

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        Entity player = world.getManager(TagManager.class).getEntity(Constants.Tags.PLAYER);
        Position position = player.getComponent(Position.class);
        int x = position.getX();
        int y = position.getY();
        switch (character){
            case 'w':
                if(!isSolid(x, y+1)){
                    position.setY(y+1);
                }
                break;
            case 'a':
                if(!isSolid(x-1, y)){
                    position.setX(x-1);
                }
                break;
            case 's':
                if(!isSolid(x, y-1)){
                    position.setY(y-1);
                }
                break;
            case 'd':
                if(!isSolid(x+1, y)){
                    position.setX(x+1);
                }
                break;
        }
        camera.position.set(position.getX()*Constants.TILE_SIZE, position.getY()*Constants.TILE_SIZE, 0);
        return false;
    }

    private boolean isSolid(int x, int y){

        Entity tile = level[x][y];
        Solid solid = solidComponentMapper.get(tile);
        if(solid!=null){
            return true;
        } else{
            return false;

        }

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("ScreenX: "+screenX +" ScreenY: "+screenY+" pointer: + "+pointer + " button: "+button);
        Vector3 v = new Vector3(screenX, screenY, 0);
        camera.unproject(v);

        int x = (int) v.x/Constants.TILE_SIZE;
        int y = (int) v.y/Constants.TILE_SIZE;
        System.out.println("x: "+x+" y: "+y);
        //get the Tile Entity



        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 v = new Vector3(screenX, screenY, 0);
        float deltaX = oldScreenCoordsDragged.x - v.x;
        float deltaY = v.y - oldScreenCoordsDragged.y;
        camera.translate(deltaX, deltaY, 0);
        oldScreenCoordsDragged = new Vector3(v);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Vector3 v = new Vector3(screenX, screenY, 0);
        oldScreenCoordsDragged = new Vector3(v);

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
