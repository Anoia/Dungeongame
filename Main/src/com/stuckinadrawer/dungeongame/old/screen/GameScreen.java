package com.stuckinadrawer.dungeongame.old.screen;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.stuckinadrawer.dungeongame.old.Constants;
import com.stuckinadrawer.dungeongame.old.DungeonGame;
import com.stuckinadrawer.dungeongame.old.EntityFactory;
import com.stuckinadrawer.dungeongame.old.LevelGenerator;
import com.stuckinadrawer.dungeongame.old.components.Position;
import com.stuckinadrawer.dungeongame.old.systems.AISystem;
import com.stuckinadrawer.dungeongame.old.systems.GestureDetectionSystem;
import com.stuckinadrawer.dungeongame.old.systems.PathfindingSystem;
import com.stuckinadrawer.dungeongame.old.systems.SpriteRenderSystem;

import java.util.LinkedList;

public class GameScreen extends AbstractScreen {

    private final SpriteRenderSystem spriteRenderSystem;
    private final AISystem aiSystem;
    private Entity[][] level;
    private Entity player;
    private World world;
    private OrthographicCamera camera;
    private float timer = 0;
    public static LinkedList<Position> movementPath = null;

    public GameScreen(DungeonGame dungeonGame) {
        super(dungeonGame);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        world = new World();

        world.setManager(new TagManager());

        spriteRenderSystem = world.setSystem(new SpriteRenderSystem(camera), true);

        world.initialize();


        player = EntityFactory.createPlayer(world, 30, 30);
        player.addToWorld();
        aiSystem = world.setSystem(new AISystem(), true);
        camera.position.set(player.getComponent(Position.class).getX() * Constants.TILE_SIZE, player.getComponent(Position.class).getY() * Constants.TILE_SIZE, 0);

        LevelGenerator generator = new LevelGenerator(world);
        level = generator.generateLevel();
        world.setSystem(new PathfindingSystem(world,level), true);

        Gdx.input.setInputProcessor(new GestureDetector(new GestureDetectionSystem(world, camera, level)));
        //Gdx.input.setInputProcessor(new PlayerInputSystem(world, camera, level));
        EntityFactory.createEnemy(world, 0, 0).addToWorld();


    }

    private void update(float delta){
        timer+=delta;
        if(timer>0.1){
            if(movementPath != null && !movementPath.isEmpty()){
                Position newPos = movementPath.pop();
                Position oldPos = player.getComponent(Position.class);
                oldPos.set(newPos);
                camera.position.set(newPos.getX()*Constants.TILE_SIZE, newPos.getY()*Constants.TILE_SIZE, 0);
                processTurn();
            }
            timer = 0;
        }
    }

    private void processTurn() {
        aiSystem.process();

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        camera.update();
        world.setDelta(delta);

        world.process();  //processes all non passive systems
        spriteRenderSystem.process();


    }
}
