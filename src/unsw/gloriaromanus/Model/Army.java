package unsw.gloriaromanus.Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import org.json.JSONObject;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextArea;

public class Army {
    private List<Unit> units;
    private Town currentlyOn;
    private IntegerProperty numAvailableUnits;

    public Army() {
        units = new ArrayList<Unit>();
        currentlyOn = null;
        numAvailableUnits = new SimpleIntegerProperty(0);
    }

    public Army(Town currentlyOn) {
        this();
        this.currentlyOn = currentlyOn;
        units = new ArrayList<Unit>();
        numAvailableUnits = new SimpleIntegerProperty(0);
    }

    public void addUnit(Unit unit) {
        units.add(unit);
        numAvailableUnits.set(numAvailableUnits.get() + 1);
        unit.setAbilityContainerTarget(this);
    }

    public void addNumAvailableUnitsListener(ChangeListener<? super Number> listener) {
        numAvailableUnits.addListener(listener);
    }

    public void removeNumAvailableUnitsListener(ChangeListener<? super Number> listener) {
        numAvailableUnits.removeListener(listener);
    }

    public void removeUnit(Unit u) {
        units.remove(u);
        numAvailableUnits.set(numAvailableUnits.get() - 1);
    }

    public void setNumAvailableUnits(int num) {
        this.numAvailableUnits.set(num);
    }

    // Getters and Setters

    public int getNumUnits() {
        return units.size();
    }

    public List<Unit> getAllUnits() {
        return units;
    }

    public IntegerProperty getAvailableUnitsProperty() {
        return numAvailableUnits;
    }

    public void setAllUnbroken() {
        for (Unit unit : units) {
            unit.setBroken(false);
        }

        numAvailableUnits.set(getNumUnits());
    }

    public void removeDefeatedUnits() {
        for (Unit unit : units) {
            if (unit != null && unit.getNumTroops() <= 0) {
                units.remove(unit);
            }
        }
    }

    public void activateArmyAbilities(TextArea terminal) {
        for (Unit unit : units) {
            if (unit != null) {
                unit.activateAbility(ArmyTarget.class, terminal);
            }
        }
    }

    public void cancelArmyAbilities() {
        for (Unit unit : units) {
            if (unit != null) {
                unit.cancelAbility(ArmyTarget.class);
            }
        }
    }
    /**
     * Returns a random unit from this army
     * (<<general>> uniformly random)
     * @return unit, randomly (uniformly) chosen
     */
    public Unit randomlySelectUnit() {
        
        // If this army has no units
        if (units.size() == 0) {
            return null;
        }

        Random r = new Random();
        
        return units.get(r.nextInt(units.size()));
    }

    public int numAvailableUnits() {
        
        return numAvailableUnits.get();
        /*
        
        */
    }

    public void updateNumAvailableUnits() {
        int num = 0;
        for (Unit unit : units) {
            if (
                unit.getNumTroops() > 0 &&
                !unit.isBroken()
            ) {
                num++;
            }
        }
        numAvailableUnits.set(num);
    }
    
    /**
     * Returns a list of available units
     * A unit is avaialble if it is:
     * <ol>
     * <li> Alive
     * <li> Not Broken
     * </ol>
     * @return
     */
    public List<Unit> getAvailableUnits() {
        List<Unit> availableUnits = new ArrayList<>();

        for (Unit unit : units) {
            if (
                unit.getNumTroops() > 0 &&
                !unit.isBroken()
            ) {
                availableUnits.add(unit);
            }
        }

        return availableUnits;
    }
    /**
     * Returns a uniformly randomly selected unit
     * which is:
     * <ol>
     * <li> Alive
     * <li> Not Broken
     * </ol>
     * @return available unit
     */
    public Unit randomlySelectAvailableUnit() {

        // If this army has no units
        if (units.size() == 0) {
            return null;
        }

        List<Unit> availableUnits = getAvailableUnits();

        Random r = new Random();
        
        return availableUnits.get(r.nextInt(availableUnits.size()));
    }

    public boolean canMoveTo(Town t, Map<Town,Faction> provinceToFactionMap) throws IOException{
        String content = Files.readString(Paths.get("src/unsw/gloriaromanus/province_adjacency_matrix_fully_connected.json"));
        JSONObject provinceAdjacencyMatrix = new JSONObject(content);

        int lowestMovement  = findLowestMovement();
        int shortestDistance = findShortestDistance(0, provinceAdjacencyMatrix, currentlyOn.getTownName(), t.getTownName(), provinceToFactionMap);
        if (lowestMovement >= shortestDistance) {
            reduceMovePoints(shortestDistance);
            return true;
        }
        return false;
    }

    public Faction getFaction(String provinceName, Map<Town,Faction> provinceToOwningFactionMap) {
        for (Town t: provinceToOwningFactionMap.keySet()){
          if (t.getTownName().equals(provinceName)) {
            return provinceToOwningFactionMap.get(t);
          }
        }
        return null;
      }

    public int findShortestDistance(int shortest, JSONObject matrix, String root, String dest, Map<Town,Faction> provinceToFactionMap) throws IOException {
        Queue<String> queue = new LinkedList<String>();
        boolean found = false;
        Map<String, String> visited = new HashMap<String,String>();
        Faction humanFaction = getFaction(root, provinceToFactionMap);

        queue.add(root);
        visited.put(root, root);
        for (String province: matrix.getJSONObject(root).keySet()) {
            if (connected(province, root) && (getFaction(province, provinceToFactionMap) == humanFaction)) {
                queue.add(province);
            }
        }

        String s = null;
        while (!queue.isEmpty() && found == false) {
            s = queue.peek();
            queue.remove();

            if (s.equals(dest)) {
                found = true;
            }
            else {
                if (visited.get(s) != null) {
                    for (String province : matrix.getJSONObject(s).keySet()) {
                        if (connected(province, s) && visited.get(province) == null && (getFaction(province, provinceToFactionMap) == humanFaction)) {
                            visited.put(province, s);
                            queue.add(province);
                        }
                    }
                }
            }
        }

        String i = dest;
        //System.out.println(i + " " + root);
        while (!i.equals(root)) {
            i = visited.get(i);
            if (i == null) {
                return shortest = Integer.MAX_VALUE;
            }
            shortest++;
        }
        System.out.println(shortest);
        return shortest;
    }

    public int findLowestMovement() {
        int lowest = Integer.MAX_VALUE;
        for (Unit u : units) {
            if (u.getMovesLeft() < lowest) {
                lowest = u.getMovesLeft();
            }
        }
        return lowest;
    }

    private boolean connected(String province1, String province2) throws IOException {
        String content = Files.readString(Paths.get("src/unsw/gloriaromanus/province_adjacency_matrix_fully_connected.json"));
        JSONObject provinceAdjacencyMatrix = new JSONObject(content);
        return provinceAdjacencyMatrix.getJSONObject(province1).getBoolean(province2);
      }

    public void move(Town t) {
        t.addArmy(this);
        currentlyOn.removeArmy(this);
        currentlyOn = t;
    }

    public void resetMoves() {
        for(Unit u: units) {
            u.resetMoves();
        }
    }

    public void reduceMovePoints(int amount) {
        for (Unit u: units) {
            u.setMovesLeft(u.getMovesLeft() - amount);
        }
    }

}