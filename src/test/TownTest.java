package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import unsw.gloriaromanus.*;

public class TownTest {

    @Test
    public void townOwnerWealth(){
        Town t = new Town();
        //Town owner autmatically set to a string called 
        // "Temp Faction" should equal owner
        assertEquals("Temp Faction", t.getFaction());
        // wealth is initially set to 10 gold
        assertEquals(10, t.getWealth());
    }

    @Test
    public void taxCheck(){
        Town t = new Town();
        // tax status should be set to low initially
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
