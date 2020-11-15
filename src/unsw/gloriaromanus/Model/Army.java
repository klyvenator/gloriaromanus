package unsw.gloriaromanus.Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import org.json.JSONObject;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;

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
    }

    public void addNumAvailableUnitsListener(ChangeListener<? super Number> listener) {
        numAvailableUnits.addListener(listener);
    }

    public void removeNumAvailableUnitsListener(ChangeListener<? super Number> listener) {
        numAvailableUnits.removeListener(listener);
    }

    public void removeUnit(Unit u) {
        units.remove(u);
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

    public void activateArmyAbilities() {
        for (Unit unit : units) {
            if (unit != null) {
                unit.activateAbility(ArmyTarget.class);
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

    public boolean canMoveTo(Town t) throws IOException{
        String content = Files.readString(Paths.get("src/unsw/gloriaromanus/province_adjacency_matrix_fully_connected.json"));
        JSONObject provinceAdjacencyMatrix = new JSONObject(content);

        int lowestMovement  = findLowestMovement();
        if (lowestMovement > findShortestDistance(0, provinceAdjacencyMatrix, currentlyOn.getTownName(), t.getTownName())) {
            return true;
        }
        return false;
    }

    public int findShortestDistance(int shortest, JSONObject matrix, String root, String dest) {
        Queue<String> queue = new LinkedList<String>();
        String s = null;
        shortest++;
        for (String province: matrix.getJSONObject(root).keySet()) {
            queue.add(province);
        }
        s = queue.peek();
        queue.remove();
        
        while (!queue.isEmpty()) {
            if (s.equals(dest)) {
                return shortest;
            }
            else {
                findShortestDistance(shortest, matrix, root, dest);
            }
        }

        return shortest;
    }

    public int findLowestMovement() {
        int lowest = Integer.MAX_VALUE;
        for (Unit u : units) {
            if (u.getMovementPoints() < lowest) {
                lowest = u.getMovementPoints();
            }
        }
        return lowest;
    }

    private boolean confirmIfProvincesConnected(String province1, String province2) throws IOException {
        String content = Files.readString(Paths.get("src/unsw/gloriaromanus/province_adjacency_matrix_fully_connected.json"));
        JSONObject provinceAdjacencyMatrix = new JSONObject(content);
        return provinceAdjacencyMatrix.getJSONObject(province1).getBoolean(province2);
      }

    public void move(Town t) {
        t.addArmy(this);
        currentlyOn.removeArmy(this);
        currentlyOn = t;
    }

}