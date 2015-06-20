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

        int targetExp = computeTargetExp(itemToUpgrade);

        ComponentSequence sequence = optimizeExp(inventory, itemToUpgrade, targetExp);
        if (sequence.getExpReached() < targetExp) {
            int missingExp = targetExp - sequence.getExpReached();
            getItemsToBuy(missingExp);
        }

        return upgradePlan;
    }

    private static ComponentPool removeComponentsToSell(ComponentPool inventory, int targetRank) {
        // TODO Auto-generated method stub
        return null;
    }

    private static ComponentPool getItemsToBuy(int experience) {
        // TODO Auto-generated method stub
        return null;
    }

    private static int computeTargetExp(ItemInstance itemToUpgrade) {
        // TODO Auto-generated method stub
        return 0;
    }

    private static ComponentSequence optimizeExp(ComponentPool components, ItemInstance itemInstance, int targetExp) {
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
                components.remove(comp, count);
                int oldExp = itemInstance.getExperience();
                int oldBonus = itemInstance.getBonusPoints();
                itemInstance.addExperience(comp.getExperience(itemInstance.getType().getRank()) * count);
                itemInstance.addBonusPoints(comp.getBonusPoints() * count);

                ComponentSequence optimalRemainderSeq = optimizeExp(components, itemInstance, targetExp);
                int exp = optimalRemainderSeq.getExpReached();
                boolean targetReached = exp >= targetExp;

                boolean newOptimalSeqFound = (currentOptimalSeq == null)
                        || (targetReachedByOpt && targetReached && exp < optimalExp)
                        || (!targetReachedByOpt && targetReached)
                        || (!targetReachedByOpt && !targetReached && exp > optimalExp);

                if (newOptimalSeqFound) {
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
