package unsw.gloriaromanus;

import org.json.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class SaveFile {
    public static String filename;
    public File file;
    public List<Faction> factions;

    public SaveFile(List<Faction> factions, String filename) throws IOException {
        this.file = createFile(filename);
        JSONArray jArray = getFileContent(factions);
        writeToFile(this.file, jArray);
    }
    public File getFile(){
        return this.file;
    }
    public void writeToFile(File file, JSONArray jArray){
        try {
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(jArray.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
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
    public JSONArray getFileContent(List<Faction>factions){
        JSONArray jArray = new JSONArray();

        for(Faction f : factions){
            JSONObject jsonObject = new JSONObject();
            JSONObject faction = new JSONObject();
            JSONArray towns = new JSONArray();
            // set faction name as key
            // add factions current gold and wealth 
            jsonObject.put("Gold", f.getTotalGold());
            jsonObject.put("Wealth", f.getTotalWealth()); 
            // adds a list of towns that the faction owns
            towns = addTowns(f.getTowns());
            jsonObject.put("Towns", towns);
            jsonObject.put("faction", f.getFactionName());
            faction.put(f.getFactionName(), jsonObject);
            jArray.put(faction);
        }
        System.out.println(jArray);
        return jArray;
    }
    // passes in a list of towns and gets the properties of each town and returns a json array
    public JSONArray addTowns(List<Town>towns){
        JSONArray jArray = new JSONArray();
        for(Town t : towns){
            JSONObject jsonObject = new JSONObject();
            JSONObject town = new JSONObject();
            JSONArray units = new JSONArray();
            jsonObject.put("Tax Type", t.getTaxStatus());
            jsonObject.put("Town Wealth", t.getWealth());
            jsonObject.put("name", t.getTownName());
            units = addUnits(t.getUnits());
            jsonObject.put("Units", units);
            town.put(t.getTownName(), jsonObject);
            jArray.put(town);
        }
        return jArray;
    }
    public JSONArray addUnits(List<Unit>units){
        JSONArray jArray = new JSONArray();
        for(Unit u : units){
            JSONObject jsonObject = new JSONObject();
            JSONObject unit = new JSONObject();
            jsonObject.put("type", u.getType());
            jsonObject.put("category", u.getCategory());
            jsonObject.put("number", u.getNumTroops());
            jsonObject.put("defense", u.getDefense().getDefenseSkill());
            jsonObject.put("armour", u.getDefense().getArmour());
            jsonObject.put("shield", u.getDefense().getShield());
            jsonObject.put("morale", u.getMorale());
            jsonObject.put("speed", u.getSpeed());
            jsonObject.put("attack", u.getAttack());
            jsonObject.put("cost", u.getCost());
            jsonObject.put("turnstoproduce", u.getTurnsToMake());
            jsonObject.put("Movement Points", u.getMovementPoints());
            jsonObject.put("Abilities", u.getAbilities());
            jsonObject.put("Buffs", u.getBuffs());
            jsonObject.put("name", u.getName());
            unit.put(u.getName(), jsonObject);
            jArray.put(unit);
        }
        return jArray;
    }
}
