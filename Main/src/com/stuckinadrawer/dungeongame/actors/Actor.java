package com.stuckinadrawer.dungeongame.actors;

import com.stuckinadrawer.dungeongame.util.Constants;
import com.stuckinadrawer.dungeongame.util.Position;
import com.stuckinadrawer.dungeongame.util.Utils;

import java.util.LinkedList;

public abstract class Actor {

    protected int x;
    protected int y;
    protected int oldX;
    protected int oldY;
    public Position renderPosition;
    public float isMoving = -1;
    private String spriteName;

    /* SPECIAL */
    private int strength;
    private int perception;
    private int endurance;
    private int charisma;
    private int intelligence;
    private int agility;
    private int luck;

    public int baseStrength = 8;

    public int maxHP;
    public int currentHP;
    public LinkedList<Position> movementPath = null;
    public int dmgRange;
    public int viewDistance = 4;

    public boolean dead = false;

    private int actionPoints = 0;

    //TEMP
    public int XPRewarded = 6;
    private int speed = 1;


    public Actor(int x, int y){
        this.x = x;
        this.y = y;
        setStrength(8);
        setPerception(4);
        setEndurance(5);
        setCharisma(5);
        setIntelligence(5);
        setAgility(5);
        setLuck(5);
        renderPosition = new Position(x* Constants.TILE_SIZE, y* Constants.TILE_SIZE);
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

    public int getAttackDamage(){
        int dmg = Utils.random(dmgRange);
        dmg = dmg + dmg * (getStrength() - baseStrength)/10;
        return dmg;
    }

    /**
     *
     * @param dmg amount of damage taken
     * @return boolean death of entity after taking damage
     */
    public boolean takeDmg(int dmg){
        currentHP -= dmg;
        System.out.println(spriteName + " has "+currentHP + " HP left");

        if(currentHP <= 0){
            System.out.println(spriteName + " died!");
            die();
        }
        return dead;
    }

    protected void die() {
        System.out.println("I'm dead! - "+spriteName);
        this.spriteName = "effect_blood";
        dead = true;

    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
        renderPosition = new Position(x* Constants.TILE_SIZE, y* Constants.TILE_SIZE);
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

    public void updateRenderPosition(float delta){
        isMoving +=delta * Constants.WALKING_SPEED;

        int currentX = (int)lerp(oldX*Constants.TILE_SIZE, x*Constants.TILE_SIZE);
        int currentY = (int)lerp(oldY*Constants.TILE_SIZE, y*Constants.TILE_SIZE);
        //System.out.println("currentX: "+currentX+ " currentY: "+currentY);
        renderPosition = new Position(currentX, currentY);
        if(isMoving >= 1){
            isMoving = -1;
        }
    }

    public void moveToPosition(Position newPosition){
        oldX = x;
        oldY = y;
        x = newPosition.getX();
        y = newPosition.getY();
        isMoving = 0;
    }

    public float lerp(float start, float end){
        return start + isMoving * (end - start);
    }

    public int getSpeed(){
        return speed;
    }

    public void setSpeed(int i) {
        this.speed = i;
    }

    public int getActionPoints(){
        return actionPoints;
    }

    public void modifyActionPoints(int change){
        this.actionPoints +=change;
    }

    public void waitTurn() {
        this.actionPoints = 0;

    }
}
