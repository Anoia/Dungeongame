package com.stuckinadrawer.dungeongame.effects;

import com.stuckinadrawer.dungeongame.actors.Actor;

public abstract class Effect {
    private Actor affectedActor;

    public abstract void applyEffect();

    public abstract void removeEffect();
}
