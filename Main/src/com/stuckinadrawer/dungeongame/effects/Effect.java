package com.stuckinadrawer.dungeongame.effects;

import com.stuckinadrawer.dungeongame.actors.Actor;

/**
 * These are Effects that influence an Actor in some way, can be passive (improved armor) or ticking (poison, deals damage every turn);   actually, can be both, doesn't matter
 */

public abstract class Effect {
    private Actor affectedActor;    // The actor that is affected
    private int duration;           // The remaining duration of the effect in turns

    public Effect(Actor affectedActor, int duration){
        this.affectedActor = affectedActor;
        this.duration = duration;
        applyEffect();

    }

    public abstract void applyEffect();

    public abstract void removeEffect();

    public void tick(){
        this.duration--;
        if(duration == 0){
            removeEffect();
        }
    }
}
