package com.stuckinadrawer.dungeongame.items;

import com.stuckinadrawer.dungeongame.effects.Effect;

public class Weapon extends Item{
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

    public void applyMaterial(String materialName, int baseDamageBonus){
        this.baseDamage += baseDamageBonus;
        this.setName(materialName + " " + this.getName());
    }

    public void applyPrefix(String prefixName, int dmgRangeBonus){
        this.damageRange += dmgRangeBonus;
        this.setName(prefixName + " " + this.getName());
    }

    public void applySuffix(String suffixName, Effect effect){
        this.setName(this.getName() + " "+suffixName);
    }

    @Override
    public String toString(){
        return getName()+": \n BaseDmg "+baseDamage+", Accuracy "+ accuracy+", Speed "+attackSpeed+", DamageRange "+damageRange+", Weaponrange "+range;
    }
}
