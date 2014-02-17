package com.stuckinadrawer.dungeongame.actors;

import com.stuckinadrawer.dungeongame.Position;
import com.stuckinadrawer.dungeongame.Utils;

import java.util.LinkedList;

public abstract class Actor {

    protected int x;
    protected int y;
    public Position renderPosition;
    protected String spriteName;

    /* SPECIAL */
    public int strength;
    public int perception;
    public int endurance;
    public int charisma;
    public int intelligence;
    public int agility;
    public int luck;

    public int maxHP;
    public int currentHP;
    public LinkedList<Position> movementPath = null;
    public int dmgRange;
    public int viewDistance = 4;

    public boolean dead = false;




    public Actor(int x, int y){
        this.x = x;
        this.y = y;
        renderPosition = new Position(x, y);
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

    public void setMovementPath(LinkedList<Position> movementPath) {
        this.movementPath = movementPath;
    }
}
