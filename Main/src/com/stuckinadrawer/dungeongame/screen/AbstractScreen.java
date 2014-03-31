package com.stuckinadrawer.dungeongame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.stuckinadrawer.dungeongame.GameContainer;

class AbstractScreen implements Screen{

    GameContainer gameContainer;
    BitmapFont fontSmall;
    BitmapFont fontBig;
    SpriteBatch batch;

    Skin skin;

    AbstractScreen(GameContainer gameContainer){
        this.gameContainer = gameContainer;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pixelmix.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 12;
        this.fontBig = generator.generateFont(param);
        param.size = 24;
        this.fontSmall = generator.generateFont(param);
        generator.dispose();
        this.batch = new SpriteBatch();
        createSkinFromJSON();
    }

    private void createSkinFromJSON() {
        TextureAtlas atlas = new TextureAtlas("ui/uiskin.atlas");
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"), atlas);
        skin.add("small-font", fontSmall);
        skin.add("big-font", fontBig);




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
        fontBig.dispose();
        batch.dispose();
    }
}
