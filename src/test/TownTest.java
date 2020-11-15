package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import unsw.gloriaromanus.Model.*;

public class TownTest {
    public Faction fac = new Faction("Bogans");
    public Town t = fac.addTown(fac,"Australia");
     @Test
    public void townOwner(){
        assertEquals("Bogans", t.getFaction());
        // TownName or province should be "Australia"
        assertEquals("Australia", t.getTownName());
        // wealth is initially set to 10 gold
        assertEquals(10, t.getWealth());
        // no units were added so it'll just be empty
        assertNotNull(t.getArmy().getAllUnits());
    }

    @Test
    public void taxCheck(){
        assertEquals("Low", t.getTaxStatus());
        // since "Low" taxrate = 10% growth is +10 gold 
        // gold added first then taxed is 20 - 10% = 18
        t.wealthAfterTax();
        assertEquals(18, t.getWealth());

        // change tax type to "High"
        t.updateTaxStatus("High");
        assertEquals("High", t.getTaxStatus());
        // get the amount of tax owed off a "High" status and get wealth after tax 
        // 18total - 10gold and 8gold -2taxed = 6
        t.wealthAfterTax();
        assertEquals(6,t.getWealth());

        //check that town wealth cannot be negative
        // current wealth is 6 - 10 gold = - 4, but since wealth cannot be negative
        // wealth should equal 0
        t.wealthAfterTax();
        assertEquals(0,t.getWealth());
    } 
}
