package com.stuckinadrawer.dungeongame.actors;

import com.stuckinadrawer.dungeongame.Position;

public abstract class Actor {
    protected int x;
    protected int y;
    protected String spriteName;
    protected int maxHP = 10;
    protected int currentHP = 10;

    public Actor(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Position getPosition(){
        return new Position(x,y);
    }

    public String getSpriteName() {
        return spriteName;
    }

    public void setSpriteName(String spriteName) {
        this.spriteName = spriteName;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }
}
