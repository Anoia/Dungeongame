package com.stuckinadrawer.dungeongame;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.TagManager;
import com.stuckinadrawer.dungeongame.components.Position;
import com.stuckinadrawer.dungeongame.components.Solid;
import com.stuckinadrawer.dungeongame.components.Sprite;

public class EntityFactory {

    public static Entity createPlayer(World world, int x, int y){
        Entity entity = world.createEntity();

        entity.addComponent(new Position(x, y));
        entity.addComponent(new Sprite("player", Sprite.Layer.ACTOR));

        world.getManager(TagManager.class).register(Constants.Tags.PLAYER, entity);

        return entity;
    }

    public static Entity createRoomFloorTile(World world, int x, int y){
        Entity entity = world.createEntity();
        entity.addComponent(new Position(x, y));
        entity.addComponent(new Sprite("floor1", Sprite.Layer.TILE));
        return entity;
    }

    public static Entity createCorridorFloorTile(World world, int x, int y){
        Entity entity = world.createEntity();
        entity.addComponent(new Position(x, y));
        entity.addComponent(new Sprite("floor2", Sprite.Layer.TILE));
        return entity;
    }

    public static Entity createEnemy(World world, int x, int y){
        Entity entity = world.createEntity();
        entity.addComponent(new Position(x, y));
        entity.addComponent(new Sprite("enemy1", Sprite.Layer.ACTOR));
        return entity;
    }

    public static Entity createWallTile(World world, int x, int y) {
        Entity entity = world.createEntity();
        entity.addComponent(new Position(x, y));
        entity.addComponent(new Sprite("wall1", Sprite.Layer.TILE));
        entity.addComponent(new Solid());
        return entity;
    }

    public static Entity createEmptyTile(World world, int x, int y) {
        Entity entity = world.createEntity();
        entity.addComponent(new Position(x, y));
        entity.addComponent(new Sprite("wall2", Sprite.Layer.TILE));
        entity.addComponent(new Solid());
        return entity;
    }
}
