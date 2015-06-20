package com.jbion.ffxiiicalculator.model;

import java.util.LinkedList;
import java.util.List;

public class ComponentSequence {

    private final LinkedList<ComponentGroup> components = new LinkedList<>();

    private int expGain;

    public void add(Component type, int count) {
        add(type, count, false);
    }

    public void add(Component type, int count, boolean mergeIfPossible) {
        ComponentGroup lastGroup = components.getLast();
        if (mergeIfPossible && lastGroup.getType() == type) {
            lastGroup.increment(count);
        } else {
            components.add(new ComponentGroup(type, 1));
        }
    }

    public void addAll(ComponentSequence sequence) {
        addAll(sequence, false);
    }

    public void addAll(ComponentSequence sequence, boolean mergeTailAndHead) {
        ComponentGroup lastGroup = components.getLast();
        ComponentGroup nextGroup = sequence.components.getFirst();
        // merge the junction groups if they hold the same component type
        if (mergeTailAndHead && lastGroup.getType() == nextGroup.getType()) {
            lastGroup.increment(nextGroup.getCount());
            sequence.components.removeFirst();
        }
        components.addAll(sequence.components);
    }

    public int getExpReached() {
        return expGain;
    }

    public void setExpReached(int exp) {
        this.expGain = exp;
    }

    public List<ComponentGroup> getOrderedGroups() {
        return components;
    }

}
