package com.stuckinadrawer.dungeongame.screen;

import com.stuckinadrawer.dungeongame.GameContainer;
import com.stuckinadrawer.dungeongame.items.WeaponGenerator;

public class TestScreen extends AbstractScreen {

    private WeaponGenerator weaponGenerator;

    public TestScreen(GameContainer gameContainer) {
        super(gameContainer);
        weaponGenerator = new WeaponGenerator();

    }

    @Override
    public void show(){
        for(int i = 0; i < 10; i++){
            weaponGenerator.createNewWeapon(1);
        }

    }


    @Override
    public void render(float delta){

    }
}