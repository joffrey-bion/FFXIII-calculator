package com.jbion.ffxiiicalculator.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ComponentPool {

    private final Map<Component, Integer> components = new HashMap<>();

    public int getCount(Component comp) {
        return components.get(comp);
    }

    public void add(Component type) {
        add(type, 1);
    }

    public void remove(Component type) {
        remove(type, 1);
    }

    public void add(Component type, int count) {
        Integer currentCount = components.get(type);
        if (currentCount == null) {
            currentCount = 0;
        }
        components.put(type, currentCount + count);
    }

    public void remove(Component type, int count) {
        Integer currentCount = components.get(type);
        if (currentCount == null || currentCount == 0) {
            throw new IllegalArgumentException(String.format("there is no %s to remove", type.getName()));
        } else if (currentCount < count) {
            throw new IllegalArgumentException(String.format("cannot remove %d %ss, only %d are present", count,
                    type.getName(), currentCount));
        }
        components.put(type, currentCount - count);
    }

    public void addAll(ComponentPool other) {
        other.components.entrySet().stream().forEach(e -> add(e.getKey(), e.getValue()));
    }

    public void addAll(ComponentSequence other) {
        other.getOrderedGroups().stream().forEach(e -> add(e.getType(), e.getCount()));
    }

    public void removeAll(ComponentPool other) {
        other.components.entrySet().stream().forEach(e -> remove(e.getKey(), e.getValue()));
    }

    public void removeAll(ComponentSequence other) {
        other.getOrderedGroups().stream().forEach(e -> remove(e.getType(), e.getCount()));
    }

    public Set<Component> distinct() {
        return components.keySet();
    }
}
