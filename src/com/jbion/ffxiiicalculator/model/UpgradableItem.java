package com.jbion.ffxiiicalculator.model;

public class UpgradableItem extends Item {

    private final int expFirstLevel;

    private final int expIncrement;

    private final int expToMax;

    private final Component catalyst;

    public UpgradableItem(String name, int rank, int sellPrice, Integer buyPrice, Shop shopToBuy,
            Integer chapterAvailability, int expFirstLevel, int expIncrement, int expToMax, Component catalyst) {
        super(name, rank, sellPrice, buyPrice, shopToBuy, chapterAvailability);
        this.expFirstLevel = expFirstLevel;
        this.expIncrement = expIncrement;
        this.expToMax = expToMax;
        this.catalyst = catalyst;
    }

    public int getExpFirstLevel() {
        return expFirstLevel;
    }

    public int getExpIncrement() {
        return expIncrement;
    }

    public int getExpToMax() {
        return expToMax;
    }

    public Component getCatalyst() {
        return catalyst;
    }

}
