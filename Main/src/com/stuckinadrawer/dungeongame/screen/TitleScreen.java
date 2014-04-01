package com.stuckinadrawer.dungeongame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.stuckinadrawer.dungeongame.GameContainer;

public class TitleScreen extends AbstractScreen {

    Stage stage;

    public TitleScreen(GameContainer gameContainer) {
        super(gameContainer);
        //stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

        Table menu = new Table(skin);

        final TextButton newGameButton = new TextButton("New Game", skin);
        menu.add(newGameButton).width(250).height(75).pad(20);
        menu.row();
        newGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameContainer.setScreen(new GameScreen(gameContainer));
            }
        });

        final TextButton newCharacterButton = new TextButton("Create Char", skin);
        menu.add(newCharacterButton).width(250).height(75).pad(20);
        menu.row();
        newCharacterButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("KLICK!");
                gameContainer.setScreen(new CharacterCreationScreen(gameContainer));
            }
        });

        final TextButton settingsButton = new TextButton("Settings", skin);
        menu.add(settingsButton).width(250).height(75).pad(20);
        menu.row();

        final TextButton quitButton = new TextButton("Quit", skin);
        menu.add(quitButton).width(250).height(75).pad(20);
        menu.row();
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        menu.pack();

        stage.addActor(menu);
        menu.setPosition(Gdx.graphics.getWidth()/2-menu.getWidth()/2, Gdx.graphics.getHeight()/2-menu.getHeight()/2);


    }

    @Override
    public void render(float delta){
        super.render(delta);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));


        batch.begin();
        Gdx.gl.glClearColor((float) (74/255.99), (float) (81/255.99), (float) (115/255.99), 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.end();

        ShapeRenderer r = new ShapeRenderer();

        Color darker = new Color((float) (74/255.99), (float) (81/255.99), (float) (115/255.99), 1f);
        Color lighter = new Color((float) (123/255.99), (float) (134/255.99), (float) (173/255.99), 1f);

       // r.begin(ShapeRenderer.ShapeType.FilledRectangle);
     //   r.setColor(darker);
     //   r.filledRect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), darker, darker, lighter, lighter);
     //   r.end();


        stage.draw();

    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }
}
