package com.stuckinadrawer.dungeongame.util;

import java.io.Serializable;

public class Position implements Serializable{
    private int x;
    private int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof Position) {
            Position pos = (Position) obj;
            return (this.getX() == pos.getX()) && (this.getY() == pos.getY());
        }
        return super.equals(obj);
    }


    @Override
    public int hashCode() {
        long bits = java.lang.Double.doubleToLongBits(getX());
        bits ^= java.lang.Double.doubleToLongBits(getY()) * 31;
        return (((int) bits) ^ ((int) (bits >> 32)));
    }
}
