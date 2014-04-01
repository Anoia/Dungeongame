package com.stuckinadrawer.dungeongame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.stuckinadrawer.dungeongame.*;
import com.stuckinadrawer.dungeongame.actors.Player;
import com.stuckinadrawer.dungeongame.actors.enemies.Enemy;
import com.stuckinadrawer.dungeongame.levelGeneration.LevelCreator;
import com.stuckinadrawer.dungeongame.render.Renderer;
import com.stuckinadrawer.dungeongame.tiles.Tile;
import com.stuckinadrawer.dungeongame.util.Constants;
import com.stuckinadrawer.dungeongame.util.GestureDetection;

import java.util.ArrayList;

public class GameScreen extends AbstractScreen {

    private OrthographicCamera camera;
    private Renderer renderer;

    private Level level;
    private Player player;

    private HUD hud;

    Stage stage;

    float movementTimer = 0;


    /**
     * Initialize ALL THE THINGS!
     * @param gameContainer
     */

    public GameScreen(GameContainer gameContainer, Player player) {
        super(gameContainer);

        LevelCreator levelCreator = new LevelCreator();
        level = levelCreator.getNewLevel(player);   //Player ohne Pos in den level gen
        this.player = level.getPlayer();            //player mit pos aus level
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        renderer = new Renderer(level, camera, fontBig);
        camera.position.set(player.getPosition().getX()* Constants.TILE_SIZE, player.getPosition().getY()*Constants.TILE_SIZE, 0);



    }

    public GameScreen(GameContainer gameContainer){
        this(gameContainer, new Player());
    }



    /**
     * set up UI, gesture detection, inital fov
     */
    @Override
    public void show() {
        // Create a new Stage
       // stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        stage = new Stage();
        // Create and set InputProcessors
        GestureDetector gd = new GestureDetector(new GestureDetection(camera, level));
        InputMultiplexer im = new InputMultiplexer(stage, gd);
        Gdx.input.setInputProcessor(im);

        // Create a HUD
        hud = new HUD(this);

        // Give player inital FOV
        level.updateFOV();
}

    @Override
    public void dispose(){
        stage.dispose();
    }

    private void update(float delta){
        movementTimer +=delta;
        if(player.isMoving != -1){
            player.updateRenderPosition(delta);
            camera.position.set(player.renderPosition.getX(), player.renderPosition.getY(), 0);
        }else{
            if(movementTimer >0.1){
                //moves player if his movement queue is not empty
                if(player.action()){
                    processTurn();
                }
                movementTimer = 0;
            }
        }
    }


    /**
     * makes things happen after the player had a turn, moves enemies etc
     */
    private void processTurn() {

        ArrayList<Enemy> dead = new ArrayList<Enemy>();
        for(Enemy e: level.getEnemies()){
            if(e.dead){
                //level.removeEnemy(e);
                Tile t = level.getTile(e.getPosition().getX(), e.getPosition().getY());
                t.object = "effect_blood";
                dead.add(e);
            }else{
                e.doTurn(level);
            }
        }
        level.getEnemies().removeAll(dead);

    }

    /**
     * this is called by Libgdx every frame,
     * has to call the update() method for stuff to happen
     * @param delta
     */
    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        camera.update();
        renderer.update(delta);
        stage.draw();

    }

    public Player getPlayer() {
        return player;
    }

    public Skin getSkin(){
        return skin;
    }

    public Stage getStage(){
        return stage;
    }
}
