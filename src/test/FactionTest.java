package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import unsw.gloriaromanus.*;

public class FactionTest {
    String file = "{\r\n    \"Provinces\": [\r\n      \"Achaia\",\r\n      \"Aegyptus\",\r\n      \"Africa Proconsularis\",\r\n      \"Alpes Cottiae\",\r\n      \"Alpes Graiae et Poeninae\",\r\n      \"Alpes Maritimae\",\r\n      \"Aquitania\",\r\n      \"Arabia\",\r\n      \"Armenia Mesopotamia\",\r\n      \"Asia\",\r\n      \"Baetica\",\r\n      \"Belgica\",\r\n      \"Bithynia et Pontus\",\r\n      \"Britannia\",\r\n      \"Cilicia\",\r\n      \"Creta et Cyrene\",\r\n      \"Cyprus\",\r\n      \"Dacia\",\r\n      \"Dalmatia\",\r\n      \"Galatia et Cappadocia\",\r\n      \"Germania Inferior\",\r\n      \"Germania Superior\",\r\n      \"I\",\r\n      \"II\",\r\n      \"III\",\r\n      \"IV\",\r\n      \"IX\",\r\n      \"Iudaea\",\r\n      \"Lugdunensis\",\r\n      \"Lusitania\",\r\n      \"Lycia et Pamphylia\",\r\n      \"Macedonia\",\r\n      \"Mauretania Caesariensis\",\r\n      \"Mauretania Tingitana\",\r\n      \"Moesia Inferior\",\r\n      \"Moesia Superior\",\r\n      \"Narbonensis\",\r\n      \"Noricum\",\r\n      \"Numidia\",\r\n      \"Pannonia Inferior\",\r\n      \"Pannonia Superior\",\r\n      \"Raetia\",\r\n      \"Sardinia et Corsica\",\r\n      \"Sicilia\",\r\n      \"Syria\",\r\n      \"Tarraconensis\",\r\n      \"Thracia\",\r\n      \"V\",\r\n      \"VI\",\r\n      \"VII\",\r\n      \"VIII\",\r\n      \"X\",\r\n      \"XI\"\r\n    ]\r\n}";
    @Test
    public void allocateTownsTest() throws IOException {
        List<String> givenList = Arrays.asList("faction1","faction2");
        List<Faction> facList = GloriaRomanusController.allocateTowns(givenList, file);
        // checks that two factions were created
        assertEquals(2, facList.size());
        // checks that the faction names are correct
        for(int i = 0; i < facList.size(); i++ ){
            assertEquals(givenList.get(i), facList.get(i).getFactionName());
        }
    }

}
