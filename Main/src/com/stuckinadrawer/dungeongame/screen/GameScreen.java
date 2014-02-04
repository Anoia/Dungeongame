package com.stuckinadrawer.dungeongame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.stuckinadrawer.dungeongame.*;
import com.stuckinadrawer.dungeongame.actors.Player;
import com.stuckinadrawer.dungeongame.actors.enemies.Enemy;
import com.stuckinadrawer.dungeongame.levelGeneration.LevelCreator;

public class GameScreen extends AbstractScreen {

    private OrthographicCamera camera;
    private Renderer renderer;

    private Level level;
    private Player player;

    Stage stage;

    float timer = 0;

    public GameScreen(DungeonGame dungeonGame) {
        super(dungeonGame);

        LevelCreator levelCreator = new LevelCreator();
        level = levelCreator.getNewLevel();
        player = level.getPlayer();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        renderer = new Renderer(level, camera);
        //Gdx.input.setInputProcessor(new GestureDetector(new GestureDetection(camera, level)));
        camera.position.set(player.getPosition().getX()*Constants.TILE_SIZE, player.getPosition().getY()*Constants.TILE_SIZE, 0);


    }
    @Override
    public void show() {
        System.out.println("I'm showing!");
        stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        GestureDetector gd = new GestureDetector(new GestureDetection(camera, level));
        InputMultiplexer im = new InputMultiplexer(stage, gd);
        Gdx.input.setInputProcessor(im);


        final TextButton button = new TextButton("The Button", skin);
        button.setPosition(200, 200);
        button.setSize(200, 50);

        stage.addActor(button);

        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("I was clicked, yay!");
            }
        });



    }

    @Override
    public void dispose(){
        stage.dispose();
    }

    private void update(float delta){
        timer+=delta;
        if(timer>0.1){
            //moves player if his movement queue is not empty
            if(player.action()){
                camera.position.set(player.getPosition().getX()*Constants.TILE_SIZE, player.getPosition().getY()*Constants.TILE_SIZE, 0);
                processTurn();
            }
            timer = 0;
        }
    }

    private void processTurn() {
        for(Enemy e: level.getEnemies()){
            if(e.dead){
                //level.removeEnemy(e);
            }else{
                e.doTurn(player);
            }
        }

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        camera.update();
        renderer.update();
        stage.draw();


    }
}
