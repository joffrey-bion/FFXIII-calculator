package com.jbion.ffxiiicalculator.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;

public class ComponentPool {

    private static final Comparator<Component> ratioComparator =
            Comparator.comparingDouble(Component::getBonusSellPriceRatio);

    private final Map<Component, Integer> components = new HashMap<>();

    private final PriorityQueue<Component> componentsByRatio = new PriorityQueue<>(ratioComparator);

    public int getCount(Component comp) {
        Integer currentCount = components.get(comp);
        return currentCount == null ? 0 : currentCount;
    }

    public void add(Component type, int count) {
        Integer currentCount = getCount(type);
        if (currentCount == 0 && count > 0) {
            // there used to be no components of that type, now there is
            componentsByRatio.add(type);
        }
        components.put(type, currentCount + count);
    }

    public void remove(Component type, int count) {
        remove(type, count, false);
    }

    private void remove(Component type, int count, boolean tolerateExcess) {
        Integer currentCount = components.get(type);
        if (currentCount == null) {
            if (tolerateExcess) {
                return;
            }
            throw new IllegalArgumentException(String.format("there is no %s to remove", type.getName()));
        }
        if (currentCount < count) {
            if (tolerateExcess) {
                components.put(type, 0);
                componentsByRatio.remove(type);
                return;
            }
            throw new IllegalArgumentException(
                    String.format("cannot remove %d %ss, only %d are present", count, type.getName(), currentCount));
        }
        components.put(type, currentCount - count);
        if (currentCount == count) {
            componentsByRatio.remove(type);
        }
    }

    public double getBestBonusToValueRatio() {
        if (componentsByRatio.isEmpty()) {
            throw new IllegalStateException("No best bonus/value ratio exists as this pool is empty");
        }
        return componentsByRatio.peek().getBonusSellPriceRatio();
    }

    public List<Component> getComponentsByRatio() {
        List<Component> orderedComponents = new ArrayList<>(componentsByRatio);
        orderedComponents.sort(ratioComparator.reversed());
        return orderedComponents;
    }

    public void addAll(ComponentPool other) {
        other.components.forEach(this::add);
    }

    public void addAll(ComponentSequence other) {
        other.getOrderedGroups().forEach(e -> add(e.getType(), e.getCount()));
    }

    public void removeAll(ComponentPool other) {
        other.components.forEach((comp, count) -> remove(comp, count, false));
    }

    public void removeAll(ComponentSequence other) {
        other.getOrderedGroups().forEach(e -> remove(e.getType(), e.getCount(), false));
    }

    public Set<Component> distinct() {
        return components.keySet();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Entry<Component, Integer> componentGroup : components.entrySet()) {
            if (componentGroup.getValue() > 0) {
                sb.append(String.format("% 2dx ", componentGroup.getValue()));
                sb.append(componentGroup.getKey().getName());
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
