package com.stuckinadrawer.dungeongame.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.stuckinadrawer.dungeongame.Level;
import com.stuckinadrawer.dungeongame.Position;
import com.stuckinadrawer.dungeongame.Utils;
import com.stuckinadrawer.dungeongame.actors.enemies.Enemy;
import com.stuckinadrawer.dungeongame.render.TextAnimation;

import java.util.LinkedList;

public class Player extends Actor {
    public int strength;
    public int baseStrength = 8;
    public LinkedList<Position> movementPath = null;
    private Level level;
    public Slider healthbar;

    public Player(int x, int y) {
        super(x, y);
        spriteName = "char_player";
        strength = 10;
        dmgRange = 10;
        maxHP = 25;
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

        if(dmg == 0){
            //miss
            TextAnimation tA = new TextAnimation(opponent.x, opponent.y, "miss", Color.YELLOW);
            level.textAnimations.add(tA);
        }else{
            //red
            TextAnimation tA = new TextAnimation(opponent.x, opponent.y, dmg+"", Color.RED);
            level.textAnimations.add(tA);
        }


        opponent.takeDmg(dmg);
    }

    @Override
    public void takeDmg(int dmg){
        if(!dead){
            currentHP -= dmg;
            System.out.println(spriteName + " has "+currentHP + " HP left");
            if(dmg == 0){
                //dodge
                TextAnimation tA = new TextAnimation(x, y, "dodge", Color.YELLOW);
                level.textAnimations.add(tA);
            }else{
                //orange
                TextAnimation tA = new TextAnimation(x, y, dmg+"", Color.ORANGE);
                level.textAnimations.add(tA);
            }

            if(currentHP <= 0){
                die();
                System.out.println(spriteName + " died!");
                healthbar.setValue(0);
            }else{
                healthbar.setValue(currentHP);
            }
        }
    }

    public boolean action() {

        if(!dead && movementPath != null && !movementPath.isEmpty()){

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
            if(!e.dead && newPos.equals(e.getPosition())){
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
