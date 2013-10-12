package com.stuckinadrawer.dungeongame.components;

import com.artemis.Component;

public class Position extends Component {
    private int x;
    private int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Position(){
        this(0, 0);
    }

    public void set(Position pos){
        this.x = pos.x;
        this.y = pos.y;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString(){
        return "("+this.getX()+","+this.getY()+")";
    }
}
