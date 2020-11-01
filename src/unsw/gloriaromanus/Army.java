package unsw.gloriaromanus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Army {
    private List<Unit> units;
    private Town currentlyOn;

    Army() {
        units = new ArrayList<Unit>();
        currentlyOn = null;
    }

    // Getters and Setters

    public int getNumUnits() {
        return units.size();
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
        
        int num = 0;
        for (Unit unit : units) {
            if (
                unit.getNumTroops() > 0 &&
                !unit.isBroken()
            ) {
                num++;
            }
        }
        return num;
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
}