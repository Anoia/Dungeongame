package com.stuckinadrawer.dungeongame.old.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stuckinadrawer.dungeongame.old.DungeonGame;

class AbstractScreen implements Screen{

    DungeonGame dungeonGame;
    BitmapFont font;
    SpriteBatch batch;


    AbstractScreen(DungeonGame dungeonGame){
        this.dungeonGame = dungeonGame;
        this.font = new BitmapFont(Gdx.files.internal("visitor.fnt"), Gdx.files.internal("visitor_0.png"), false);
        this.batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        font.dispose();
        batch.dispose();
    }
}
