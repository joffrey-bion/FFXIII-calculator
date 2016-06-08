package com.jbion.ffxiiicalculator.model;

public class UpgradePlan {

    private ComponentPool componentsToSell;

    private ComponentPool componentsToBuy;

    private ComponentSequence componentsToUse;

    public ComponentPool getComponentsToSell() {
        return componentsToSell;
    }

    public void setComponentsToSell(ComponentPool componentsToSell) {
        this.componentsToSell = componentsToSell;
    }

    public ComponentPool getComponentsToBuy() {
        return componentsToBuy;
    }

    public void setComponentsToBuy(ComponentPool componentsToBuy) {
        this.componentsToBuy = componentsToBuy;
    }

    public ComponentSequence getComponentsToUse() {
        return componentsToUse;
    }

    public void setComponentsToUse(ComponentSequence componentsToUse) {
        this.componentsToUse = componentsToUse;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("STEP 1: Sell the following components:\n\n");
        sb.append(componentsToSell.toString());
        sb.append("\n");
        sb.append("STEP 2: Buy the following components:\n\n");
        sb.append(componentsToBuy.toString());
        sb.append("\n");
        sb.append("STEP 3: Apply the following components in the given order:\n\n");
        sb.append(componentsToUse.toString());
        return sb.toString();
    }

}
