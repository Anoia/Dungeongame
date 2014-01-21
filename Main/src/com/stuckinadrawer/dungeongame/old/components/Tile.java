package com.stuckinadrawer.dungeongame.old.components;

import com.artemis.Component;
import com.artemis.Entity;

public class Tile extends Component {
    private Entity item = null;
    private Entity actor = null;



    public Entity getActor() {
        return actor;
    }

    public void setActor(Entity actor) {
        this.actor = actor;
    }

    public Entity getItem() {
        return item;
    }

    public void setItem(Entity item) {
        this.item = item;
    }
}
