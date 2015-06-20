package com.jbion.ffxiiicalculator.model;

public class Accessory extends UpgradableItem {

    public Accessory(String name, int rank, int sellPrice, Integer buyPrice, Shop shopToBuy,
            Integer chapterAvailability, int expFirstLevel, int expIncrement, int expToMax, int maxLevel,
            Component catalyst, Accessory evolution) {
        super(name, rank, sellPrice, buyPrice, shopToBuy, chapterAvailability, expFirstLevel, expIncrement, expToMax,
                maxLevel, catalyst, evolution);
    }

}
