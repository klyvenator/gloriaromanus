package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import unsw.gloriaromanus.*;
import unsw.gloriaromanus.Enums.*;


public class ArmyTest{
    
    @Test
    public void blahTest(){
        assertEquals("basic", "basic");
    }
    
    @Test
    public void blahTest2(){
        //Unit u = new Unit();
        //assertEquals(u.getNumTroops(), 50);
    }

    @Test
    public void armyTests() {
        Cavalry c = new Cavalry("Elephants");
        Infantry i = new Infantry("Archers");
        Artillery a = new Artillery("Catapults");
        
        
        Army army = new Army();
        army.addUnit(c);
        army.addUnit(a);
        army.addUnit(i);

        List<Unit> units = army.getAllUnits();
        assertEquals(3, army.getNumUnits(), "Army initialisation not working");

    }

    @Test
    public void battleTest() {
        Army army1 = new Army();
        Infantry infantry = new Infantry("Archers");
        army1.addUnit(infantry);

        Army army2 = new Army();
        Artillery artillery = new Artillery("Catapults");
        army2.addUnit(artillery);

        assertEquals(1, army1.getNumUnits());
        assertEquals(1, army2.getNumUnits());
        
        BattleResolver res = new BattleResolver(army1, army2);
        res.startBattle();

        // One of the armies lost => all defeated or broken
        assertEquals(true, (army1.numAvailableUnits() == 0) || (army2.numAvailableUnits() == 0)); 

    }

    @BeforeClass
    public void units(){
        Cavalry c = new Cavalry("Elephants");
        
        assertEquals(c.getNumTroops(), 100);
        assertEquals(c.getType(), Range.MELEE);
        assertEquals(c.getAbility(), null);
        assertEquals(c.getName(), "Elephants");
        
        Infantry i = new Infantry("Archers");
        
        assertEquals(i.getAttack(), 10);
        assertEquals(c.getTurnsToMake(), 1);
        
        Artillery a = new Artillery("Catapults");
        assertEquals(a.getSpeed(), 10);
    }

    /*
    @Test
    public void unitFactoryTests(){
        UnitFactory factory = new UnitFactory();
        List<JSONObject> blueprints = factory.getBlueprints();
        assertEquals(factory.createUnit("blah", null, null), null);

        JSONObject json = new JSONObject();
        json.put("name", "Panda Bears");
        json.put("number", 10000);
        json.put("cost", 500);
        json.put("type", Range.RANGED);
        json.put("category", "cavalry");
        blueprints.add(json);
        assertEquals(factory.createUnit("blah", null, null), null);

        Cavalry panda = (Cavalry)factory.createUnit("Panda Bears", null, null);
        assertEquals(panda.getCost(), 500);
        assertEquals(panda.getSpeed(), 10);
        assertEquals(panda instanceof Cavalry , true);
        

    }

    */
}


