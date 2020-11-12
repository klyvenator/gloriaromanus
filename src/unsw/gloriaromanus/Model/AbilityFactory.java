package unsw.gloriaromanus.Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import unsw.gloriaromanus.Model.Enums.Range;


public class AbilityFactory implements Factory{
    private List<JSONObject> blueprints;
    
    public AbilityFactory() {
        blueprints = new ArrayList<JSONObject>();
        initialise();
    }

    // extracts file into json objects and stores it as a list.
    public void initialise(){
        String jsonString = null;
        try {
            jsonString = Files.readString(Paths.get("src/unsw/gloriaromanus/Json/Abilities.json"));
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }

        JSONObject json = new JSONObject(jsonString);
        for (String key: json.keySet()) {
            if (!json.getJSONObject(key).has("type")) {
                System.out.println("Ability must have a type");
                System.exit(1);
            }
            json.getJSONObject(key).put("name", key);
            blueprints.add(json.getJSONObject(key));
        }
    }

    private JSONObject getBlueprint(String name) {
        for (JSONObject blueprint : blueprints) {
            if (blueprint.getString("name").equals("name")) {
                return blueprint;
            }
        }
        return null;
    }

    public Ability deserialise(String name) {
        JSONObject blueprint = getBlueprint(name);
        return deserialise(blueprint);
    }

    // Deserialises a single unit contained in a json object and returns a Unit.
    public Ability deserialise(JSONObject blueprint) {

        if (blueprint == null) {
            return null;
        }

        // for now
        return null;

        /*
        Ability ability = null;
        String type = blueprint.getString("type");
        String name = blueprint.getString("name");

        switch (type) {
            case "faction":
                ability = new FactionAbility(name);
                break;
            case "army":
                ability = new ArmyAbility(name);    
                break;
            case "skirmish":
                ability = new SkirmishAbility(name);    
                break;
            case "engagement":
                ability = new EngagementAbility(name);    
                break;
            case "unit":
                ability = new UnitAbility(name);    
                break;
        }

        // Example
        if (json.has("number")) {
            newUnit.setNumTroops(json.getInt("number"));
        }
        
        */

    }

    public List<JSONObject> getBlueprints() {
        return blueprints;
    }
}
