package com.stuckinadrawer.dungeongame;

import com.badlogic.gdx.Game;
import com.stuckinadrawer.dungeongame.screen.GameScreen;
import com.stuckinadrawer.dungeongame.screen.SplashScreen;

public class DungeonGame extends Game{

    @Override
    public void create() {
        //this.setScreen(new SplashScreen(this));
        this.setScreen(new GameScreen(this));
    }
}
