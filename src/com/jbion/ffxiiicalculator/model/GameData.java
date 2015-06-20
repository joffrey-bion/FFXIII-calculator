package com.jbion.ffxiiicalculator.model;

import java.util.HashSet;
import java.util.Set;

public class GameData {

    private final Set<Component> organicComponents = new HashSet<>();

    private final Set<Component> syntheticComponents = new HashSet<>();

    private final Set<Component> catalysts = new HashSet<>();

    private final Set<ItemInstance> weapons = new HashSet<>();

    public GameData() {
        // TODO add everything
    }

    private void add(Component comp) {
        switch (comp.getType()) {
        case ORGANIC:
            organicComponents.add(comp);
            break;
        case SYNTHETIC:
            syntheticComponents.add(comp);
            break;
        case CATALYST:
            catalysts.add(comp);
            break;
        }
    }

    public Set<Component> getOrganicComponents() {
        return organicComponents;
    }

    public Set<Component> getSyntheticComponents() {
        return syntheticComponents;
    }

    public Set<Component> getCatalysts() {
        return catalysts;
    }

    public Set<ItemInstance> getWeapons() {
        return weapons;
    }

    public double getMaxBonusRatio(int currentChapter) {
        return syntheticComponents.stream().filter(c -> c.isBuyable(currentChapter))
                .mapToDouble(Component::getBonusRatio).max().orElse(0);
    }

    public double getMaxExpRatio(int currentChapter, int targetRank) {
        return syntheticComponents.stream().filter(c -> c.isBuyable(currentChapter))
                .mapToDouble(c -> c.getExpRatio(targetRank)).max().orElse(0);
    }
}
