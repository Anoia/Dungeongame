package com.stuckinadrawer.dungeongame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.stuckinadrawer.dungeongame.DungeonGame;

public class GameScreen extends AbstractScreen {

    private OrthographicCamera camera;
    private float timer = 0;

    public GameScreen(DungeonGame dungeonGame) {
        super(dungeonGame);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());



    }

    private void update(float delta){

    }

    private void processTurn() {


    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        camera.update();


    }
}
