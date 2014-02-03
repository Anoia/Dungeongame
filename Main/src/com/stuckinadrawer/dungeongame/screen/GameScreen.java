package com.stuckinadrawer.dungeongame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.stuckinadrawer.dungeongame.DungeonGame;
import com.stuckinadrawer.dungeongame.GestureDetection;
import com.stuckinadrawer.dungeongame.Renderer;
import com.stuckinadrawer.dungeongame.levelGeneration.LevelCreator;
import com.stuckinadrawer.dungeongame.tiles.Tile;

public class GameScreen extends AbstractScreen {

    private OrthographicCamera camera;
    private Renderer renderer;
    private Tile[][] level;

    public GameScreen(DungeonGame dungeonGame) {
        super(dungeonGame);
        LevelCreator levelCreator = new LevelCreator();
        level = levelCreator.getNewLevel();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
        renderer = new Renderer(level, camera);
        Gdx.input.setInputProcessor(new GestureDetector(new GestureDetection(camera, level)));



    }

    private void update(float delta){

    }

    private void processTurn() {


    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        renderer.update();
        camera.update();


    }
}
