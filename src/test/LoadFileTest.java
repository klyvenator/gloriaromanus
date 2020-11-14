package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;


import unsw.gloriaromanus.Model.*;

public class LoadFileTest {
    public String loadFile = "[\r\n    {\r\n       \"faction1\":{\r\n          \"Gold\":0,\r\n          \"Towns\":[\r\n             {\r\n                \"Sydney\":{\r\n                   \"Tax Type\":\"Low\",\r\n                   \"Town Wealth\":10,\r\n                   \"name\":\"Sydney\",\r\n                   \"Units\":[\r\n                      {\r\n                         \"Melee Infantry\":{\r\n                            \"shield\":1,\r\n                            \"cost\":1,\r\n                            \"Abilities\":[\r\n                               \r\n                            ],\r\n                            \"type\":\"MELEE\",\r\n                            \"speed\":1,\r\n                            \"armour\":1,\r\n                            \"number\":1,\r\n                            \"Movement Points\":10,\r\n                            \"defense\":1,\r\n                            \"attack\":1,\r\n                            \"Buffs\":[\r\n                               \r\n                            ],\r\n                            \"name\":\"Melee Infantry\",\r\n                            \"morale\":1,\r\n                            \"category\":\"infantry\",\r\n                            \"turnstoproduce\":1\r\n                         }\r\n                      }\r\n                   ]\r\n                }\r\n             },\r\n             {\r\n                \"Melbourne\":{\r\n                   \"Tax Type\":\"Low\",\r\n                   \"Town Wealth\":10,\r\n                   \"name\":\"Melbourne\",\r\n                   \"Units\":[\r\n                      {\r\n                         \"Elephant\":{\r\n                            \"shield\":1,\r\n                            \"cost\":1,\r\n                            \"Abilities\":[\r\n                               \r\n                            ],\r\n                            \"type\":\"MELEE\",\r\n                            \"speed\":1,\r\n                            \"armour\":1,\r\n                            \"number\":1,\r\n                            \"Movement Points\":15,\r\n                            \"defense\":1,\r\n                            \"attack\":1,\r\n                            \"Buffs\":[\r\n                               \r\n                            ],\r\n                            \"name\":\"Elephant\",\r\n                            \"morale\":1,\r\n                            \"category\":\"cavalry\",\r\n                            \"turnstoproduce\":1\r\n                         }\r\n                      }\r\n                   ]\r\n                }\r\n             },\r\n             {\r\n                \"Brisbane\":{\r\n                   \"Tax Type\":\"Low\",\r\n                   \"Town Wealth\":10,\r\n                   \"name\":\"Brisbane\",\r\n                   \"Units\":[\r\n                      \r\n                   ]\r\n                }\r\n             }\r\n          ],\r\n          \"faction\":\"faction1\",\r\n          \"Wealth\":0\r\n       }\r\n    }\r\n ]";

    @Test
     public void loadSaveFile(){
        assertNotNull(loadFile);
        LoadFile game = new LoadFile(loadFile);
        List<Faction> factions = game.getFactionList();
        // only created one faction "faction1"
        assertEquals("faction1",factions.get(0).getFactionName());
        // since only one faction all towns belong to that faction
        List<Town> towns = factions.get(0).getTowns();
       /// 3 towns were created Sydney, Melbourne, Brisbane
        assertEquals("Sydney", towns.get(0).getTownName());
        assertEquals("Melbourne", towns.get(1).getTownName());
        assertEquals("Brisbane", towns.get(2).getTownName());
        List<Unit> sydUnits = towns.get(0).getArmy().getAllUnits();
        List<Unit> melbUnits = towns.get(1).getArmy().getAllUnits();
        //sydney only has Melee Infantry and Melb only has Elephant
        assertEquals("Melee Infantry", sydUnits.get(0).getName());
        assertEquals("Elephant", melbUnits.get(0).getName()); 
    } 
}
