package com.jbion.ffxiiicalculator.model;

public class Weapon extends UpgradableItem {

    private final GameCharacter character;

    private final int strengthMin;

    private final int strengthMax;

    private final int magicMin;

    private final int magicMax;

    public Weapon(String name, int rank, int sellPrice, Integer buyPrice, Shop shopToBuy, Integer chapterAvailability,
            int expFirstLevel, int expIncrement, int expToMax, int maxLevel, Component catalyst, Weapon evolution,
            Integer evolutionLevel, String synthesizedAbility, String passiveAbility, GameCharacter character,
            int strengthMin, int strengthMax, int magicMin, int magicMax) {
        super(name, rank, sellPrice, buyPrice, shopToBuy, chapterAvailability, expFirstLevel, expIncrement, expToMax,
                maxLevel, catalyst, evolution, evolutionLevel, synthesizedAbility, passiveAbility);
        this.character = character;
        this.strengthMin = strengthMin;
        this.strengthMax = strengthMax;
        this.magicMin = magicMin;
        this.magicMax = magicMax;
    }

    public GameCharacter getCharacter() {
        return character;
    }

    public int getStrengthMin() {
        return strengthMin;
    }

    public int getStrengthMax() {
        return strengthMax;
    }

    public int getMagicMin() {
        return magicMin;
    }

    public int getMagicMax() {
        return magicMax;
    }

}
