package com.stuckinadrawer.dungeongame.old.components;

import com.artemis.Component;

public class Health extends Component {
    private int health;
    private int maxHealth;

    public Health(int maxHealth){
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
