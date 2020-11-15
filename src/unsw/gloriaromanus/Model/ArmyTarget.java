package unsw.gloriaromanus.Model;

import java.util.ArrayList;
import java.util.List;

public class ArmyTarget implements TargetType {

    private List<Unit> units;

    public ArmyTarget() {
        units = null;
    }

    @Override
    public void addUnits(List<Unit> units) {
        this.units = new ArrayList<Unit>(units);
        removeNullUnits();
    }

    private void removeNullUnits() {
        for (Unit unit : units) {
            if (unit == null) {
                units.remove(unit);
            }
        }
    }

    @Override
    public List<Unit> getUnits() {
        return units;
    }
    
}
