package com.jbion.ffxiiicalculator.model;

public enum Shop {

    UNICORN_MART("Unicorn Mart", 1),
    BW_OUTFITTERS("B&W Outfitters", 2),
    UP_IN_ARMS("Up In Arms", 3),
    LENORA("Lenora's Garage", 4),
    MAGICAL_MOMENTS("Magical Moments", 5),
    CREATURE_COMFORTS("Creature comforts", 5),
    PLAUTUS_WORKSHOP("Plautus's Workshop", 7),
    THE_MOTHERLODE("The Motherlode", 8),
    MOOGLEWORKS("Moogleworks", 10),
    RD_DEPOT("R&D Depot", 11),
    GILGAMESH("Gilgamesh Inc.", 11),
    SANCTUM_LABS("Sanctum Labs", 12),
    EDEN_PHARMACEUTICALS("Eden Pharmaceuticals", 12);

    private static final Shop[] ALL = values();

    private final String name;

    private final int chapterAvailability;

    Shop(String name, int chapterAvailability) {
        this.name = name;
        this.chapterAvailability = chapterAvailability;
    }

    public static Shop fromStringContaining(String locations) {
        String locationsLowercase = locations.toLowerCase();
        for (Shop s : ALL) {
            if (locationsLowercase.contains(s.getName().toLowerCase())) {
                return s;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public int getChapterAvailability() {
        return chapterAvailability;
    }
}
