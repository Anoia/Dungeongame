package com.stuckinadrawer.dungeongame.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.stuckinadrawer.dungeongame.Constants;
import com.stuckinadrawer.dungeongame.Level;
import com.stuckinadrawer.dungeongame.Position;
import com.stuckinadrawer.dungeongame.Utils;
import com.stuckinadrawer.dungeongame.actors.enemies.Enemy;
import com.stuckinadrawer.dungeongame.render.TextAnimation;

public class Player extends Actor {


    private Level map;
    public int playerLevel = 1;
    public int currentXP = 0;
    public int XPToNextLevel = 100;
    public Slider healthbar;
    public Slider XPBar;






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
    }




    @Override
    public void attack(Actor opponent){
        int dmg = Utils.random(dmgRange);
        dmg = dmg + dmg * (getStrength() - baseStrength)/10;
        System.out.println(opponent.getSpriteName() + " taking "+dmg + " damage from "+getSpriteName());

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

        if(opponent.dead){
            currentXP += opponent.XPRewarded*2;

            if(currentXP >= XPToNextLevel){
                levelUP();
            } else{
                XPBar.setValue(currentXP);
            }
        }
    }

    @Override
    public void takeDmg(int dmg){
        if(!dead){
            currentHP -= dmg;
            System.out.println(getSpriteName() + " has "+currentHP + " HP left");
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
                System.out.println(getSpriteName() + " died!");
                healthbar.setValue(0);
            }else{
                healthbar.setValue(currentHP);
            }
        }
    }

    public boolean action() {

        if(!dead && movementPath != null && !movementPath.isEmpty()){

            Position newPos = movementPath.pop();


            Enemy e = map.getEnemyOnPos(newPos);
            if(e != null){
                attack(e);

            } else{
                oldX = x;
                oldY = y;
                x = newPos.getX();
                y = newPos.getY();
                map.updateFOV();
                isMoving = 0;
            }

            return true;
        }else{
            return false;
        }


    }







    public void setMap(Level map) {
        this.map = map;
    }

    public void levelUP(){
        this.playerLevel ++;
        changeStrength(1);
        changeEndurance(1);
        changeEndurance(1);
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
        System.out.println("Strength: "+getStrength());
    }
}
