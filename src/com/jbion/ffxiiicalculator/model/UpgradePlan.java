package com.jbion.ffxiiicalculator.model;


public class UpgradePlan {

    private ComponentPool componentsToSell;

    private ComponentPool componentsToBuy;

    private ComponentSequence organicComponentsToUse;

    private ComponentSequence syntheticComponentsToUse;

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

    public ComponentSequence getOrganicComponentsToUse() {
        return organicComponentsToUse;
    }

    public void setOrganicComponentsToUse(ComponentSequence organicComponentsToUse) {
        this.organicComponentsToUse = organicComponentsToUse;
    }

    public ComponentSequence getSyntheticComponentsToUse() {
        return syntheticComponentsToUse;
    }

    public void setSyntheticComponentsToUse(ComponentSequence syntheticComponentsToUse) {
        this.syntheticComponentsToUse = syntheticComponentsToUse;
    }

}
