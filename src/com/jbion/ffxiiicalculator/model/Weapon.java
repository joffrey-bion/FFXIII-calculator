package com.jbion.ffxiiicalculator.model;

public class Weapon extends UpgradableItem {

    private final Character character;

    public Weapon(String name, int rank, int sellPrice, Integer buyPrice, Shop shopToBuy, Integer chapterAvailability,
            int expFirstLevel, int expIncrement, int expToMax, int maxLevel, Component catalyst, Character character) {
        super(name, rank, sellPrice, buyPrice, shopToBuy, chapterAvailability, expFirstLevel, expIncrement, expToMax,
                maxLevel, catalyst);
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }

}
