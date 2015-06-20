package com.jbion.ffxiiicalculator.model;

public class ItemInstance {

    private final UpgradableItem type;

    private int experience = 0;

    private int bonusPoints = 0;

    public ItemInstance(UpgradableItem type, int level, int experienceInLevel, int bonusPoints) {
        this.type = type;
        this.bonusPoints = bonusPoints;
        computeInitialExperience(level, experienceInLevel);
    }

    private void computeInitialExperience(int level, int experienceInLevel) {
        // TODO
        experience = 0;
    }

    public Item getType() {
        return type;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int exp) {
        experience = exp;
    }

    public void addExperience(int exp) {
        experience += (int) Math.round(exp * getExpMultiplier());
    }

    public int getBonusPoints() {
        return bonusPoints;
    }

    public void setBonusPoints(int points) {
        bonusPoints = points;
    }

    public void addBonusPoints(int points) {
        bonusPoints += points;
        if (bonusPoints < 0) {
            bonusPoints = 0;
        }
    }

    private double getExpMultiplier() {
        if (bonusPoints <= 50) {
            return 1;
        } else if (bonusPoints <= 100) {
            return 1.25;
        } else if (bonusPoints <= 200) {
            return 1.5;
        } else if (bonusPoints <= 250) {
            return 1.75;
        } else if (bonusPoints <= 500) {
            return 2;
        } else {
            return 3;
        }
    }
}
