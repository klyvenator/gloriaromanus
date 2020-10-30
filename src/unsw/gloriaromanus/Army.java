package unsw.gloriaromanus;

import java.util.ArrayList;
import java.util.List;

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
    Unit randomlySelectUnit() {
        return null;
    }
}