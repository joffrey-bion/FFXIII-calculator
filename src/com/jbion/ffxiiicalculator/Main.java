package com.jbion.ffxiiicalculator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import com.jbion.ffxiiicalculator.data.GameData;
import com.jbion.ffxiiicalculator.data.Parser;
import com.jbion.ffxiiicalculator.model.Component;
import com.jbion.ffxiiicalculator.model.ComponentPool;
import com.jbion.ffxiiicalculator.model.ItemInstance;
import com.jbion.ffxiiicalculator.model.UpgradePlan;
import com.jbion.ffxiiicalculator.model.Weapon;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 400, 400);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setTitle("Final Fantasy XIII Calculator");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Parser parser = new Parser();
        GameData data = parser.parseData();
        //parser.printData();

        System.out.print("Init components...");
        Component sturdyBone = data.findComponent("Sturdy Bone");
        Component crankshaft = data.findComponent("Crankshaft");
        Component superconductor = data.findComponent("Superconductor");
        Component particleAccelerator = data.findComponent("Particle Accelerator");
        Component ultracompactReactor = data.findComponent("Ultracompact Reactor");

        ComponentPool inventory = new ComponentPool();
        inventory.add(sturdyBone, 40);
        //        inventory.add(crankshaft, 3);
        //        inventory.add(superconductor, 2);
        //        inventory.add(particleAccelerator, 1);
        //        inventory.add(ultracompactReactor, 0);

        Weapon lionheart = data.findWeapon("Lionheart");
        ItemInstance item = new ItemInstance(lionheart, 3, 35, 0);
        System.out.println(" done.");

        System.out.println("Calculating upgrade...");
        UpgradePlan plan = Calculator.upgrade(inventory, item);
        System.out.println();
        System.out.println("Here is the upgrade plan:");
        System.out.println(plan);

        //launch(args);
        System.exit(0);
    }
}
