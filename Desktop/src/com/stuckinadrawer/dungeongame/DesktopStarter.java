package com.stuckinadrawer.dungeongame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class DesktopStarter {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "The Dungeongame";
        cfg.useGL20 = true;
        cfg.width = 800*2;
        cfg.height = 480*2;
        new LwjglApplication(new DungeonGame(), cfg);
    }
}
