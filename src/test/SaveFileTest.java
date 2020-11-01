package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
        for(String s : givenList){
            Faction newFac = new Faction(s);
            facList.add(newFac);
        }
        Faction f1 = facList.get(0);
        for(String st : smallTowns){
            f1.addTown(f1, st);
        }
            saveFile = new SaveFile(facList, "testSave");
    }
}
