package com.stuckinadrawer.dungeongame.actors;

import com.stuckinadrawer.dungeongame.Level;
import com.stuckinadrawer.dungeongame.Position;
import com.stuckinadrawer.dungeongame.Utils;
import com.stuckinadrawer.dungeongame.actors.enemies.Enemy;

import java.util.LinkedList;

public class Player extends Actor {
    public int strength;
    public int baseStrength = 8;
    public LinkedList<Position> movementPath = null;
    private Level level;

    public Player(int x, int y) {
        super(x, y);
        spriteName = "char_player";
        strength = 8;
        dmgRange = 6;
        maxHP = 18;
        currentHP = maxHP;
    }


    public void setMovementPath(LinkedList<Position> movementPath) {
        this.movementPath = movementPath;
    }

    @Override
    public void attack(Actor opponent){
        int dmg = Utils.random(dmgRange);
        dmg = dmg + dmg * (strength - baseStrength)/10;
        System.out.println(opponent.getSpriteName() + " taking "+dmg + " damage from "+spriteName);
        opponent.takeDmg(dmg);
    }

    public boolean action() {

        if(movementPath != null && !movementPath.isEmpty()){

            Position newPos = movementPath.pop();


            Enemy e = getEnemyOnNewPos(newPos);
            if(e != null){
                attack(e);
            } else{
                x = newPos.getX();
                y = newPos.getY();
            }

            return true;
        }else{
            return false;
        }


    }

    private Enemy getEnemyOnNewPos(Position newPos) {
        for(Enemy e: level.getEnemies()){
            if(newPos.equals(e.getPosition())){
                System.out.println("ENEMY");
                return e;
            }
        }
        return null;
    }


    public void setLevel(Level level) {
        this.level = level;
    }
}
