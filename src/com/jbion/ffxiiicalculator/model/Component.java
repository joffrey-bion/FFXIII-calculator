package com.jbion.ffxiiicalculator.model;

public class Component extends ItemType {

    public static enum ComponentClass {
        ORGANIC,
        SYNTHETIC;
    }

    private final ComponentClass type;

    private final int[] expPerRank;

    private final int multiplierPoints;

    public Component(String name, int rank, int[] expPerRank, int multiplierPoints, int sellPrice) {
        this(name, rank, expPerRank, multiplierPoints, sellPrice, null, null, null, null);
    }

    public Component(String name, int rank, int[] expPerRank, int multiplierPoints, int sellPrice, Integer buyPrice,
            ComponentClass type, Shop shopToBuy, Integer chapterAvailability) {
        super(name, rank, sellPrice, buyPrice, shopToBuy, chapterAvailability);
        this.expPerRank = expPerRank;
        this.multiplierPoints = multiplierPoints;
        this.type = type;
    }

    public boolean isOrganic() {
        return type == ComponentClass.ORGANIC;
    }

    public boolean isSynthetic() {
        return type == ComponentClass.SYNTHETIC;
    }

    public int getExperience(int itemRank) {
        return expPerRank[itemRank - 1];
    }

    public int getBonusPoints() {
        return multiplierPoints;
    }

}
