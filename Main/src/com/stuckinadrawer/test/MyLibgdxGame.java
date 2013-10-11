package com.stuckinadrawer.test;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;

public class MyLibgdxGame implements ApplicationListener{

    // constant useful for logging
    public static final String LOG = MyLibgdxGame.class.getSimpleName();

    // a libgdx helper class that logs the current FPS each second
    private FPSLogger fpsLogger;

    @Override
    public void create()
    {
        Gdx.app.log( MyLibgdxGame.LOG, "Creating game" );
        fpsLogger = new FPSLogger();
    }

    @Override
    public void resize(
            int width,
            int height )
    {
        Gdx.app.log( MyLibgdxGame.LOG, "Resizing game to: " + width + " x " + height );
    }

    @Override
    public void render()
    {
        // the following code clears the screen with the given RGB color (green)
        Gdx.gl.glClearColor( 0f, 1f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        // output the current FPS
        fpsLogger.log();
    }

    @Override
    public void pause()
    {
        Gdx.app.log( MyLibgdxGame.LOG, "Pausing game" );
    }

    @Override
    public void resume()
    {
        Gdx.app.log( MyLibgdxGame.LOG, "Resuming game" );
    }

    @Override
    public void dispose()
    {
        Gdx.app.log( MyLibgdxGame.LOG, "Disposing game" );
    }
}
