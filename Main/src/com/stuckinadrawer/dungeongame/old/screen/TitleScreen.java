package com.stuckinadrawer.dungeongame.old.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.stuckinadrawer.dungeongame.old.DungeonGame;

public class TitleScreen extends AbstractScreen {

    private float time = 0;

    public TitleScreen(DungeonGame dungeonGame) {
        super(dungeonGame);
    }

    @Override
    public void render(float delta){
        super.render(delta);
        time +=delta;

        batch.begin();
        Gdx.gl.glClearColor((float) (54/255.99), (float) (47/255.99), (float) (45/255.99), 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        font.draw(batch, "hello", 100, 100);
        font.draw(batch, "X: " + Gdx.graphics.getWidth(), 100, 200);
        font.draw(batch, "Y: " + Gdx.graphics.getHeight(), 100, 300);

        batch.end();

        ShapeRenderer r = new ShapeRenderer();
        r.begin(ShapeRenderer.ShapeType.FilledRectangle);
        r.setColor(1, 0, 0, 1);
        r.filledRect(0, 0, 64, 64);
        r.end();

        if(time > 2){
            dungeonGame.setScreen(new GameScreen(dungeonGame));
        }


    }
}
