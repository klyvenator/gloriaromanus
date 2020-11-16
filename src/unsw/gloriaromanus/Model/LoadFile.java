package unsw.gloriaromanus.Model;

import org.json.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

public class LoadFile {
    public String filename;
    public List<Faction> factionList;
    public int gameYear;

    public LoadFile(String filename){
        this.filename = filename;
        this.factionList = new ArrayList<Faction>();
        load(filename);
    }
    public void setFactionList(List<Faction> factionList) {
        this.factionList = factionList;
    }
    public void setGameYear(int year) {
        this.gameYear = year;
    }
    public int getGameYear(){
        return this.gameYear;
    }
    public void addToFactions(Faction faction){
        this.factionList.add(faction);
    }
    public List<Faction> getFactionList() {
        return factionList;
    }
    // reads the json file and gets each objet and saves it
    public void load(String filename){
        // TO DO uncomment this for milestone 3 
        String jsonString = null;
        try {
            jsonString = Files.readString(Paths.get("src/unsw/gloriaromanus/SaveFiles/"+filename));
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
        //String jsonString = filename;
        JSONArray jArray = new JSONArray(jsonString);
        JSONObject jObject = new JSONObject();
        for(int i = 0; i < jArray.length(); i++){
            // gets the faction object
            jObject = jArray.getJSONObject(i);
            if( !jObject.isNull("Current year") ){
                setGameYear(jObject.getInt("Current year"));
            }else{ 
                createFaction(jObject);
            }
        }
        
    }
    // creates a faction from the faction object
    public void createFaction(JSONObject jsonObject){
        for (String key: jsonObject.keySet()) {
            JSONObject factionObject = jsonObject.getJSONObject(key);
            String factionName = factionObject.getString("faction");
            // create and set facion attributes
            Faction fac = new Faction(factionName);
            fac.setGold(factionObject.getInt("Gold"));
            fac.setwealth(factionObject.getInt("Wealth"));
            //adds to the faction list
            addToFactions(fac);
            // gets the array of towns belonging to the faction
            JSONArray towns = factionObject.getJSONArray("Towns");
            JSONObject jObject = new JSONObject();
            for(int i = 0; i < towns.length(); i++){
                jObject = towns.getJSONObject(i);
                createTown(jObject,fac);
            }     
        }
    }

    // from the object containing the towns creates towns
    public void createTown(JSONObject jsonObject, Faction fac){
        for (String key: jsonObject.keySet()) {
            JSONObject townObject = jsonObject.getJSONObject(key);
            String townName = townObject.getString("name");
            // creates and stores town attributes
            Town town = new Town(fac, townName);
            town.setWealth(townObject.getInt("Town Wealth"));
            town.updateTaxStatus(townObject.getString("Tax Type"));
            town.setRecentlyInvaded(townObject.getInt("recentlyInvaded"));
            fac.addTown(town);
            // gets the array of units for each town
            JSONArray units = townObject.getJSONArray("Units");
            JSONObject jObject = new JSONObject();
            for(int i = 0; i < units.length(); i++){
                jObject = units.getJSONObject(i);
                createUnits(jObject, fac, town);
            }
            
        }
    }
    //  uses the UnitFactory functionality to create units
    public void createUnits(JSONObject jsonObject, Faction fac, Town town){
        for (String key: jsonObject.keySet()) {
            JSONObject unitObject = jsonObject.getJSONObject(key);
            UnitFactory factory = new UnitFactory();
            Unit unit = factory.deserialise(unitObject);
            unit.setFaction(fac);
            unit.setCurrentlyOn(town);
            town.addUnit(unit);
        }
    }/* 
    public static void main(String[] args) {
        String f = "[\r\n    {\r\n       \"faction1\":{\r\n          \"Gold\":0,\r\n          \"Towns\":[\r\n             {\r\n                \"Sydney\":{\r\n                   \"Tax Type\":\"Low\",\r\n                   \"Town Wealth\":10,\r\n                   \"name\":\"Sydney\",\r\n                   \"Units\":[\r\n                      {\r\n                         \"Melee Infantry\":{\r\n                            \"shield\":1,\r\n                            \"cost\":1,\r\n                            \"Abilities\":[\r\n                               \r\n                            ],\r\n                            \"type\":\"MELEE\",\r\n                            \"speed\":1,\r\n                            \"armour\":1,\r\n                            \"number\":1,\r\n                            \"Movement Points\":10,\r\n                            \"defense\":1,\r\n                            \"attack\":1,\r\n                            \"Buffs\":[\r\n                               \r\n                            ],\r\n                            \"name\":\"Melee Infantry\",\r\n                            \"morale\":1,\r\n                            \"category\":\"infantry\",\r\n                            \"turnstoproduce\":1\r\n                         }\r\n                      }\r\n                   ]\r\n                }\r\n             },\r\n             {\r\n                \"Melbourne\":{\r\n                   \"Tax Type\":\"Low\",\r\n                   \"Town Wealth\":10,\r\n                   \"name\":\"Melbourne\",\r\n                   \"Units\":[\r\n                      {\r\n                         \"Elephant\":{\r\n                            \"shield\":1,\r\n                            \"cost\":1,\r\n                            \"Abilities\":[\r\n                               \r\n                            ],\r\n                            \"type\":\"MELEE\",\r\n                            \"speed\":1,\r\n                            \"armour\":1,\r\n                            \"number\":1,\r\n                            \"Movement Points\":15,\r\n                            \"defense\":1,\r\n                            \"attack\":1,\r\n                            \"Buffs\":[\r\n                               \r\n                            ],\r\n                            \"name\":\"Elephant\",\r\n                            \"morale\":1,\r\n                            \"category\":\"cavalry\",\r\n                            \"turnstoproduce\":1\r\n                         }\r\n                      }\r\n                   ]\r\n                }\r\n             },\r\n             {\r\n                \"Brisbane\":{\r\n                   \"Tax Type\":\"Low\",\r\n                   \"Town Wealth\":10,\r\n                   \"name\":\"Brisbane\",\r\n                   \"Units\":[\r\n                      \r\n                   ]\r\n                }\r\n             }\r\n          ],\r\n          \"faction\":\"faction1\",\r\n\"Wealth\":0\r\n       },\"faction2\":{\"faction\":\"test\"}\r\n    }\r\n ]";
        new LoadFile(f);
    } */
}
