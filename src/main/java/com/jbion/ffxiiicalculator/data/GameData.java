package com.jbion.ffxiiicalculator.data;

import java.util.Set;
import java.util.stream.Collectors;

import com.jbion.ffxiiicalculator.model.Accessory;
import com.jbion.ffxiiicalculator.model.Component;
import com.jbion.ffxiiicalculator.model.Component.Type;
import com.jbion.ffxiiicalculator.model.Weapon;

public class GameData {

    private final Set<Component> allComponents;

    private final Set<Component> organicComponents;

    private final Set<Component> syntheticComponents;

    private final Set<Component> catalysts;

    private final Set<Weapon> weapons;

    private final Set<Accessory> accessories;

    GameData(Set<Component> components, Set<Weapon> weapons, Set<Accessory> accessories) {
        this.allComponents = components;
        this.weapons = weapons;
        this.accessories = accessories;

        this.organicComponents = components.stream().filter(Component::isOrganic).collect(Collectors.toSet());
        this.syntheticComponents = components.stream().filter(Component::isSynthetic).collect(Collectors.toSet());
        this.catalysts = components.stream().filter(Component::isCatalyst).collect(Collectors.toSet());
    }

    public Component findComponent(String name) {
        return allComponents.stream().filter(c -> c.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }

    public Weapon findWeapon(String name) {
        return weapons.stream().filter(c -> c.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }

    public Accessory findAccessory(String name) {
        return accessories.stream().filter(c -> c.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }

    public Set<Component> getComponents() {
        return allComponents;
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

    public Set<Weapon> getWeapons() {
        return weapons;
    }

    public Set<Accessory> getAccessories() {
        return accessories;
    }

    public double getMaxBonusRatio(int currentChapter) {
        return syntheticComponents.stream()
                                  .filter(c -> c.isBuyable(currentChapter))
                                  .mapToDouble(Component::getBonusBuyPriceRatio)
                                  .max()
                                  .orElse(0);
    }

    public double getMaxExpRatio(int currentChapter, int targetRank) {
        return syntheticComponents.stream()
                                  .filter(c -> c.isBuyable(currentChapter))
                                  .mapToDouble(c -> c.getExpBuyPriceRatio(targetRank))
                                  .max()
                                  .orElse(0);
    }
}
