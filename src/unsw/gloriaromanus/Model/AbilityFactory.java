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
            jsonString = Files.readString(Paths.get("src/unsw/gloriaromanus/Json/abilities2.json"));
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }

        JSONObject json = new JSONObject(jsonString);
        for (String key: json.keySet()) {
            JSONObject abilityInfo = json.getJSONObject(key);
            if (
                abilityInfo.has("target") &&
                abilityInfo.has("unitName") &&
                abilityInfo.has("buffs")
            ) {
                abilityInfo.put("name", key);
                blueprints.add(abilityInfo);
            }
        }
    }

    private JSONObject getBlueprint(String name) {
        for (JSONObject blueprint : blueprints) {
            if (blueprint.getString("name").equals(name)) {
                return blueprint;
            }
        }
        return null;
    }

    public AbilityContainer deserialise(String name) {
        JSONObject blueprint = getBlueprint(name);
        return deserialise(blueprint);
    }

    // Deserialises a single unit contained in a json object and returns a Unit.
    public AbilityContainer deserialise(JSONObject blueprint) {

        if (blueprint == null) {
            return null;
        }

        return new AbilityContainer(
            blueprint.getString("name"),
            blueprint.getString("target"),
            blueprint.getJSONObject("buffs")
        );

        /*
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
