package com.stuckinadrawer.dungeongame.actors.enemies;

import com.stuckinadrawer.dungeongame.actors.Actor;
import com.stuckinadrawer.dungeongame.actors.Player;

import static java.lang.Math.abs;


public abstract class Enemy extends Actor {
    public Enemy(int x, int y) {
        super(x, y);


    }

    public void doTurn(Player p ){
        if(playerIsCloseBy(p)){
            p.movementPath = null;
            attack(p);
        }
    }

    private boolean playerIsCloseBy(Player p) {
        int distance = abs(p.getPosition().getX() - x) + abs(p.getPosition().getY()-y);
        return (distance == 1);
    }
}
