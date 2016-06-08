package com.jbion.ffxiiicalculator;

import com.jbion.ffxiiicalculator.model.Component;
import com.jbion.ffxiiicalculator.model.ComponentPool;
import com.jbion.ffxiiicalculator.model.ComponentSequence;
import com.jbion.ffxiiicalculator.model.ItemInstance;

public class ExperienceOptimizer {

    private final ComponentPool components;

    private final ItemInstance item;

    private final int targetExp;

    private final ComponentSequence sequence;

    private int sequenceExp;

    private ComponentSequence optimalSequence;

    private int optimalExp;

    private ExperienceOptimizer(ComponentPool components, ItemInstance itemToUpgrade, int targetExp) {
        this.components = components;
        this.item = itemToUpgrade;
        this.targetExp = targetExp;
        this.sequence = new ComponentSequence();
        this.sequenceExp = itemToUpgrade.getExperience();
        this.optimalSequence = new ComponentSequence();
        this.optimalExp = itemToUpgrade.getExperience();
    }

    public static ComponentSequence optimizeExp(ComponentPool components, ItemInstance itemToUpgrade, int targetExp) {
        System.out.println("** Starting optimization **");
        System.out.println("Current = " + itemToUpgrade.getExperience() + "xp");
        System.out.println("Target  = " + targetExp + "xp");
        ExperienceOptimizer optimizer = new ExperienceOptimizer(components, itemToUpgrade, targetExp);
        optimizer.optimize("");
        System.out.println(optimizer.optimalSequence);
        return optimizer.optimalSequence;
    }

    private void optimize(String indent) {
        for (Component comp : components.distinct()) {
            if (comp.getType() == Component.Type.CATALYST) {
                System.out.println(indent + "Skipping catalyst");
                continue; // skip catalysts
            }
            int availableCount = components.getCount(comp);
            for (int count = availableCount; count > 0; count--) {
                //System.out.println(String.format(indent + "Trying with % 2d %s", count, comp.getName()));

                // use the component
                components.remove(comp, count);
                int oldExp = item.getExperience();
                int oldBonus = item.getBonusPoints();
                item.addExperience(comp.getExperience(item.getType().getRank()) * count);
                item.addBonusPoints(comp.getBonusPoints() * count);

                sequence.add(comp, count);

                sequenceExp = item.getExperience();

                boolean keepGoing = updateOptimalSequence();
                if (keepGoing) {
                    optimize(indent + " ");
                } else {
                    System.out.println(String.format(indent + "Target reached with %dxp (wasted %dxp)", sequenceExp,
                            sequenceExp - targetExp));
                }

                // rollback comonent use
                item.setBonusPoints(oldBonus);
                item.setExperience(oldExp);

                sequence.removeLastGroup();
                sequenceExp = item.getExperience();
                components.add(comp, count);
            }
        }
        //System.out.println(indent + "No more components to try.");
    }

    private boolean updateOptimalSequence() {

        final boolean currentSeqReachedTarget = sequenceExp >= targetExp;
        final boolean optimalSeqReachedTarget = optimalExp >= targetExp;

        final boolean newOptimalSeqFound = (!optimalSeqReachedTarget && currentSeqReachedTarget)
                || (optimalSeqReachedTarget && currentSeqReachedTarget && sequence.getTotalValue() < optimalSequence
                        .getTotalValue())
                || (!optimalSeqReachedTarget && !currentSeqReachedTarget && sequenceExp > optimalExp);

        if (newOptimalSeqFound) {
            System.out.println(String.format("New optimum found, with %dxp (wasted %dxp)\n%s", sequenceExp, sequenceExp
                    - targetExp, sequence));
            optimalSequence = new ComponentSequence();
            optimalSequence.addAll(sequence);
            optimalExp = sequenceExp;
        }

        return !currentSeqReachedTarget;
    }
}
