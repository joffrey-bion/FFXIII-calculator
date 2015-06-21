package com.jbion.ffxiiicalculator.model;

public class UpgradableItem extends Item {

    private final int expFirstLevel;

    private final int expIncrement;

    private final int expToMax;

    private final int maxLevel;

    private final Component catalyst;

    private final UpgradableItem evolution;

    private final Integer evolutionLevel;

    private final String synthesizedAbility;

    private final String passiveAbility;

    public UpgradableItem(String name, int rank, int sellPrice, Integer buyPrice, Shop shopToBuy,
            Integer chapterAvailability, int expFirstLevel, int expIncrement, int expToMax, int maxLevel,
            Component catalyst, UpgradableItem evolution, Integer evolutionLevel, String synthesizedAbility,
            String passiveAbility) {
        super(name, rank, sellPrice, buyPrice, shopToBuy, chapterAvailability);
        this.expFirstLevel = expFirstLevel;
        this.expIncrement = expIncrement;
        this.expToMax = expToMax;
        this.maxLevel = maxLevel;
        this.catalyst = catalyst;
        this.evolution = evolution;
        this.evolutionLevel = evolutionLevel;
        this.synthesizedAbility = synthesizedAbility;
        this.passiveAbility = passiveAbility;
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

    public int getMaxLevel() {
        return maxLevel;
    }

    public Component getCatalyst() {
        return catalyst;
    }

    public UpgradableItem getEvolution() {
        return evolution;
    }

    public Integer getEvolutionLevel() {
        return evolutionLevel;
    }

    public String getSynthesizedAbility() {
        return synthesizedAbility;
    }

    public String getPassiveAbility() {
        return passiveAbility;
    }
}
