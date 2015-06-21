package com.jbion.ffxiiicalculator.data;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.hildan.utils.csv.CsvReader;

import com.jbion.ffxiiicalculator.model.Accessory;
import com.jbion.ffxiiicalculator.model.Component;
import com.jbion.ffxiiicalculator.model.Component.Type;
import com.jbion.ffxiiicalculator.model.GameCharacter;
import com.jbion.ffxiiicalculator.model.Shop;
import com.jbion.ffxiiicalculator.model.Weapon;

public class Parser {

    private static final String[] EMPTY_COL_VALUES = {"-", "---", "—", ""};

    private static final String FILE_COMPONENTS = "components.csv";

    private static final String FILE_WEAPONS = "weapons.csv";

    private static final String FILE_ACCESSORIES = "accessories.csv";

    /*
     * Item columns
     */
    private static final int COL_NAME = 0;

    private static final int COL_RANK = 1;

    private static final int COL_SELL_PRICE = 2;

    private static final int COL_BUY_PRICE = 3;

    private static final int COL_SHOP = 4;

    private static final int COL_CHAPTER = 5;

    /*
     * Component specific columns
     */
    private static final int COL_CHAPTER_STEP = 6;

    private static final int COL_COMPONENT_TYPE = 7;

    private static final int COL_MULTIPLIER_POINTS = 8;

    private static final int COL_EXP_RANK_X_START = 9;

    private static final int COL_EXP_RANK_X_END = 19;

    /*
     * UpgradableItem specific columns
     */
    private static final int COL_EXP_FIRST_LEVEL = 6;

    private static final int COL_EXP_INCREMENT = 7;

    private static final int COL_EXP_MAX = 8;

    private static final int COL_LVL_MAX = 9;

    private static final int COL_CATALYST = 10;

    private static final int COL_EVOLUTION = 11;

    private static final int COL_EVOLUTION_LVL = 12;

    private static final int COL_SYNTHESIS = 13;

    private static final int COL_ABILITY = 14;

    /*
     * Weapon specific columns
     */
    private static final int COL_STRENGTH_MIN = 15;

    private static final int COL_STRENGTH_MAX = 16;

    private static final int COL_MAGIC_MIN = 17;

    private static final int COL_MAGIC_MAX = 18;

    private static final int COL_CHARACTER = 19;

    /*
     * Accessory specific columns
     */
    private static final int COL_BONUS_MIN = 15;

    private static final int COL_BONUS_MAX = 16;

    private final Set<Component> components = new HashSet<>();

    private final Set<Weapon> weapons = new HashSet<>();

    private final Set<Accessory> accessories = new HashSet<>();

    public GameData parseData() {
        parseFile(FILE_COMPONENTS, ";", this::parseComponent);
        parseFileWithDependentRows(FILE_WEAPONS, ";", this::parseWeapon);
        parseFileWithDependentRows(FILE_ACCESSORIES, ";", this::parseAccessory);
        return new GameData(components, weapons, accessories);
    }

    private Component findComponent(String name) {
        return components.stream().filter(c -> c.getName().equals(name)).findAny().orElse(null);
    }

    private Weapon findWeapon(String name) {
        return weapons.stream().filter(c -> c.getName().equals(name)).findAny().orElse(null);
    }

    private Accessory findAccessory(String name) {
        return accessories.stream().filter(c -> c.getName().equals(name)).findAny().orElse(null);
    }

    public void parseFile(String filename, String delimiter, Consumer<String[]> rowParser) {
        InputStream stream = getClass().getResourceAsStream(filename);
        try (CsvReader reader = new CsvReader(stream, delimiter)) {
            reader.readRow(); // skip the header
            String[] row;
            while ((row = reader.readRow()) != null) {
                rowParser.accept(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void parseFileWithDependentRows(String filename, String delimiter,
            BiConsumer<String[], Collection<String[]>> rowParser) {
        final List<String[]> dependentRowsInFile = new ArrayList<>();
        parseFile(filename, delimiter, r -> rowParser.accept(r, dependentRowsInFile));

        List<String[]> dependentRows = dependentRowsInFile;
        while (!dependentRows.isEmpty()) {
            List<String[]> stillDependentRows = new ArrayList<>();
            for (String[] row : dependentRows) {
                rowParser.accept(row, stillDependentRows);
            }
            dependentRows = stillDependentRows;
        }
    }

    private void parseComponent(String[] row) {
        String name = row[COL_NAME];
        int rank = Integer.parseInt(row[COL_RANK]);
        int sellPrice = Integer.parseInt(row[COL_SELL_PRICE]);
        Integer buyPrice = optionalInt(row[COL_BUY_PRICE]);
        Shop shopToBuy = optionalShop(row[COL_SHOP]);
        Integer chapterAvailability = optionalInt(row[COL_CHAPTER]);

        Component.Type type = Type.valueOf(row[COL_COMPONENT_TYPE]);
        int multiplierPoints = Integer.parseInt(row[COL_MULTIPLIER_POINTS]);
        int[] expPerRank = new int[COL_EXP_RANK_X_END - COL_EXP_RANK_X_START + 1];
        for (int i = 0; i < expPerRank.length; i++) {
            expPerRank[i] = Integer.parseInt(row[COL_EXP_RANK_X_START + i]);
        }

        components.add(new Component(name, rank, expPerRank, multiplierPoints, sellPrice, buyPrice, type, shopToBuy,
                chapterAvailability));
    }

    private void parseWeapon(String[] row, Collection<String[]> dependentRows) {
        // dependency check first
        String evolutionStr = row[COL_EVOLUTION];
        Weapon evolution = findWeapon(evolutionStr);
        if (!isEmpty(evolutionStr) && evolution == null) {
            // dependencies not found yet
            dependentRows.add(row);
            return;
        }

        String name = row[COL_NAME];
        int rank = Integer.parseInt(row[COL_RANK]);
        int sellPrice = Integer.parseInt(row[COL_SELL_PRICE]);
        Integer buyPrice = optionalInt(row[COL_BUY_PRICE]);
        Shop shopToBuy = optionalShop(row[COL_SHOP]);
        Integer chapterAvailability = optionalInt(row[COL_CHAPTER]);

        int expFirstLevel = Integer.parseInt(row[COL_EXP_FIRST_LEVEL]);
        int expIncrement = optionalInt(row[COL_EXP_INCREMENT], 0);
        int expToMax = Integer.parseInt(row[COL_EXP_MAX]);
        int maxLevel = Integer.parseInt(row[COL_LVL_MAX]);
        Component catalyst = findComponent(row[COL_CATALYST]);
        // evolution already parsed at the top
        Integer evolutionLvl = optionalInt(row[COL_EVOLUTION_LVL]);
        String synthesizedAbility = row[COL_SYNTHESIS];
        String passiveAbility = row[COL_ABILITY];

        int strengthMin = Integer.parseInt(row[COL_STRENGTH_MIN]);
        int strengthMax = Integer.parseInt(row[COL_STRENGTH_MAX]);
        int magicMin = Integer.parseInt(row[COL_MAGIC_MIN]);
        int magicMax = Integer.parseInt(row[COL_MAGIC_MAX]);
        GameCharacter character = GameCharacter.forName(row[COL_CHARACTER]);

        weapons.add(new Weapon(name, rank, sellPrice, buyPrice, shopToBuy, chapterAvailability, expFirstLevel,
                expIncrement, expToMax, maxLevel, catalyst, evolution, evolutionLvl, synthesizedAbility,
                passiveAbility, character, strengthMin, strengthMax, magicMin, magicMax));
    }

    private void parseAccessory(String[] row, Collection<String[]> dependentRows) {
        // dependency check first
        String evolutionStr = row[COL_EVOLUTION];
        Accessory evolution = findAccessory(evolutionStr);
        if (!isEmpty(evolutionStr) && evolution == null) {
            // dependencies not found yet
            dependentRows.add(row);
            return;
        }

        String name = row[COL_NAME].trim();
        int rank = Integer.parseInt(row[COL_RANK]);
        int sellPrice = Integer.parseInt(row[COL_SELL_PRICE]);
        Integer buyPrice = optionalInt(row[COL_BUY_PRICE]);
        Shop shopToBuy = optionalShop(row[COL_SHOP]);
        Integer chapterAvailability = optionalInt(row[COL_CHAPTER]);

        int expFirstLevel = Integer.parseInt(row[COL_EXP_FIRST_LEVEL]);
        int expIncrement = optionalInt(row[COL_EXP_INCREMENT], 0);
        int expToMax = Integer.parseInt(row[COL_EXP_MAX]);
        int maxLevel = Integer.parseInt(row[COL_LVL_MAX]);
        Component catalyst = findComponent(row[COL_CATALYST]);
        // evolution already parsed at the top
        Integer evolutionLvl = optionalInt(row[COL_EVOLUTION_LVL]);
        String synthesizedAbility = row[COL_SYNTHESIS];
        String passiveAbility = row[COL_ABILITY];

        Integer bonusMin = optionalInt(row[COL_BONUS_MIN]);
        Integer bonusMax = optionalInt(row[COL_BONUS_MAX]);

        accessories.add(new Accessory(name, rank, sellPrice, buyPrice, shopToBuy, chapterAvailability, expFirstLevel,
                expIncrement, expToMax, maxLevel, catalyst, evolution, evolutionLvl, synthesizedAbility,
                passiveAbility, bonusMin, bonusMax));
    }

    private static boolean isEmpty(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        for (String s : EMPTY_COL_VALUES) {
            if (s.equals(str)) {
                return true;
            }
        }
        return false;
    }

    private static Integer optionalInt(String str) {
        return optionalInt(str, null);
    }

    private static Integer optionalInt(String str, Integer defaultValue) {
        if (isEmpty(str)) {
            return defaultValue;
        }
        return Integer.parseInt(str);
    }

    private static Shop optionalShop(String str) {
        if (isEmpty(str)) {
            return null;
        }
        return Shop.fromStringContaining(str);
    }

    public void printData() {
        System.out.println("======== COMPONENTS (" + components.size() + ") ========");
        System.out.println();
        components.stream().forEach(c -> System.out.println(c));
        System.out.println();

        System.out.println("======== WEAPONS (" + weapons.size() + ") ========");
        System.out.println();
        weapons.stream().forEach(c -> System.out.println(c));
        System.out.println();

        System.out.println("======== ACCESSORIES (" + accessories.size() + ") ========");
        System.out.println();
        accessories.stream().forEach(c -> System.out.println(c));
        System.out.println();
    }
}
