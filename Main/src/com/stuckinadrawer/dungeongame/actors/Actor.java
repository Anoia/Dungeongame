package com.stuckinadrawer.dungeongame.actors;

import com.stuckinadrawer.dungeongame.Position;
import com.stuckinadrawer.dungeongame.Utils;

public abstract class Actor {

    protected int x;
    protected int y;
    protected String spriteName;


    public int maxHP;
    public int currentHP;

    public int dmgRange;

    public boolean dead = false;




    public Actor(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void attack(Actor opponent){
        if(!dead){
            int dmg = Utils.random(dmgRange);
            System.out.println(opponent.getSpriteName() + " taking "+dmg + " damage from "+spriteName);
            opponent.takeDmg(dmg);
        }
    }

    public void takeDmg(int dmg){
        if(!dead){
            currentHP -= dmg;
            System.out.println(spriteName + " has "+currentHP + " HP left");

            if(currentHP <= 0){
                die();
                System.out.println(spriteName + " died!");
            }
        }
    }

    protected void die() {
        System.out.println("I'm dead! - "+spriteName);
        this.spriteName = "effect_blood";
        dead = true;

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

}
