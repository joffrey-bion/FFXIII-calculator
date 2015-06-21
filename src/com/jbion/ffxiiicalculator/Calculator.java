package com.jbion.ffxiiicalculator;

import com.jbion.ffxiiicalculator.model.Component;
import com.jbion.ffxiiicalculator.model.ComponentPool;
import com.jbion.ffxiiicalculator.model.ComponentSequence;
import com.jbion.ffxiiicalculator.model.ItemInstance;
import com.jbion.ffxiiicalculator.model.UpgradePlan;

public class Calculator {

    public static UpgradePlan upgrade(ComponentPool inventory, ItemInstance itemToUpgrade) {
        UpgradePlan upgradePlan = new UpgradePlan();

        int targetRank = itemToUpgrade.getType().getRank();

        ComponentPool componentsToSell = removeComponentsToSell(inventory, targetRank);
        upgradePlan.setComponentsToSell(componentsToSell);

        int targetExp = itemToUpgrade.getType().getExpToMax();

        ComponentSequence sequence = optimizeExpRec(inventory, itemToUpgrade, targetExp);
        ComponentPool componentsToBuy = new ComponentPool();
        if (sequence.getExpReached() < targetExp) {
            int missingExp = targetExp - sequence.getExpReached();
            componentsToBuy = getItemsToBuy(missingExp);
            inventory.addAll(componentsToBuy);
            sequence = optimizeExpRec(inventory, itemToUpgrade, targetExp);
        }
        upgradePlan.setComponentsToUse(sequence);
        inventory.removeAll(sequence);
        componentsToBuy.removeAll(inventory);
        upgradePlan.setComponentsToBuy(componentsToBuy);
        return upgradePlan;
    }

    private static ComponentPool removeComponentsToSell(ComponentPool inventory, int targetRank) {
        // TODO Auto-generated method stub
        return new ComponentPool();
    }

    private static ComponentPool getItemsToBuy(int experience) {
        // TODO Auto-generated method stub
        return new ComponentPool();
    }

    private static ComponentSequence optimizeExpRec(ComponentPool components, ItemInstance itemInstance, int targetExp) {
        ComponentSequence currentOptimalSeq = new ComponentSequence();
        int optimalExp = itemInstance.getExperience();
        currentOptimalSeq.setExpReached(optimalExp);
        boolean targetReachedByOpt = optimalExp >= targetExp;

        if (targetReachedByOpt) {
            return currentOptimalSeq;
        }

        for (Component comp : components.distinct()) {
            if (comp.getType() == Component.Type.CATALYST) {
                continue; // skip catalysts
            }
            int availableCount = components.getCount(comp);
            for (int count = availableCount; count > 0; count--) {
                System.out.println(String.format("Trying with % 2d %s", count, comp.getName()));

                components.remove(comp, count);
                int oldExp = itemInstance.getExperience();
                int oldBonus = itemInstance.getBonusPoints();
                itemInstance.addExperience(comp.getExperience(itemInstance.getType().getRank()) * count);
                itemInstance.addBonusPoints(comp.getBonusPoints() * count);

                ComponentSequence optimalRemainderSeq = optimizeExpRec(components, itemInstance, targetExp);
                int exp = optimalRemainderSeq.getExpReached();
                boolean targetReached = exp >= targetExp;

                boolean newOptimalSeqFound = (currentOptimalSeq == null)
                        || (targetReachedByOpt && targetReached && exp < optimalExp)
                        || (!targetReachedByOpt && targetReached)
                        || (!targetReachedByOpt && !targetReached && exp > optimalExp);

                if (newOptimalSeqFound) {
                    System.out.println("New optimum found!");
                    ComponentSequence newOptimalSeq = new ComponentSequence();
                    newOptimalSeq.add(comp, count);
                    newOptimalSeq.addAll(optimalRemainderSeq);
                    newOptimalSeq.setExpReached(exp);
                    optimalExp = exp;
                    targetReachedByOpt = targetReached;
                    currentOptimalSeq = newOptimalSeq;
                }

                itemInstance.setBonusPoints(oldBonus);
                itemInstance.setExperience(oldExp);
                components.add(comp, count);
            }
        }
        return currentOptimalSeq;
    }

}
