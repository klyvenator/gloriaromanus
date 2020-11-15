package unsw.gloriaromanus.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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


}