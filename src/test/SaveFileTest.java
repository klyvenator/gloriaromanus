package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import unsw.gloriaromanus.*;

public class SaveFileTest {
    // provided lists to test 
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
        // delete file before use for sanity reasons
        File temp = new File("testSave.json"); 
        temp.delete();
        saveFile = new SaveFile(facList, "testSave");
        // store contents of file to a string 
        File newFile = saveFile.getFile();
        String created = Files.readString(Paths.get(newFile.getName()));
        String expected = "[{\"faction1\":{\"Gold\":0,\"Towns\":[{\"Sydney\":{\"Tax Type\":\"Low\",\"Town Wealth\":10,\"name\":\"Sydney\",\"Units\":[{\"Melee Infantry\":{\"shield\":1,\"cost\":1,\"Abilities\":[],\"type\":\"MELEE\",\"speed\":1,\"armour\":1,\"number\":1,\"Movement Points\":10,\"defense\":1,\"attack\":1,\"Buffs\":[],\"name\":\"Melee Infantry\",\"morale\":1,\"category\":\"infantry\",\"turnstoproduce\":1}}]}},{\"Melbourne\":{\"Tax Type\":\"Low\",\"Town Wealth\":10,\"name\":\"Melbourne\",\"Units\":[{\"Elephant\":{\"shield\":1,\"cost\":1,\"Abilities\":[],\"type\":\"MELEE\",\"speed\":1,\"armour\":1,\"number\":1,\"Movement Points\":15,\"defense\":1,\"attack\":1,\"Buffs\":[],\"name\":\"Elephant\",\"morale\":1,\"category\":\"cavalry\",\"turnstoproduce\":1}}]}},{\"Brisbane\":{\"Tax Type\":\"Low\",\"Town Wealth\":10,\"name\":\"Brisbane\",\"Units\":[]}}],\"faction\":\"faction1\",\"Wealth\":0}}]";
        // checks that the expected output is the same as the created file 
        assertEquals(expected, created);
        // delete file after use for santiy reasons 
        temp = new File("testSave.json"); 
        temp.delete();
    }
    // creates some units from a name, makes a reference to the factin and the town it's located 
    // then adds that unit to list of units in town
    public void createSomeUnits(String name, Faction faction, Town town){
        UnitFactory factory = new UnitFactory();
        factory.initialise();
        Unit unit = factory.createUnit(name, faction, town);
        town.addUnit(unit);
    }
}
