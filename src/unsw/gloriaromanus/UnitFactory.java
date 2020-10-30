package unsw.gloriaromanus;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import unsw.gloriaromanus.Enums.Range;


public class UnitFactory implements Factory{
    List<JSONObject> blueprints;
    
    public UnitFactory() {
        blueprints = new ArrayList<JSONObject>();
    }

    // extracts file into json objects and stores it as a list.
    public void initialise() {
    }

    // Deserialises a single unit contained in a json object.
    public void deserialise(JSONObject json) {

        String name = json.getString("name");
        int number = json.getInt("number");
        Range type = Range.MELEE;
        int defense = json.getInt("defense");
        int morale = json.getInt("morale");
        int speed = json.getInt("speed");
        int attack = json.getInt("attack");
        int cost = json.getInt("cost");
        int turns = json.getInt("turns");
        List<Ability> abilities = AbilityFactory.deserialise(); // to be completed.

        Range range = Range.MELEE;
        if (json.getString("range").equals("ranged")) {
            range = Range.RANGED;
        }

        if (json.getString("type").equals("cavalry")) {
            Cavalry unit = new Cavalry(name, range, number, attack, defense, speed, morale, turns, cost, abilities);
        } else if (json.getString("type").equals("infantry")) {
            Infantry unit = new Infantry(name, range, number, attack, defense, speed, morale, turns, cost, abilities);
        } else {
            Artillery unit = new Artillery(name, range, number, attack, defense, speed, morale, turns, cost, abilities);
        }
    }

    // returns created unit
    public Unit createUnit(String name) {
        return null;
    }
}