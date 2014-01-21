package com.stuckinadrawer.dungeongame;


import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.stuckinadrawer.dungeongame.old.DungeonGame;

public class MyActivity extends AndroidApplication{

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = false;
        initialize(new DungeonGame(), cfg);
    }
}
