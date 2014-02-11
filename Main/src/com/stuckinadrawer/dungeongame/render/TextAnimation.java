package com.stuckinadrawer.dungeongame.render;

import com.badlogic.gdx.graphics.Color;
import com.stuckinadrawer.dungeongame.Constants;

public class TextAnimation{
    public float x;
    public float y;
    public Color color;
    public String text;
    public float alpha;
    public float count;
    public TextAnimation(int x, int y, String text, Color color){
        this.x = x * Constants.TILE_SIZE;
        this.y = y * Constants.TILE_SIZE + Constants.TILE_SIZE;
        this.text = text;
        this. color = color;
        count = 0;
        alpha = 1;
    }

    public void animate(float delta){

        if(count > 0.75){
            alpha -= 0.05;
            //color.set(color.r, color.g, color.b, alpha);
            color = new Color(color.r, color.g, color.b, alpha);
        }

        y += 0.2f;
        count += delta+0.5*delta;
    }
}