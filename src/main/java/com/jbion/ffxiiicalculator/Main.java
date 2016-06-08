package com.jbion.ffxiiicalculator;

import com.jbion.ffxiiicalculator.data.GameData;
import com.jbion.ffxiiicalculator.data.Parser;
import com.jbion.ffxiiicalculator.model.Component;
import com.jbion.ffxiiicalculator.model.ComponentPool;
import com.jbion.ffxiiicalculator.model.ComponentSequence;

public class Main {

    public static void main(String[] args) {
        Parser parser = new Parser();
        GameData data = parser.parseData();
        //parser.printData();

        System.out.println("Init organic components...");
        Component sturdyBone = data.findComponent("Sturdy Bone");
        System.out.println(sturdyBone);
        Component fragrantOil = data.findComponent("Fragrant Oil");
        System.out.println(fragrantOil);
        Component scaledWing = data.findComponent("Scaled Wing");
        System.out.println(scaledWing);
        Component murkyOoze = data.findComponent("Murky Ooze");
        System.out.println(murkyOoze);
        System.out.println();

        ComponentPool organics = new ComponentPool();
        organics.add(sturdyBone, 10);
        organics.add(fragrantOil, 12);
        organics.add(scaledWing, 18);
        organics.add(murkyOoze, 26);
        System.out.println(organics);
        System.out.println();

        System.out.println("Init synthetic components...");
        Component crankshaft = data.findComponent("Crankshaft");
        Component superconductor = data.findComponent("Superconductor");
        Component particleAccelerator = data.findComponent("Particle Accelerator");
        Component ultracompactReactor = data.findComponent("Ultracompact Reactor");
        System.out.println();

        ComponentSequence seq = OrganicOptimizer.optimizeMultiplierPoints(organics, 12);
        System.out.println("Optimal organic comps sequence:\n" + seq);

        ComponentPool synthetics = new ComponentPool();
        synthetics.add(crankshaft, 3);
        synthetics.add(superconductor, 2);
        synthetics.add(particleAccelerator, 1);
        synthetics.add(ultracompactReactor, 0);

        //        Weapon lionheart = data.findWeapon("Lionheart");
        //        ItemInstance item = new ItemInstance(lionheart, 3, 35, 0);
        //        System.out.println(" done.");
        //
        //        System.out.println("Calculating upgrade...");
        //        UpgradePlan plan = Calculator.upgrade(inventory, item);
        //        System.out.println();
        //        System.out.println("Here is the upgrade plan:");
        //        System.out.println(plan);
    }
}
