package com.stuckinadrawer.dungeongame.old;

import com.badlogic.gdx.Game;
import com.stuckinadrawer.dungeongame.old.screen.GameScreen;

public class DungeonGame extends Game{

    @Override
    public void create() {
        //this.setScreen(new SplashScreen(this));
        this.setScreen(new GameScreen(this));
    }
}
