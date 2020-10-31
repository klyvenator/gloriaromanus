package unsw.gloriaromanus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Army {
    private List<Unit> units;
    private Towns currentlyOn;

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

    public List<Unit> getAvailableUnits() {
        return null;
    }
    /**
     * Returns a uniformly randomly selected unit
     * which is:
     * <ol>
     * <li> Alive
     * <li> Not Broken
     * </ol>
     * @return
     */
    public Unit randomlySelectAvailableUnit() {

        // If this army has no units
        if (units.size() == 0) {
            return null;
        }

        // TODO
        return null;
    }
}