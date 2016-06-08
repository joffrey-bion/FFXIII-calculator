package com.jbion.ffxiiicalculator.model;

public class Component extends Item {

    public enum Type {
        ORGANIC,
        SYNTHETIC,
        CATALYST
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

    public boolean isCatalyst() {
        return type == Type.CATALYST;
    }

    public int getExperience(int itemRank) {
        return expPerRank[itemRank - 1];
    }

    public int getBonusPoints() {
        return multiplierPoints;
    }

    public double getBonusBuyPriceRatio() {
        Integer price = getBuyPrice();
        if (price == null) {
            return 0;
        }
        return (double) multiplierPoints / (double) price;
    }

    public double getBonusSellPriceRatio() {
        return (double) multiplierPoints / (double) getSellPrice();
    }

    public double getExpBuyPriceRatio(int targetRank) {
        Integer price = getBuyPrice();
        if (price == null) {
            return 0;
        }
        return (double) getExperience(targetRank) / (double) price;
    }

    public double getExpSellPriceRatio(int targetRank) {
        return (double) getExperience(targetRank) / (double) getSellPrice();
    }

    @Override
    public String toString() {
        return String.format("%s [%d pts, %d XP, %dgil]", getName(), multiplierPoints, expPerRank[5], getSellPrice());
    }
}
