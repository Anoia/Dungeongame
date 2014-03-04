package com.stuckinadrawer.dungeongame;

import com.badlogic.gdx.Game;
import com.stuckinadrawer.dungeongame.screen.GameScreen;

public class GameContainer extends Game {

    @Override
    public void create() {
        //this.setScreen(new SplashScreen(this));
        this.setScreen(new GameScreen(this));
    }
}