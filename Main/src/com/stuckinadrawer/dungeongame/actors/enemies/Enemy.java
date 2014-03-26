package com.stuckinadrawer.dungeongame.actors.enemies;

import com.stuckinadrawer.dungeongame.Level;
import com.stuckinadrawer.dungeongame.util.Position;
import com.stuckinadrawer.dungeongame.actors.Actor;
import com.stuckinadrawer.dungeongame.actors.Player;

import static java.lang.Math.abs;


public abstract class Enemy extends Actor {

    //public int XPRewarded;

    public Enemy(int x, int y) {
        super(x, y);


    }

    public void doTurn(Level level ){
        Player p = level.getPlayer();
        if(playerIsCloseBy(p) &&!dead){
            movementPath = null;
            p.movementPath = null;
            attack(p);
        }else if(level.isInLOS(new Position(x, y), p.getPosition(), viewDistance)){
            level.findPath(this, p.getPosition());
            if(movementPath!= null && !movementPath.isEmpty()){
                Position newPos = movementPath.pop();
                if(level.isWalkable(newPos.getX(), newPos.getY())){
                    System.out.println(getSpriteName() + " moving from "+x+" "+y+" to "+newPos.getX()+" "+newPos.getY());
                    oldX = x;
                    oldY = y;
                    x = newPos.getX();
                    y = newPos.getY();
                    isMoving = 0;
                }

            }
        }
    }

    private boolean playerIsCloseBy(Player p) {
        int distance = abs(p.getPosition().getX() - x) + abs(p.getPosition().getY()-y);
        return (distance == 1);
    }
}
