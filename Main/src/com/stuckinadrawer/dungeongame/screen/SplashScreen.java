package com.stuckinadrawer.dungeongame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stuckinadrawer.dungeongame.GameContainer;

public class SplashScreen extends AbstractScreen {

    private Texture splashTexture;
    private TextureRegion splashTextureRegion;
    private float time = 0;

    public SplashScreen(GameContainer gameContainer){
        super(gameContainer);
    }

    @Override
    public void show(){
        super.show();

        splashTexture = new Texture("splash.png");
        splashTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        splashTextureRegion = new TextureRegion(splashTexture, 0, 0, 512, 301);

    }

    @Override
    public void render(float delta){
        super.render(delta);

        time += delta;

        batch.begin();
        batch.draw(splashTextureRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        if(time > 2){
            gameContainer.setScreen(new TitleScreen(gameContainer));
        }

    }

    @Override
    public void dispose(){
        super.dispose();
        splashTexture.dispose();
    }

}
