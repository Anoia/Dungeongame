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

        //remove empty nodes (because whitespace)
        XOMUtil.Normalizer.STRIP.normalize(doc);

    }

    public Weapon createNewWeapon(int level){

        Node levelNode = doc.query("/Weapons/Weights/Level"+level).get(0);
        Weapon weapon = getWeaponWithType(levelNode);
        setWeaponMaterial(levelNode, weapon);
        setWeaponPrefix(levelNode, weapon);
        setWeaponSuffix(levelNode, weapon);
        System.out.println(weapon.toString()+"\n");

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
        Weapon w = new Weapon(name, baseDmg, accuracy, speed, dmgRange, range);
        w.setSpriteName(typeNode.getChild(6).getValue());
        return w;

    }

    private void setWeaponMaterial(Node levelNode, Weapon weapon){
        Nodes materialsInLevel = levelNode.query("Material");
        String randomMaterialName = getRandomElement(materialsInLevel);

        Node materialNode = doc.query("/Weapons/Elements/Material/"+randomMaterialName).get(0);
        String name = materialNode.getChild(0).getValue();
        int baseDmgBonus = Integer.parseInt(materialNode.getChild(1).getValue());
        weapon.applyMaterial(name, baseDmgBonus);
    }

    private void setWeaponPrefix(Node levelNode, Weapon weapon){
        Nodes prefixesInLevel = levelNode.query("Prefix");
        String randomPrefixName = getRandomElement(prefixesInLevel);

        Node prefixNode = doc.query("/Weapons/Elements/Prefix/"+randomPrefixName).get(0);
        String name = prefixNode.getChild(0).getValue();
        int dmgRangeBonus = Integer.parseInt(prefixNode.getChild(1).getValue());
        weapon.applyPrefix(name, dmgRangeBonus);
    }

    private void setWeaponSuffix(Node levelNode, Weapon weapon){
        Nodes suffixesInLevel = levelNode.query("Suffix");
        String randomSuffix = getRandomElement(suffixesInLevel);
        Node suffixNode = doc.query("/Weapons/Elements/Suffix/"+randomSuffix).get(0);
        String name = suffixNode.getChild(0).getValue();
        String effect = suffixNode.getChild(1).getValue();
        if(!name.equals(""))weapon.applySuffix(name, effect);

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

        private String getName() {
            return name;
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
