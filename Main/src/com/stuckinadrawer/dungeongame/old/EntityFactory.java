package com.stuckinadrawer.dungeongame.old;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.TagManager;
import com.stuckinadrawer.dungeongame.old.components.*;

public class EntityFactory {

    //###ACTOR ENTITIES###
    public static Entity createPlayer(World world, int x, int y){
        Entity entity = world.createEntity();

        entity.addComponent(new Position(x, y));
        entity.addComponent(new Sprite("player", Sprite.Layer.ACTOR));
        entity.addComponent(new Health(20));

        world.getManager(TagManager.class).register(Constants.Tags.PLAYER, entity);

        return entity;
    }

    public static Entity createEnemy(World world, int x, int y){
        Entity entity = world.createEntity();
        entity.addComponent(new Position(x, y));
        entity.addComponent(new Sprite("enemy1", Sprite.Layer.ACTOR));
        entity.addComponent(new Health(10));
        entity.addComponent(new AI());
        return entity;
    }
    //###TILE ENTITIES###


    public static Entity createRoomFloorTile(World world, int x, int y){
        Entity entity = world.createEntity();
        entity.addComponent(new Position(x, y));
        entity.addComponent(new Sprite("floor1", Sprite.Layer.TILE));
        entity.addComponent(new Tile());
        return entity;
    }

    public static Entity createCorridorFloorTile(World world, int x, int y){
        Entity entity = world.createEntity();
        entity.addComponent(new Position(x, y));
        entity.addComponent(new Sprite("floor2", Sprite.Layer.TILE));
        entity.addComponent(new Tile());
        return entity;
    }



    public static Entity createWallTile(World world, int x, int y) {
        Entity entity = world.createEntity();
        entity.addComponent(new Position(x, y));
        entity.addComponent(new Sprite("wall1", Sprite.Layer.TILE));
        entity.addComponent(new Solid());
        entity.addComponent(new Tile());
        return entity;
    }

    public static Entity createEmptyTile(World world, int x, int y) {
        Entity entity = world.createEntity();
        entity.addComponent(new Position(x, y));
        entity.addComponent(new Sprite("wall2", Sprite.Layer.TILE));
        entity.addComponent(new Solid());
        entity.addComponent(new Tile());
        return entity;
    }

    //###OTHER ENTITIES###

    public static Entity createPathfindingGoal(World world, int x, int y){
        Entity entity = world.createEntity();
        entity.addComponent(new Position(x, y));
        world.getManager(TagManager.class).register(Constants.Tags.PLAYER_PATHFINDING_GOAL, entity);
        return entity;
    }
}
