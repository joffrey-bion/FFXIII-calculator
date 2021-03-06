package com.jbion.ffxiiicalculator;

import java.util.List;

import com.jbion.ffxiiicalculator.model.Component;
import com.jbion.ffxiiicalculator.model.ComponentPool;
import com.jbion.ffxiiicalculator.model.ComponentSequence;

public class OrganicOptimizer {

    private static final int[] thresholds = {51, 101, 201, 251, 501};

    private final ComponentPool components;

    private final int targetPoints;

    private final ComponentSequence currentSequence;

    private final ComponentSequence optimalSequence;

    private int currentPoints;

    private int optimalPoints;

    private OrganicOptimizer(ComponentPool organicComponents, int currentBonusPoints, int targetBonusPoints) {
        this.components = organicComponents;
        this.targetPoints = targetBonusPoints;
        this.currentPoints = currentBonusPoints;
        this.optimalPoints = currentBonusPoints;
        this.currentSequence = new ComponentSequence();
        this.optimalSequence = new ComponentSequence();
    }

    public static ComponentSequence optimizeMultiplierPoints(ComponentPool organicComponents, int currentBonusPoints) {
        ComponentPool pool = new ComponentPool();
        pool.addAll(organicComponents);
        int currentPoints = currentBonusPoints;
        ComponentSequence optSequence = new ComponentSequence();
        for (int threshold : thresholds) {
            if (currentPoints < threshold) {
                ComponentSequence s = optimizeMultiplierPoints(pool, currentPoints, threshold);
                optSequence.addAll(s);
                currentPoints += s.getTotalBonusPoints();
                pool.removeAll(s);
            }
        }
        return optSequence;
    }

    private static ComponentSequence optimizeMultiplierPoints(ComponentPool organicComponents, int currentPoints,
                                                              int targetPoints) {
        if (currentPoints >= targetPoints) {
            System.out.println("Already reached target, this method shouldn't be called.");
            return new ComponentSequence();
        }

        System.out.println(String.format("Optimizing bonus points, starting at %d, trying to reach %d", currentPoints,
                targetPoints));
        OrganicOptimizer oo = new OrganicOptimizer(organicComponents, currentPoints, targetPoints);
        oo.initWithGreedySolution();
        oo.optimizeMultiplierPoints("");

        return oo.optimalSequence;
    }

    private void initWithGreedySolution() {
        List<Component> orderedComponents = components.getComponentsByRatio();
        for (Component comp : orderedComponents) {
            int max = maxWithoutWaste(comp, true);
            optimalSequence.add(comp, max);
            optimalPoints += max * comp.getBonusPoints();
            if (optimalPoints >= targetPoints) {
                break; // found solution
            }
        }

        System.out.println(
                String.format("Greedy algo sequence: value=%s, points=%d :\n%s", optimalSequence.getTotalValue(),
                        optimalPoints, optimalSequence));
    }

    private void optimizeMultiplierPoints(String indent) {

        for (Component comp : components.distinct()) {

            //System.out.println(String.format("%sTrying with %ss", indent, comp.getName()));

            for (int count = maxWithoutWaste(comp, true); count > 0; count--) {
                components.remove(comp, count);
                currentSequence.add(comp, count);
                currentPoints += count * comp.getBonusPoints();
                //System.out.println(String.format("%sTrying with %d %s", indent, count, comp.getName()));

                updateOptimumIfNecessary();
                if (currentPoints < targetPoints && isCurrentSeqWorth()) {
                    optimizeMultiplierPoints(indent + " ");
                }

                currentPoints -= count * comp.getBonusPoints();
                currentSequence.removeLastGroup();
                components.add(comp, count);
            }
        }
    }

    private int maxWithoutWaste(Component component, boolean allowExcess) {
        int availableCount = components.getCount(component);
        int maxNecessary = (targetPoints - currentPoints) / component.getBonusPoints();
        if (allowExcess) {
            maxNecessary++;
        }
        return Math.min(availableCount, maxNecessary);
    }

    private boolean isCurrentSeqWorth() {
        if (optimalPoints < targetPoints) {
            // we haven't got any solution yet, so any sequence is worth trying
            return true;
        }
        long seqValue = currentSequence.getTotalValue();
        double bestRatio = components.getBestBonusToValueRatio();
        // the theoretical cheapest we could get, if we could use only the best component
        long valueLowerBound = seqValue + (int) ((targetPoints - currentPoints) / bestRatio);
        return valueLowerBound < optimalSequence.getTotalValue();
    }

    private void updateOptimumIfNecessary() {
        boolean optimalReachedTarget = optimalPoints >= targetPoints;
        boolean currentReachedTarget = currentPoints >= targetPoints;
        boolean currentIsCheaper = currentSequence.getTotalValue() < optimalSequence.getTotalValue();
        boolean currentIsCloser = currentSequence.getTotalBonusPoints() > optimalSequence.getTotalBonusPoints();

        boolean cheaperSolution = optimalReachedTarget && currentReachedTarget && currentIsCheaper;
        boolean gotASolutionNow = !optimalReachedTarget && currentReachedTarget;
        boolean closerSequence = !optimalReachedTarget && !currentReachedTarget && currentIsCloser;
        if (closerSequence || gotASolutionNow || cheaperSolution) {
            optimalSequence.clear();
            optimalSequence.addAll(currentSequence);
            optimalPoints = currentPoints;

            String message;
            if (closerSequence) {
                message = "Better score found";
            } else if (gotASolutionNow) {
                message = "First solution found";
            } else {
                message = "New optimum found";
            }

            System.out.println(String.format("%s, value=%s, points=%d :\n%s", message, optimalSequence.getTotalValue(),
                    optimalPoints, optimalSequence));
        }
    }
}
