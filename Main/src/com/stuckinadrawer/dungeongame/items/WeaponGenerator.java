package com.stuckinadrawer.dungeongame.items;

import com.badlogic.gdx.Gdx;
import nu.xom.*;

import java.io.IOException;

public class WeaponGenerator {

    private Document doc;

    public WeaponGenerator(){

    try{
        Builder parser = new Builder();
        doc = parser.build(Gdx.files.internal("data/weapons.xml").file());
    } catch (ValidityException e) {
        e.printStackTrace();
    } catch (ParsingException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }

    Element e = doc.getRootElement();
        System.out.println(e.getChild(0).getValue());

    }

    public Weapon createNewWeapon(){
        Weapon w ;

        return null;
    }

}
