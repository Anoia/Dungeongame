package com.stuckinadrawer.dungeongame.old.systems;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.stuckinadrawer.dungeongame.old.components.AI;

public class AISystem extends EntityProcessingSystem{

    public AISystem(){
        super(Aspect.getAspectForAll(AI.class));
    }

    @Override
    protected void process(Entity e) {
        System.out.println("processing AI");
    }


    @Override
    protected boolean checkProcessing() {
        return false;
    }
}
