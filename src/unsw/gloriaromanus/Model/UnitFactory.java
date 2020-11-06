package unsw.gloriaromanus.Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import unsw.gloriaromanus.Model.Enums.Range;


public class UnitFactory implements Factory{
    private List<JSONObject> blueprints;
    
    public UnitFactory() {
        blueprints = new ArrayList<JSONObject>();
    }

    // extracts file into json objects and stores it as a list.
    public void initialise(){
        String jsonString = null;
        try {
            jsonString = Files.readString(Paths.get("src/unsw/gloriaromanus/Json/Units.json"));
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        } 
        //String jsonString = "{\r\n    \"Melee Infantry\":{\r\n        \"type\": \"melee\",\r\n        \"category\": \"infantry\",\r\n        \"number\": 1,\r\n        \"attack\": 1,\r\n        \"defense\": 1,\r\n        \"armour\": 1,\r\n        \"shield\": 1,\r\n        \"speed\": 1,\r\n        \"morale\": 1,\r\n        \"turnsToProduce\": 1,\r\n        \"cost\": 1,\r\n        \"specialAbility\": \"shield\"\r\n    },\r\n    \"Heavy Infantry\":{\r\n        \"type\": \"melee\",\r\n        \"category\": \"infantry\",\r\n        \"number\": 1,\r\n        \"attack\": 1,\r\n        \"defense\": 1,\r\n        \"armour\": 1,\r\n        \"shield\": 1,\r\n        \"speed\": 1,\r\n        \"morale\": 1,\r\n        \"turnsToProduce\": 1,\r\n        \"cost\": 1\r\n    },\r\n    \"Spearman\":{\r\n        \"type\": \"melee\",\r\n        \"category\": \"infantry\",\r\n        \"number\": 1,\r\n        \"attack\": 1,\r\n        \"defense\": 1,\r\n        \"armour\": 1,\r\n        \"shield\": 1,\r\n        \"speed\": 1,\r\n        \"morale\": 1,\r\n        \"turnsToProduce\": 1,\r\n        \"cost\": 1\r\n    },\r\n    \"Legionary\":{\r\n        \"Type\": \"melee\",\r\n        \"category\": \"infantry\",\r\n        \"number\": 1,\r\n        \"attack\": 1,\r\n        \"defense\": 1,\r\n        \"armour\": 1,\r\n        \"shield\": 1,\r\n        \"speed\": 1,\r\n        \"morale\": 1,\r\n        \"turnsToProduce\": 1,\r\n        \"cost\": 1,\r\n        \"specialAbility\": \"eagle\"\r\n    },\r\n    \"Beserker\":{\r\n        \"Type\": \"melee\",\r\n        \"category\": \"infantry\",\r\n        \"number\": 1,\r\n        \"attack\": 1,\r\n        \"defense\": 1,\r\n        \"armour\": 1,\r\n        \"shield\": 1,\r\n        \"speed\": 1,\r\n        \"morale\": 1,\r\n        \"turnsToProduce\": 1,\r\n        \"cost\": 1,\r\n        \"specialAbility\": \"rage\"\r\n    },\r\n    \"Melee Cavalry\":{\r\n        \"Type\": \"melee\",\r\n        \"category\": \"cavalry\",\r\n        \"number\": 1,\r\n        \"attack\": 1,\r\n        \"defense\": 1,\r\n        \"armour\": 1,\r\n        \"shield\": 1,\r\n        \"speed\": 1,\r\n        \"morale\": 1,\r\n        \"turnsToProduce\": 1,\r\n        \"cost\": 1,\r\n        \"specialAbility\": \"heroic\"\r\n    },\r\n    \"Chariot\":{\r\n        \"Type\": \"melee\",\r\n        \"category\": \"cavalry\",\r\n        \"number\": 1,\r\n        \"attack\": 1,\r\n        \"defense\": 1,\r\n        \"armour\": 1,\r\n        \"shield\": 1,\r\n        \"speed\": 1,\r\n        \"morale\": 1,\r\n        \"turnsToProduce\": 1,\r\n        \"cost\": 1\r\n    },\r\n    \"Elephant\":{\r\n        \"Type\": \"melee\",\r\n        \"category\": \"cavalry\",\r\n        \"number\": 1,\r\n        \"attack\": 1,\r\n        \"defense\": 1,\r\n        \"armour\": 1,\r\n        \"shield\": 1,\r\n        \"speed\": 1,\r\n        \"morale\": 1,\r\n        \"turnsToProduce\": 1,\r\n        \"cost\": 1,\r\n        \"specialAbility\": \"random attack\"\r\n    },\r\n    \"Horse Archer\":{\r\n        \"Type\": \"ranged\",\r\n        \"category\": \"cavalry\",\r\n        \"number\": 1,\r\n        \"attack\": 1,\r\n        \"defense\": 1,\r\n        \"armour\": 1,\r\n        \"shield\": 1,\r\n        \"speed\": 1,\r\n        \"morale\": 1,\r\n        \"turnsToProduce\": 1,\r\n        \"cost\": 1\r\n    },\r\n    \"Druid\":{\r\n        \"Type\": \"ranged\",\r\n        \"category\": \"infantry\",\r\n        \"number\": 1,\r\n        \"attack\": 1,\r\n        \"defense\": 1,\r\n        \"armour\": 1,\r\n        \"shield\": 1,\r\n        \"speed\": 1,\r\n        \"morale\": 1,\r\n        \"turnsToProduce\": 1,\r\n        \"cost\": 1,\r\n        \"specialAbility\": \"druid fervour\"\r\n    },\r\n    \"Artillery\":{\r\n        \"Type\": \"ranged\",\r\n        \"category\": \"artillery\",\r\n        \"number\": 1,\r\n        \"attack\": 1,\r\n        \"defense\": 1,\r\n        \"armour\": 1,\r\n        \"shield\": 1,\r\n        \"speed\": 1,\r\n        \"morale\": 1,\r\n        \"turnsToProduce\": 1,\r\n        \"cost\": 1\r\n    },\r\n    \"Missile Infantry\":{\r\n        \"Type\": \"ranged\",\r\n        \"category\": \"artillery\",\r\n        \"number\": 1,\r\n        \"attack\": 1,\r\n        \"defense\": 1,\r\n        \"armour\": 1,\r\n        \"shield\": 1,\r\n        \"speed\": 1,\r\n        \"morale\": 1,\r\n        \"turnsToProduce\": 1,\r\n        \"cost\": 1\r\n    }\r\n\r\n\r\n}";
        JSONObject json = new JSONObject(jsonString);
        for (String key: json.keySet()) {
            if (!json.getJSONObject(key).has("category")) {
                System.out.println("Unit must have a category");
                System.exit(1);
            }
            json.getJSONObject(key).put("name", key);
            blueprints.add(json.getJSONObject(key));
        }
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

        newUnit.setCategory(json.getString("category"));
        if (json.has("number")) {
            newUnit.setNumTroops(json.getInt("number"));
        }
        if (json.has("range")) {
            if (json.getString("range").equals("ranged")) {
                newUnit.setType(Range.RANGED);
            } 
        }
        if (json.has("defense")) {
            newUnit.getDefense().setDefenseSkill(json.getInt("defense"));
        }
        if (json.has("armour")) {
            newUnit.getDefense().setArmour(json.getInt("armour"));
        }
        if (json.has("shield")) {
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
    public Unit createUnit(String name, Faction faction, Town town) {
        if (blueprints.size() == 0) {
            return null;
        }

        Unit newUnit;

        for (JSONObject current : blueprints) {
            if (current.getString("name").equals(name)) {
                newUnit = deserialise(current);
                newUnit.setCurrentlyOn(town);
                newUnit.setFaction(faction);
                return newUnit;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        UnitFactory factory = new UnitFactory();
        factory.initialise();
        Infantry newUnit = (Infantry)factory.createUnit("Melee Infantry", null, null);
        System.out.println(newUnit);
    }

    public List<JSONObject> getBlueprints() {
        return blueprints;
    }
}