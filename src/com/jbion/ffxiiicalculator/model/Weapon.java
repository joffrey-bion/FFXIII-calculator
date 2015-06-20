package com.jbion.ffxiiicalculator.model;

public class Weapon extends UpgradableItem {

    private final GameCharacter character;

    public Weapon(String name, int rank, int sellPrice, Integer buyPrice, Shop shopToBuy, Integer chapterAvailability,
            int expFirstLevel, int expIncrement, int expToMax, int maxLevel, Component catalyst, Weapon evolution,
            GameCharacter character) {
        super(name, rank, sellPrice, buyPrice, shopToBuy, chapterAvailability, expFirstLevel, expIncrement, expToMax,
                maxLevel, catalyst, evolution);
        this.character = character;
    }

    public GameCharacter getCharacter() {
        return character;
    }

}
