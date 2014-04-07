package com.stuckinadrawer.dungeongame.actors;

import com.stuckinadrawer.dungeongame.items.Item;
import com.stuckinadrawer.dungeongame.items.Weapon;
import com.stuckinadrawer.dungeongame.util.Constants;
import com.stuckinadrawer.dungeongame.util.Position;
import com.stuckinadrawer.dungeongame.util.Utils;

import java.util.ArrayList;

public class Player extends Actor {


    public int playerLevel = 1;
    public int currentXP = 0;
    public int XPToNextLevel = 100;

    private Weapon equippedWeapon;

    private ArrayList<Item> inventory;



    public Player(int x, int y) {
        super(x, y);
        renderPosition = new Position(x* Constants.TILE_SIZE, y* Constants.TILE_SIZE);
        setSpriteName("char_player");
        setStrength(10);
        setPerception(6);
        setEndurance(5);
        setCharisma(5);
        setIntelligence(5);
        setAgility(5);
        setLuck(5);
        dmgRange = 10;
        currentHP = maxHP;
        inventory = new ArrayList<Item>();
    }

    public Player(){
        this(0, 0);
    }

    @Override
    public int getAttackDamage(){
        int dmg = Utils.random(equippedWeapon.getBaseDamage(), equippedWeapon.getBaseDamage()+ equippedWeapon.getDamageRange());
        dmg = dmg + dmg * (getStrength() - baseStrength)/10;
        dmg = (Utils.random(8)>0)? dmg : 0;
        return dmg;
    }


    @Override
    public boolean takeDmg(int dmg){
            currentHP -= dmg;
            System.out.println(getSpriteName() + " has "+currentHP + " HP left");

            if(currentHP <= 0){
                die();
                currentHP = 0;
                System.out.println(getSpriteName() + " died!");
            }

        return dead;
    }


    public void setEquippedWeapon(Weapon equippedWeapon){
        this.equippedWeapon = equippedWeapon;
    }

    public Weapon getEquippedWeapon(){
        return equippedWeapon;
    }

    public ArrayList<Item> getInventory(){
        return inventory;
    }

    public void addToInventory(Item item){
        inventory.add(item);
    }

    public void removeFromInventory(Item item){
        inventory.remove(item);
    }

    public void levelUP(){
        this.playerLevel ++;
        changeStrength(1);
        changeEndurance(1);
        changePerception(1);
        currentHP = maxHP;
        currentXP = currentXP-XPToNextLevel;
        XPToNextLevel = XPToNextLevel +  (int)(XPToNextLevel * 0.25);
        System.out.println("LevelUP " + " xp to next level: "+XPToNextLevel);
        System.out.println("Level: " + playerLevel);
        System.out.println("HP: " + maxHP );
        System.out.println("Strength: "+getStrength());
    }

    /**
     *
     * @param amount amount of xp gained
     * @return true if player leveled up
     */
    public boolean earnXP(int amount) {
        currentXP += amount;
        return currentXP >= XPToNextLevel;

    }
}
