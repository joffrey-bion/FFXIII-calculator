package com.jbion.ffxiiicalculator.model;

public enum GameCharacter {

    LIGHTNING,
    SAZH,
    SNOW,
    HOPE,
    VANILLE,
    FANG;

    public static GameCharacter forName(String name) {
        try {
            return GameCharacter.valueOf(name.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }

}
