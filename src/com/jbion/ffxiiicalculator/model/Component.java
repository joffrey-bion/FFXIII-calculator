package com.jbion.ffxiiicalculator.model;

public class Component extends ItemType {

    public static enum Type {
        ORGANIC,
        SYNTHETIC,
        CATALYST;
    }

    private final Type type;

    private final int[] expPerRank;

    private final int multiplierPoints;

    public Component(String name, int rank, int[] expPerRank, int multiplierPoints, int sellPrice) {
        this(name, rank, expPerRank, multiplierPoints, sellPrice, null, null, null, null);
    }

    public Component(String name, int rank, int[] expPerRank, int multiplierPoints, int sellPrice, Integer buyPrice,
            Type type, Shop shopToBuy, Integer chapterAvailability) {
        super(name, rank, sellPrice, buyPrice, shopToBuy, chapterAvailability);
        this.expPerRank = expPerRank;
        this.multiplierPoints = multiplierPoints;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public boolean isOrganic() {
        return type == Type.ORGANIC;
    }

    public boolean isSynthetic() {
        return type == Type.SYNTHETIC;
    }

    public int getExperience(int itemRank) {
        return expPerRank[itemRank - 1];
    }

    public int getBonusPoints() {
        return multiplierPoints;
    }

    public double getBonusRatio() {
        Integer price = getBuyPrice();
        if (price == null) {
            return 0;
        }
        return (double) multiplierPoints / (double) price;
    }

}
