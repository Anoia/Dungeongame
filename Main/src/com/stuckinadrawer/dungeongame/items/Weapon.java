package com.stuckinadrawer.dungeongame.items;

import com.stuckinadrawer.dungeongame.actors.Player;
import com.stuckinadrawer.dungeongame.effects.Effect;

public class Weapon extends Item implements Equipable{
    private int attackSpeed;
    private int baseDamage;
    private int damageRange;
    private int range;
    private int accuracy;

    public Weapon(String name, int baseDamage, int accuracy, int attackSpeed, int damageRange, int range) {
        super(name);
        this.baseDamage = baseDamage;
        this.accuracy = accuracy;
        this.attackSpeed = attackSpeed;
        this.damageRange = damageRange;
        this.range = range;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public int getDamageRange() {
        return damageRange;
    }

    public void applyMaterial(String materialName, int baseDamageBonus){
        this.baseDamage += baseDamageBonus;
        this.setName(materialName + " " + this.getName());
    }

    public void applyPrefix(String prefixName, int dmgRangeBonus){
        this.damageRange += dmgRangeBonus;
        this.setName(prefixName + " " + this.getName());
    }

    public void applySuffix(String suffixName, String effect){
        this.setName(this.getName() + " "+suffixName);
    }

    @Override
    public String toString(){
        return getName()+": \n BaseDmg "+baseDamage+"\nAccuracy "+ accuracy+"\nSpeed "+attackSpeed+"\nDamageRange "+damageRange+"\nWeaponrange "+range;
    }

    @Override
    public String getDescription(){
        return "BaseDmg "+baseDamage+"\nDamageRange "+damageRange+"\nAccuracy "+ accuracy+"\nSpeed "+attackSpeed+"\nWeaponrange "+range;
    }

    @Override
    public void equip(Player player) {
        if(player.getEquippedWeapon() != null){
            player.addToInventory(player.getEquippedWeapon());
        }
        player.setEquippedWeapon(this);
        player.removeFromInventory(this);
    }

    @Override
    public void unEquip(Player player) {
        player.addToInventory(this);
        player.setEquippedWeapon(null);
    }

    public int getRange() {
        return range;
    }

    public int getSpeed() {
        return attackSpeed;
    }
}
