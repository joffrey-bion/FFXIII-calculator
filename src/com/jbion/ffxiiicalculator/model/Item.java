package com.jbion.ffxiiicalculator.model;


public class Item {

    private final ItemType type;

    private int experience = 0;

    private int bonusPoints = 0;

    //    private Stack<Integer> experienceHistory = new Stack<>();
    //
    //    private Stack<Integer> bonusPointsHistory = new Stack<>();

    public Item(ItemType type, int level, int experienceInLevel, int bonusPoints) {
        this.type = type;
        this.bonusPoints = bonusPoints;
        computeInitialExperience(level, experienceInLevel);
    }

    private void computeInitialExperience(int level, int experienceInLevel) {
        // TODO
        experience = 0;
    }

    public ItemType getType() {
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

    //    public int upgradeWith(Component component, int count) {
    //        experienceHistory.push(experience);
    //        int expGain = (int) Math.round(component.getExperience(type.getRank()) * count * getExpMultiplier());
    //        experience += expGain;
    //        bonusPointsHistory.push(bonusPoints);
    //        bonusPoints += component.getBonusPoints() * count;
    //        if (bonusPoints < 0) {
    //            bonusPoints = 0;
    //        }
    //        return expGain;
    //    }
    //
    //    public void rollbackLastUpgrade() {
    //        if (bonusPointsHistory.isEmpty()) {
    //            throw new IllegalStateException("empty history, no last upgrade to cancel");
    //        }
    //        bonusPoints = bonusPointsHistory.pop();
    //        experience = experienceHistory.pop();
    //    }

    public double getExpMultiplier() {
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
