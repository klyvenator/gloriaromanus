package unsw.gloriaromanus;

import org.json.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.JsonObject;

import java.util.ArrayList;

public class SaveFile {
    public static String filename;
    public File file;
    public List<Faction> factions;

    public SaveFile(List<Faction> factions, String filename) throws IOException {
        this.file = createFile(filename);
        addToFile(factions);
    }
    public File getFile(){
        return this.file;
    }
    public static File createFile(String filename) throws IOException {
        // uncomment this for milestone 3 won't work for testing 
        //File temp = new File("src/unsw/gloriaromanus/SaveFiles/"+filename+".json");
        File temp = new File(filename+".json");
        try {
            if (temp.createNewFile()) {
                System.out.println("File created: " + temp.getName());
                return temp;
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
        return temp;
    }
    // adds faction and all of the things that are attached to that faction
    // eg Towns, units, wealth, 
    public void addToFile(List<Faction>factions){
        JSONArray jArray = new JSONArray();

        for(Faction f : factions){
            JSONObject jsonObject = new JSONObject();
            JSONObject faction = new JSONObject();
            JSONArray towns = new JSONArray();
            // set faction name as key
            //jArray.put(f.getFactionName());
            // add factions current gold and wealth 
            jsonObject.put("Gold", f.getTotalGold());
            jsonObject.put("Wealth", f.getTotalWealth()); 
            // adds a list of towns that the faction owns
            towns = addTowns(f.getTowns());
            jsonObject.put("Towns", towns);
            faction.put(f.getFactionName(), jsonObject);
            jArray.put(faction);
        }
        System.out.println(jArray);
    }
    public void writeToFile(){
        
    }
    // passes in a list of towns and gets the properties of each town and returns a json array
    public JSONArray addTowns(List<Town>towns){
        JSONArray jArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        for(Town t : towns){
            JSONArray units = new JSONArray();
            jArray.put(t.getTownName());
            jsonObject.put("Tax Type", t.getTaxStatus());
            jsonObject.put("Town Wealth", t.getWealth());
            //units = addUnits(t.getArmy())
            jArray.put(jsonObject);
        }
        return jArray;
    }
    public JSONArray addUnits(List<Unit>units){
        JSONArray jArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        for(Unit u : units){
            jArray.put(u.getName());
            jsonObject.put("NumTroops", u.getNumTroops());
            jsonObject.put("Type", u.getType());
            jsonObject.put("Defense", u.getDefense());
            jsonObject.put("Morale", u.getMorale());
            jsonObject.put("Speed", u.getSpeed());
            jsonObject.put("Attack", u.getAttack());
            jsonObject.put("Cost", u.getCost());
            jsonObject.put("Turns to Make", u.getTurnsToMake());
            jsonObject.put("Movement Points", u.getMovementPoints());
            jsonObject.put("Abilities", u.getAbilities());
            jsonObject.put("Buffs", u.getBuffs());
            jArray.put(jsonObject);
        }
        return jArray;
    }
}
