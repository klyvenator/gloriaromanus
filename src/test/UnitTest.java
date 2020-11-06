package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import unsw.gloriaromanus.Model.*;
import unsw.gloriaromanus.Model.Enums.*;

public class UnitTest{
    
    @Test
    public void blahTest(){
        assertEquals("a", "a");
    }
    
    @Test
    public void blahTest2(){
        //Unit u = new Unit();
        //assertEquals(u.getNumTroops(), 50);
    }

    @Test
    public void unitTests(){
        Cavalry c = new Cavalry("Elephants");
        assertEquals(c.getNumTroops(), 100);
        assertEquals(c.getType(), Range.MELEE);
        assertEquals((c.getAbilities().size()), 0);
        assertEquals(c.getName(), "Elephants");
        Infantry i = new Infantry("Archers");
        assertEquals(i.getAttack(), 10);
        assertEquals(c.getTurnsToMake(), 1);
        Artillery a = new Artillery("Catapults");
        assertEquals(a.getSpeed(), 10);
    }

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

        Unit panda = factory.createUnit("Panda Bears", null, null);
        assertEquals(panda.getCost(), 500);
        assertEquals(panda.getSpeed(), 10);
        assertEquals(panda instanceof Cavalry , true);

    }
}


