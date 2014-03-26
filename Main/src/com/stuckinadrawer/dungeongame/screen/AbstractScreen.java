package com.stuckinadrawer.dungeongame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.stuckinadrawer.dungeongame.GameContainer;

class AbstractScreen implements Screen{

    GameContainer gameContainer;
    BitmapFont font;
    SpriteBatch batch;

    Skin skin;

    AbstractScreen(GameContainer gameContainer){
        this.gameContainer = gameContainer;
        this.font = new BitmapFont(Gdx.files.internal("ui/default.fnt"), Gdx.files.internal("ui/default.png"), false);
        this.batch = new SpriteBatch();
        createSkinFromJSON();
    }

    private void createSkin() {

        skin = new Skin();

        // Generate a 1x1 white texture and store it in the skin named "white".
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        //add default font
        skin.add("default", font);

        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.LIGHT_GRAY);

        textButtonStyle.font = skin.getFont("default");
        textButtonStyle.fontColor = Color.BLACK;
        textButtonStyle.disabled = skin.newDrawable("white", new Color(.9f,.9f,.9f,1.f));
        textButtonStyle.disabledFontColor = Color.GRAY;

        skin.add("default", textButtonStyle);


    }
    private void createSkinFromJSON() {
        TextureAtlas atlas = new TextureAtlas("ui/uiskin.atlas");
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"), atlas);




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
