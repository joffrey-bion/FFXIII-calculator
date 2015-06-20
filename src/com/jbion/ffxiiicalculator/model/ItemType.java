package com.jbion.ffxiiicalculator.model;

public class ItemType {

    private final String name;

    private final int rank;

    private final int sellPrice;

    private final Integer buyPrice;

    private final Shop shopToBuy;

    private final Integer chapterAvailability;

    public ItemType(String name, int rank, int sellPrice, Integer buyPrice, Shop shopToBuy, Integer chapterAvailability) {
        this.name = name;
        this.rank = rank;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.shopToBuy = shopToBuy;
        this.chapterAvailability = chapterAvailability;
    }

    public String getName() {
        return name;
    }

    public int getRank() {
        return rank;
    }

    public Integer getBuyPrice() {
        return buyPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public Shop getShopToBuy() {
        return shopToBuy;
    }

    public Integer getChapterAvailability() {
        return chapterAvailability;
    }
}
