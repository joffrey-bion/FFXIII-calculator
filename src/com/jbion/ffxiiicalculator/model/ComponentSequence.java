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
        if (mergeIfPossible && !components.isEmpty()) {
            ComponentGroup lastGroup = components.getLast();
            if (lastGroup.getType() == type) {
                lastGroup.increment(count);
                return;
            }
        }
        components.add(new ComponentGroup(type, 1));
    }

    public void addAll(ComponentSequence sequence) {
        addAll(sequence, false);
    }

    public void addAll(ComponentSequence sequence, boolean mergeTailAndHead) {
        if (mergeTailAndHead && !components.isEmpty() && !sequence.components.isEmpty()) {
            ComponentGroup lastGroup = components.getLast();
            ComponentGroup nextGroup = sequence.components.getFirst();
            // merge the junction groups if they hold the same component type
            if (mergeTailAndHead && lastGroup.getType() == nextGroup.getType()) {
                lastGroup.increment(nextGroup.getCount());
                sequence.components.removeFirst();
                return;
            }
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ComponentGroup componentGroup : components) {
            if (componentGroup.getCount() > 0) {
                sb.append(String.format("% 2dx ", componentGroup.getCount()));
                sb.append(componentGroup.getType().getName());
                sb.append("\n");
            }
        }
        return sb.toString();
    }

}
