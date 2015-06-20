package com.jbion.ffxiiicalculator.model;

public class ComponentGroup {

    private final Component type;

    private int count;

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

    public void decrement() {
        count--;
    }

    public void decrement(int n) {
        this.count -= n;
    }

    public void increment() {
        count++;
    }

    public void increment(int n) {
        this.count += n;
    }

}
