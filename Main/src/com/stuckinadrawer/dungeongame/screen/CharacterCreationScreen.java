package com.stuckinadrawer.dungeongame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.stuckinadrawer.dungeongame.GameContainer;
import com.stuckinadrawer.dungeongame.actors.Player;

import java.util.ArrayList;

public class CharacterCreationScreen extends AbstractScreen {

    ShapeRenderer r;
    Stage stage;
    Table table;
    private int startValue = 5;
    private int totalPointsToSpend = 45;
    private int pointsLeftToSpend;

    private Label pointsLeftLabel;
    private Label descriptionLabel;

    ArrayList<PlusMinusButtons> buttons = new ArrayList<PlusMinusButtons>();

    CharacterCreationScreen(GameContainer gameContainer) {
        super(gameContainer);
        r = new ShapeRenderer();

    }

    @Override
    public void show(){
        pointsLeftToSpend = totalPointsToSpend - startValue * 7;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        VerticalGroup group = new VerticalGroup();
        table = new Table(skin);

        buttons.add(new PlusMinusButtons("Strength", "Increases meele dmg" ,startValue));
        buttons.add(new PlusMinusButtons("Perception", " sight radius" ,startValue));
        buttons.add(new PlusMinusButtons("Endurance", " end" ,startValue));
        buttons.add(new PlusMinusButtons("Charisma", " char" ,startValue));
        buttons.add(new PlusMinusButtons("Intelligence", " int" ,startValue));
        buttons.add(new PlusMinusButtons("Agility", " ag" ,startValue));
        buttons.add(new PlusMinusButtons("Luck", " luck" ,startValue));



        table.pack();

        final Label title = new Label("You're S.P.E.C.I.A.L!", skin, "big");


        pointsLeftLabel = new Label("Points left to spend: "+pointsLeftToSpend, skin);
        descriptionLabel = new Label(" ", skin);
        descriptionLabel.setWrap(true);
        Container container = new Container(descriptionLabel);
        container.align(Align.center);
        container.prefWidth(300);

        group.space(20);
        group.setWidth(500);
        group.addActor(title);
        group.addActor(pointsLeftLabel);
        group.addActor(table);
        group.addActor(container);
        group.pack();
        group.setPosition(Gdx.graphics.getWidth() / 2 - group.getWidth() / 2, Gdx.graphics.getHeight() / 2 - group.getHeight() / 2);
        stage.addActor(group);



        //BACK FORWARD BUTTONS
        TextButton back = new TextButton("Back", skin);
        back.setPosition(50, 50);
        back.setSize(120, 50);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameContainer.setScreen(new TitleScreen(gameContainer));
            }
        });
        stage.addActor(back);

        TextButton forward = new TextButton("Continue", skin);
        forward.setSize(120, 50);
        forward.setPosition(Gdx.graphics.getWidth()-50-forward.getWidth(), 50);
        forward.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(pointsLeftToSpend == 0){
                    Player p = new Player();
                    p.setStrength(buttons.get(0).getValue());
                    p.setPerception(buttons.get(1).getValue());
                    p.setEndurance(buttons.get(2).getValue());
                    p.setCharisma(buttons.get(3).getValue());
                    p.setIntelligence(buttons.get(4).getValue());
                    p.setAgility(buttons.get(5).getValue());
                    p.setLuck(buttons.get(6).getValue());
                    gameContainer.setScreen(new GameScreen(gameContainer, p));
                }

            }
        });
        stage.addActor(forward);
    }


    @Override
    public void render(float delta){
        super.render(delta);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        batch.begin();
        Gdx.gl.glClearColor((float) (74/255.99), (float) (81/255.99), (float) (115/255.99), 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.end();

        Color darker = new Color((float) (74/255.99), (float) (81/255.99), (float) (115/255.99), 1f);
        Color lighter = new Color((float) (123/255.99), (float) (134/255.99), (float) (173/255.99), 1f);

        r.begin(ShapeRenderer.ShapeType.Filled);
        r.setColor(darker);
        r.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), darker, darker, lighter, lighter);
        r.end();

        stage.draw();

    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }

    private class PlusMinusButtons extends HorizontalGroup{
        private int value;
        private Label labelValue;
        private String description;
        public PlusMinusButtons(String name, String descr, int initalValue){
            this.value = initalValue;
            this.description = descr;
            table.row().pad(10);
            this.space(10);
            labelValue = new Label(value+"", skin);
            labelValue.setWidth(50);
            Button minus = new Button(skin, "minus");
            minus.pad(10);
            minus.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (value > 1) {
                        value--;
                        pointsLeftToSpend++;
                        pointsLeftLabel.setText("Points left to spend: "+pointsLeftToSpend);
                        labelValue.setText(value + "");
                        descriptionLabel.setText(description);
                    }
                }
            });

            Button plus = new Button(skin, "plus");
            plus.pad(10);
            plus.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if(pointsLeftToSpend > 0){
                        value ++;
                        pointsLeftToSpend--;
                        pointsLeftLabel.setText("Points left to spend: "+pointsLeftToSpend);
                        labelValue.setText(value+"");
                        descriptionLabel.setText(description);
                    }
                }
            });
            this.addActor(minus);
            this.addActor(labelValue);
            this.addActor(plus);

            this.pack();
            Label nameLabel = new Label(name, skin);
            nameLabel.addListener(new ClickListener(){
                @Override
            public void clicked(InputEvent event, float x, float y){
                    descriptionLabel.setText(description);
                }

            });
            table.add(nameLabel);
            table.add(this);

        }

        public int getValue() {
            return value;
        }
    }
}
