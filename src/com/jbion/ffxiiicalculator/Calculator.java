package com.jbion.ffxiiicalculator;

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

        ComponentSequence sequence = ExperienceOptimizer.optimizeExp(inventory, itemToUpgrade, targetExp);
        ComponentPool componentsToBuy = new ComponentPool();
        //        if (sequence.getExpReached() < targetExp) {
        //            int missingExp = targetExp - sequence.getExpReached();
        //            componentsToBuy = getItemsToBuy(missingExp);
        //            inventory.addAll(componentsToBuy);
        //            sequence = ExperienceOptimizer.optimizeExp(inventory, itemToUpgrade, targetExp);
        //        }
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

}
