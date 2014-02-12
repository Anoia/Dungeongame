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

    private Level map;
    public int playerLevel = 1;
    public int currentXP = 0;
    public int XPToNextLevel = 100;
    public Slider healthbar;
    public Slider XPBar;




    public Player(int x, int y) {
        super(x, y);
        spriteName = "char_player";
        strength = 10;
        dmgRange = 10;
        maxHP = 25;
        currentHP = maxHP;
        viewDistance = 8;
    }




    @Override
    public void attack(Actor opponent){
        int dmg = Utils.random(dmgRange);
        dmg = dmg + dmg * (strength - baseStrength)/10;
        System.out.println(opponent.getSpriteName() + " taking "+dmg + " damage from "+spriteName);

        if(dmg == 0){
            //miss
            TextAnimation tA = new TextAnimation(opponent.x, opponent.y, "miss", Color.YELLOW);
            map.textAnimations.add(tA);
        }else{
            //red
            TextAnimation tA = new TextAnimation(opponent.x, opponent.y, dmg+"", Color.RED);
            map.textAnimations.add(tA);
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
                map.textAnimations.add(tA);
            }else{
                //orange
                TextAnimation tA = new TextAnimation(x, y, dmg+"", Color.ORANGE);
                map.textAnimations.add(tA);
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
                if(e.dead){
                    currentXP +=e.XPRewarded*2;

                    if(currentXP >= XPToNextLevel){
                        levelUP();
                    } else{
                        XPBar.setValue(currentXP);
                    }
                }
            } else{
                x = newPos.getX();
                y = newPos.getY();
                map.updateFOV();
            }

            return true;
        }else{
            return false;
        }


    }

    private Enemy getEnemyOnNewPos(Position newPos) {
        for(Enemy e: map.getEnemies()){
            if(!e.dead && newPos.equals(e.getPosition())){
                System.out.println("ENEMY");
                return e;
            }
        }
        return null;
    }


    public void setMap(Level map) {
        this.map = map;
    }

    public void levelUP(){
        playerLevel ++;
        strength++;
        maxHP += 5;
        currentHP = maxHP;
        healthbar.setRange(0, maxHP);
        healthbar.setValue(currentHP);
        currentXP = currentXP-XPToNextLevel;
        XPToNextLevel = XPToNextLevel +  (int)(XPToNextLevel * 0.25);
        XPBar.setRange(0, XPToNextLevel);
        XPBar.setValue(currentXP);
        System.out.println("LevelUP " + " xp to next level: "+XPToNextLevel);
        System.out.println("Level: " + playerLevel);
        System.out.println("HP: " + maxHP );
        System.out.println("Strength: "+strength);
    }
}
