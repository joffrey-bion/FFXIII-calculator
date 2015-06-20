package com.jbion.ffxiiicalculator;

import com.jbion.ffxiiicalculator.model.Component;
import com.jbion.ffxiiicalculator.model.ComponentPool;
import com.jbion.ffxiiicalculator.model.ComponentSequence;
import com.jbion.ffxiiicalculator.model.Item;
import com.jbion.ffxiiicalculator.model.UpgradePlan;

public class Calculator {

    public static UpgradePlan upgrade(ComponentPool inventory, Item itemToUpgrade) {
        UpgradePlan upgradePlan = new UpgradePlan();

        int targetRank = itemToUpgrade.getType().getRank();

        ComponentPool componentsToSell = getComponentsToSell(inventory, targetRank);
        upgradePlan.setComponentsToSell(componentsToSell);

        int targetExp = computeTargetExp(itemToUpgrade);

        ComponentSequence sequence = optimizeExp(inventory, itemToUpgrade, targetExp);

        return upgradePlan;
    }

    private static ComponentPool getComponentsToSell(ComponentPool inventory, int targetRank) {
        // TODO Auto-generated method stub
        return null;
    }

    private static int computeTargetExp(Item itemToUpgrade) {
        // TODO Auto-generated method stub
        return 0;
    }

    private static ComponentSequence optimizeExp(ComponentPool components, Item item, int targetExp) {
        if (item.getExperience() >= targetExp) {
            ComponentSequence emptySeq = new ComponentSequence();
            emptySeq.setExpReached(item.getExperience());
            return emptySeq;
        }

        ComponentSequence currentOptimalSeq = null;
        int optimalExp = 0;
        boolean targetReachedByOpt = false;

        for (Component comp : components.distinct()) {
            int availableCount = components.getCount(comp);
            for (int count = availableCount; count > 0; count--) {
                components.remove(comp, count);
                int oldExp = item.getExperience();
                int oldBonus = item.getBonusPoints();
                item.addExperience(comp.getExperience(item.getType().getRank()) * count);
                item.addBonusPoints(comp.getBonusPoints() * count);

                ComponentSequence optimalRemainderSeq = optimizeExp(components, item, targetExp);
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
                    optimalExp = exp;
                    targetReachedByOpt = targetReached;
                    currentOptimalSeq = newOptimalSeq;
                }

                item.setBonusPoints(oldBonus);
                item.setExperience(oldExp);
                components.add(comp, count);

                if (comp.isSynthetic() && exp < targetExp) {
                    break; // skip lower counts if we didn't reach the max
                }
            }
        }

        currentOptimalSeq.setExpReached(optimalExp);
        return currentOptimalSeq == null ? new ComponentSequence() : currentOptimalSeq;
    }
    //    private static ComponentPool optimizeMultiplierPoints(List<ComponentGroup> components, int targetRank,
    //            int currentPoints, int targetPoints) {
    //        if (currentPoints >= targetPoints) {
    //            return new ComponentPool();
    //        }
    //
    //        ComponentPool result = null;
    //        int currentExp = 0;
    //        int currentBonusPoints = 0;
    //        for (ComponentGroup cg : components) {
    //            if (cg.getCount() == 0) {
    //                continue;
    //            }
    //            cg.decrement();
    //
    //            Component type = cg.getType();
    //            int points = currentPoints + type.getBonusPoints();
    //            ComponentPool comps = new ComponentPool();
    //            comps.add(type);
    //            comps.addAll(optimizeMultiplierPoints(components, targetRank, points, targetPoints));
    //
    //            int exp = comps.getExperience(targetRank);
    //            int bonus = currentPoints + comps.getMultiplierPoints();
    //
    //            if (result == null || bonus < currentBonusPoints || (bonus == currentBonusPoints && exp > currentExp)) {
    //                result = comps;
    //                currentExp = exp;
    //                currentBonusPoints = bonus;
    //            }
    //
    //            cg.increment();
    //        }
    //
    //        return result == null ? new ComponentPool() : result;
    //    }
}
