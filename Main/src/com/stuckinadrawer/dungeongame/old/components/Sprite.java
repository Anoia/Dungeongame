package com.stuckinadrawer.dungeongame.old.components;

import com.artemis.Component;

public class Sprite extends Component {

    public enum Layer{
        DEFAULT,
        TILE,
        ITEM,
        ACTOR,
        EFFECT;

        public int getLayerId(){
            return ordinal();
        }


    }

    private String name;
    private Layer layer;

    public Sprite(String name, Layer layer){
        this.name = name;
        this.layer = layer;
    }



    public Layer getLayer() {
        return layer;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }







}
