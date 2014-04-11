package com.stuckinadrawer.dungeongame.items;

import com.stuckinadrawer.dungeongame.util.Utils;

public class LootGenerator {
    WeaponGenerator weaponGenerator;

    public LootGenerator(){
        weaponGenerator = new WeaponGenerator();
    }

    public Item generateLoot(){
        int rand = Utils.random(2);
        Item item = null;
        switch(rand){
            case 0:
                item = weaponGenerator.createNewWeapon(1);
                break;
            case 1:
                item = new Ring("The Ring");
                break;
            case 2:
                item = new Chestpiece("Awesome Shirt");
                break;
        }
        if(item == null){
            item = weaponGenerator.createNewWeapon(1);
        }
        return item;
    }

    public Weapon generateWeapon(){
        return weaponGenerator.createNewWeapon(1);
    }
}
