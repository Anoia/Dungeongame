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
    private int strength;
    private int perception;
    private int endurance;
    private int charisma;
    private int intelligence;
    private int agility;
    private int luck;

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

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;

    }

    public void changeStrength(int amount){
        this.strength += amount;
    }

    public int getPerception() {
        return perception;
    }

    public void setPerception(int perception) {
        this.perception = perception;
        this.viewDistance = perception;
    }

    public void changePerception(int amount){
        this.perception += amount;
        this.viewDistance = perception;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
        this.maxHP = endurance*5;
    }

    public void changeEndurance(int amount){
        this.endurance += amount;
        this.maxHP = endurance*5;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }


    public void changeCharisma(int amount) {
        this.charisma += amount;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public void changeIntelligence(int amount){
        this.intelligence += amount;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public void changeAgility(int amount) {
        this.agility += amount;
    }

    public int getLuck() {
        return luck;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public void changeLuck(int amount) {
        this.luck += amount;
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
                System.out.println(spriteName + " died!");
                die();

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
