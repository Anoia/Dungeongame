package com.stuckinadrawer.dungeongame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.stuckinadrawer.dungeongame.DungeonGame;
import com.stuckinadrawer.dungeongame.Renderer;
import com.stuckinadrawer.dungeongame.levelGeneration.Generator;
import com.stuckinadrawer.dungeongame.tiles.Tile;

public class GameScreen extends AbstractScreen {

    private OrthographicCamera camera;
    private Renderer renderer;
    private float timer = 0;
    private Tile[][] level;

    public GameScreen(DungeonGame dungeonGame) {
        super(dungeonGame);
        Generator generator = new Generator();
        level = generator.getNewLevel();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        renderer = new Renderer(level, camera);



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
