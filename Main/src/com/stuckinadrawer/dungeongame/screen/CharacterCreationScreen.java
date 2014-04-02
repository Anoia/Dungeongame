package com.stuckinadrawer.dungeongame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.stuckinadrawer.dungeongame.GameContainer;
import com.stuckinadrawer.dungeongame.actors.Player;

import java.util.ArrayList;

public class CharacterCreationScreen extends AbstractScreen {

    Stage stage;
    Table table;
    private int startValue = 5;
    private int totalPointsToSpend = 45;
    private int pointsLeftToSpend;

    private Label pointsLeftLabel;

    ArrayList<PlusMinusButtons> buttons = new ArrayList<PlusMinusButtons>();

    CharacterCreationScreen(GameContainer gameContainer) {
        super(gameContainer);


    }

    @Override
    public void show(){
        pointsLeftToSpend = totalPointsToSpend - startValue * 7;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        VerticalGroup group = new VerticalGroup();
        table = new Table(skin);

        buttons.add(new PlusMinusButtons("Strength", startValue));
        buttons.add(new PlusMinusButtons("Perception", startValue));
        buttons.add(new PlusMinusButtons("Endurance", startValue));
        buttons.add(new PlusMinusButtons("Charisma", startValue));
        buttons.add(new PlusMinusButtons("Intelligence", startValue));
        buttons.add(new PlusMinusButtons("Agility", startValue));
        buttons.add(new PlusMinusButtons("Luck", startValue));



        table.pack();

        final Label title = new Label("You're S.P.E.C.I.A.L!", skin, "big");


        pointsLeftLabel = new Label("Points left to spend: "+pointsLeftToSpend, skin);

        group.space(40);
        group.addActor(title);
        group.addActor(pointsLeftLabel);
        group.addActor(table);
        group.pack();
        group.setPosition(Gdx.graphics.getWidth() / 2 - group.getWidth() / 2, Gdx.graphics.getHeight() / 2 - table.getHeight() / 2);
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

        stage.draw();

        batch.end();

    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }

    private class PlusMinusButtons extends HorizontalGroup{
        private int value;
        private Label labelValue;
        public PlusMinusButtons(String name,int initalValue){
            this.value = initalValue;
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
                    }
                }
            });
            this.addActor(minus);
            this.addActor(labelValue);
            this.addActor(plus);

            this.pack();
            table.add(new Label(name, skin));
            table.add(this);

        }

        public int getValue() {
            return value;
        }
    }
}
