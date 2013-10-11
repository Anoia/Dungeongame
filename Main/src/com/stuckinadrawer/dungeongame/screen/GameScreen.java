package com.stuckinadrawer.dungeongame.screen;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.stuckinadrawer.dungeongame.Constants;
import com.stuckinadrawer.dungeongame.DungeonGame;
import com.stuckinadrawer.dungeongame.EntityFactory;
import com.stuckinadrawer.dungeongame.LevelGenerator;
import com.stuckinadrawer.dungeongame.components.Position;
import com.stuckinadrawer.dungeongame.systems.GestureDetectionSystem;
import com.stuckinadrawer.dungeongame.systems.PlayerInputSystem;
import com.stuckinadrawer.dungeongame.systems.SpriteRenderSystem;

public class GameScreen extends AbstractScreen{

    private final SpriteRenderSystem spriteRenderSystem;
    private World world;
    private OrthographicCamera camera;
    public Entity[][] level;

    public GameScreen(DungeonGame dungeonGame) {
        super(dungeonGame);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());




        world = new World();

        world.setManager(new TagManager());

        spriteRenderSystem = world.setSystem(new SpriteRenderSystem(camera), true);
        world.initialize();



        Entity player = EntityFactory.createPlayer(world, 4, 5);
        player.addToWorld();

        camera.position.set(player.getComponent(Position.class).getX()* Constants.TILE_SIZE, player.getComponent(Position.class).getY()* Constants.TILE_SIZE, 0);

        LevelGenerator generator = new LevelGenerator(world);
        level = generator.generateLevel();

        Gdx.input.setInputProcessor(new GestureDetector(new GestureDetectionSystem(world, camera, level)));
        EntityFactory.createEnemy(world, 0, 0).addToWorld();



    }

    @Override
    public void render(float delta){
        super.render(delta);
        camera.update();
        world.setDelta(delta);

        world.process();  //processes all non passive systems
        spriteRenderSystem.process();



    }
}
