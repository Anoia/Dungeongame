package com.stuckinadrawer.dungeongame.items;

import com.badlogic.gdx.Gdx;
import com.stuckinadrawer.dungeongame.util.Utils;
import nu.xom.*;
import nux.xom.pool.XOMUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

   //     Element rootElement = doc.getRootElement();

        XOMUtil.Normalizer.STRIP.normalize(doc);

       // Node node = doc.query("/Weapons/Elements/Type/Sword").get(0);
       // System.out.println(node.getValue());
     //   for(int i = 0; i < node.getChildCount(); i++){
      //      String val =  node.getChild(i).getValue();
            //System.out.println("Child "+i+": "+val);
       // }
        //System.out.println("done");
        /*
        Element elements = rootElement.getChildElements("Elements").get(0);
        System.out.println(elements.getValue());
        Element type = elements.getChildElements("Type").get(0);
        System.out.println(type.getValue());
        type = type.getChildElements("Sword").get(0);
        System.out.println("Type: " + type.getValue() + " Base Damage: "+ type.getAttribute("baseDmg").getValue());
        */
    }

    public Weapon createNewWeapon(int level){
        Node levelNode = doc.query("/Weapons/Weights/Level"+level).get(0);
        //System.out.println(levelNode.getValue());
        //Nodes types = levelNode.query("Type");
        //System.out.println(types.get(0).getValue());
        Weapon weapon = getWeaponWithType(levelNode);

        System.out.println(weapon.toString());

        return weapon;
    }

    private Weapon getWeaponWithType(Node levelNode){
        Nodes typesInLevel = levelNode.query("Type");

        String randomTypeName = getRandomElement(typesInLevel);

        Node typeNode = doc.query("/Weapons/Elements/Type/"+randomTypeName).get(0);
        String name = typeNode.getChild(0).getValue();
        int baseDmg = Integer.parseInt(typeNode.getChild(1).getValue());
        int accuracy = Integer.parseInt(typeNode.getChild(2).getValue());
        int speed = Integer.parseInt(typeNode.getChild(3).getValue());
        int dmgRange = Integer.parseInt(typeNode.getChild(4).getValue());
        int range = Integer.parseInt(typeNode.getChild(5).getValue());

        return new Weapon(name, baseDmg, accuracy, speed, dmgRange, range);

    }

    private class ItemWithWeight {
        int weigth;
        String name;

        private ItemWithWeight(int weigth, String name) {
            this.weigth = weigth;
            this.name = name;
        }

        private int getWeigth() {
            return weigth;
        }

        private void setWeigth(int weigth) {
            this.weigth = weigth;
        }

        private String getName() {
            return name;
        }

        private void setName(String name) {
            this.name = name;
        }
    }

    private String getRandomElement(Nodes nodes){

        List<ItemWithWeight> list = new ArrayList<ItemWithWeight>();
        int totalWeightSum = 0;
        for(int i = 0; i<nodes.size(); i++){
            Node type = nodes.get(i);
            String name =  type.getChild(0).getValue();
            int weight = Integer.parseInt(type.getChild(1).getValue());
            totalWeightSum += weight;
            list.add(new ItemWithWeight(weight, name));
        }

        return chooseItemFromList(list, totalWeightSum);
    }

    private String chooseItemFromList(List<ItemWithWeight> list, int totalWeightSum){

        int randomNum = Utils.nextInt(totalWeightSum);
        int sum = 0;

        for(ItemWithWeight currentItem: list){
            if(randomNum >= sum && randomNum < (sum+ currentItem.getWeigth())){
                return currentItem.getName();
            }
            sum += currentItem.getWeigth();
        }
        return null;
    }


}
