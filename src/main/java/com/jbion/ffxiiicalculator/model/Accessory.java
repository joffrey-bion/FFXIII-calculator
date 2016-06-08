package com.jbion.ffxiiicalculator.model;

public class Accessory extends UpgradableItem {

    private final Integer bonusMin;

    private final Integer bonusMax;

    public Accessory(String name, int rank, int sellPrice, Integer buyPrice, Shop shopToBuy,
            Integer chapterAvailability, int expFirstLevel, int expIncrement, int expToMax, int maxLevel,
            Component catalyst, Accessory evolution, Integer evolutionLevel, String synthesizedAbility,
            String passiveAbility, Integer bonusMin, Integer bonusMax) {
        super(name, rank, sellPrice, buyPrice, shopToBuy, chapterAvailability, expFirstLevel, expIncrement, expToMax,
                maxLevel, catalyst, evolution, evolutionLevel, synthesizedAbility, passiveAbility);
        this.bonusMin = bonusMin;
        this.bonusMax = bonusMax;
    }

    public Integer getBonusMin() {
        return bonusMin;
    }

    public Integer getBonusMax() {
        return bonusMax;
    }

}
