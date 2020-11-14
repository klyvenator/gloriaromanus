package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import unsw.gloriaromanus.Model.*;
import unsw.gloriaromanus.Controller.*;

public class FactionTest {
    String file = "{\r\n    \"Provinces\": [\r\n      \"Achaia\",\r\n      \"Aegyptus\",\r\n      \"Africa Proconsularis\",\r\n      \"Alpes Cottiae\",\r\n      \"Alpes Graiae et Poeninae\",\r\n      \"Alpes Maritimae\",\r\n      \"Aquitania\",\r\n      \"Arabia\",\r\n      \"Armenia Mesopotamia\",\r\n      \"Asia\",\r\n      \"Baetica\",\r\n      \"Belgica\",\r\n      \"Bithynia et Pontus\",\r\n      \"Britannia\",\r\n      \"Cilicia\",\r\n      \"Creta et Cyrene\",\r\n      \"Cyprus\",\r\n      \"Dacia\",\r\n      \"Dalmatia\",\r\n      \"Galatia et Cappadocia\",\r\n      \"Germania Inferior\",\r\n      \"Germania Superior\",\r\n      \"I\",\r\n      \"II\",\r\n      \"III\",\r\n      \"IV\",\r\n      \"IX\",\r\n      \"Iudaea\",\r\n      \"Lugdunensis\",\r\n      \"Lusitania\",\r\n      \"Lycia et Pamphylia\",\r\n      \"Macedonia\",\r\n      \"Mauretania Caesariensis\",\r\n      \"Mauretania Tingitana\",\r\n      \"Moesia Inferior\",\r\n      \"Moesia Superior\",\r\n      \"Narbonensis\",\r\n      \"Noricum\",\r\n      \"Numidia\",\r\n      \"Pannonia Inferior\",\r\n      \"Pannonia Superior\",\r\n      \"Raetia\",\r\n      \"Sardinia et Corsica\",\r\n      \"Sicilia\",\r\n      \"Syria\",\r\n      \"Tarraconensis\",\r\n      \"Thracia\",\r\n      \"V\",\r\n      \"VI\",\r\n      \"VII\",\r\n      \"VIII\",\r\n      \"X\",\r\n      \"XI\"\r\n    ]\r\n}";
    List<String> givenList = Arrays.asList("faction1","faction2");
    List<String> smallTowns = Arrays.asList("Sydney", "Melbourne", "Brisbane");

  /*   @Test
    public void allocateTownsTest() throws IOException {
        // 
        List<Faction> facList = GloriaRomanusController.allocateTowns(givenList, file);
        // checks that two factions were created
        assertEquals(2, facList.size());
        // checks that the faction names are correct
        for(int i = 0; i < facList.size(); i++ ){
            assertEquals(givenList.get(i), facList.get(i).getFactionName());
        }
    } */

    @Test
    public void checkWealthOfFactions(){
        List<Faction> facList = new ArrayList<Faction>();
        for(String s : givenList){
            Faction newFac = new Faction(s);
            facList.add(newFac);
        }
        // check the first faction is indeed faction1
        Faction f1 = facList.get(0);
        Faction f2 = facList.get(1);
        assertEquals("faction1", f1.getFactionName());
        for(String st : smallTowns){
            f1.addTown(f1, st);
        }
        assertEquals("Sydney",f1.getTowns().get(0).getTownName());
        // total wealth and gold for f2 should be zero since it has no provinces with towns
        assertEquals(0, f2.getTotalGold());
        assertEquals(0, f2.getTotalWealth());

        // add growth to all towns and then claim tax for 3 provinces -> (10+10-2)*3 = 54 total wealth
        // there will then be 6 gold to treasury
        f1.updateWealth();
        assertEquals(54, f1.getTotalWealth());
        assertEquals(6, f1.getTotalGold());
    }

}
