package com.jbion.ffxiiicalculator.model;

public class ComponentGroup {

    private final Component type;

    private final int count;

    public ComponentGroup(Component type, int count) {
        this.type = type;
        this.count = count;
    }

    public Component getType() {
        return type;
    }

    public int getCount() {
        return count;
    }
}
