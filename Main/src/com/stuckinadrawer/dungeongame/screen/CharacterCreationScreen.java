package com.stuckinadrawer.dungeongame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.stuckinadrawer.dungeongame.GameContainer;

public class CharacterCreationScreen extends AbstractScreen {

    Stage stage;
    Table table;
    private int s,p,e,c,i,a,l = 5;
   // private int totalPointsSpent = 30;
    private int pointsLeftToSpend = 10;

    CharacterCreationScreen(GameContainer gameContainer) {
        super(gameContainer);
        stage = new Stage();
        table = new Table(skin);

        final Label title = new Label("You're S.P.E.C.I.A.L!", skin, "big");
        table.add(title).pad(20);
        table.pack();
        stage.addActor(table);
        table.setPosition(Gdx.graphics.getWidth()/2-table.getWidth()/2, Gdx.graphics.getHeight()/2-table.getHeight()/2);

    }

    @Override
    public void show(){

    }


    @Override
    public void render(float delta){
        super.render(delta);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        batch.begin();
        Gdx.gl.glClearColor((float) (74/255.99), (float) (81/255.99), (float) (115/255.99), 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();

        batch.end();

    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }
}
