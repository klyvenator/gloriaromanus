package unsw.gloriaromanus;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import unsw.gloriaromanus.Enums.Range;


public class UnitFactory implements Factory{
    private List<JSONObject> blueprints;
    
    public UnitFactory() {
        blueprints = new ArrayList<JSONObject>();
    }

    // extracts file into json objects and stores it as a list.
    public void initialise() {
        Files.readString(Paths.get("unsw/gloriousromanus/Json/Units.json"));
        
        //to do. also check no duplicate names., has name and has category
    }

    // Deserialises a single unit contained in a json object and returns a Unit.
    public Unit deserialise(JSONObject json) {

        Unit newUnit = null;
        switch (json.getString("category")) {
            case "cavalry":
                newUnit = new Cavalry(json.getString("name"));
                break;
            case "infantry":
                newUnit = new Infantry(json.getString("name"));
                break;
            case "artillery":
                newUnit = new Artillery(json.getString("name"));
                break;
        }

        if (json.has("number")) {
            newUnit.setNumTroops(json.getInt("number"));
        }
        if (json.has("range")) {
            if (json.getString("range").equals("ranged")) {
                newUnit.setType(Range.RANGED);
            } 
        }
        if (json.has("defense skill")) {
            newUnit.getDefense().setDefenseSkill(json.getInt("defense"));
        }
        if (json.has("armour")) {
            newUnit.getDefense().setArmour(json.getInt("armour"));
        }
        if (json.has("defense skill")) {
            newUnit.getDefense().setShield(json.getInt("defense"));
        }
        if (json.has("morale")) {
            newUnit.setMorale(json.getInt("morale"));
        }
        if (json.has("speed")) {
            newUnit.setSpeed(json.getInt("speed"));
        }
        if (json.has("attack")) {
            newUnit.setAttack(json.getInt("attack"));
        }
        if (json.has("cost")) {
            newUnit.setCost(json.getInt("cost"));
        }
        if (json.has("turns")) {
            newUnit.setTurnsToMake(json.getInt("turns"));
        }

        //List<Ability> abilities = AbilityFactory.deserialise(json.getJSONArray("abilities"));  To be completed

        return newUnit;

    }

    // returns created unit
    public Unit createUnit(String name) {
        if (blueprints.size() == 0) {
            return null;
        }

        for (JSONObject current : blueprints) {
            if (current.getString("name").equals(name)) {
                return deserialise(current);
            }
        }
        return null;
    }

    public List<JSONObject> getBlueprints() {
        return blueprints;
    }
}