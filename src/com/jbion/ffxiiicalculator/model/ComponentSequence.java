package com.jbion.ffxiiicalculator.model;

import java.util.LinkedList;
import java.util.List;

public class ComponentSequence {

    private final LinkedList<ComponentGroup> components = new LinkedList<>();

    private long size = 0;

    private long totalValue = 0;

    public long size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(ComponentGroup group) {
        components.add(group);
        size += group.getCount();
        totalValue += group.getCount() * group.getType().getSellPrice();
    }

    public void add(Component type, int count) {
        add(new ComponentGroup(type, count));
    }

    public void addAll(ComponentSequence sequence) {
        sequence.components.stream().forEach(cg -> add(cg));
    }

    public void removeLastGroup() {
        if (components.isEmpty()) {
            throw new IllegalStateException("this sequence is empty, cannot remove anything");
        }
        ComponentGroup group = components.removeLast();
        size -= group.getCount();
        totalValue -= group.getCount() * group.getType().getSellPrice();
    }

    public long getTotalValue() {
        return totalValue;
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
