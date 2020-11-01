package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import unsw.gloriaromanus.*;

public class SaveFileTest {
    List<String> givenList = Arrays.asList("faction1");
    List<String> smallTowns = Arrays.asList("Sydney", "Melbourne", "Brisbane");
	private SaveFile saveFile;

    @Test
    public void createSaveFile() throws IOException {
        // just one faction for now
        List<Faction> facList = new ArrayList<Faction>();
        List<Town> townList = new ArrayList<Town>();
        // create each faction from the givenlist and add it to the Faction list
        for(String s : givenList){
            Faction newFac = new Faction(s);
            facList.add(newFac);
        }
        // create each town from smallTowns and add to the Town list
        Faction f1 = facList.get(0);
        for(String st : smallTowns){
            f1.addTown(f1, st);
        }
        townList = f1.getTowns();
        // create some units and add them to Sydney and Melbourne
        createSomeUnits("Melee Infantry", f1, townList.get(0));
        createSomeUnits("Elephant", f1, townList.get(1));
        File temp = new File("testSave.json"); 
        temp.delete();
        saveFile = new SaveFile(facList, "testSave");
        System.out.println(saveFile);
        temp = new File("testSave.json"); 
        temp.delete();
    }

    public void createSomeUnits(String name, Faction faction, Town town){
        UnitFactory factory = new UnitFactory();
        factory.initialise();
        Unit unit = factory.createUnit(name, faction, town);
        town.addUnit(unit);
        //List<Unit> u = town.getUnits();
       // System.out.println(u.get(0).getName());
    }
}
