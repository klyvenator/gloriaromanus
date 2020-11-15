package unsw.gloriaromanus.Model;

import java.util.ArrayList;
import java.util.List;

public class UnitTarget implements TargetType {

    private Unit unit;

    public UnitTarget() {
        unit = null;
    }
    
    @Override
    public void addUnits(List<Unit> units) {
        Unit tmp = units.get(0);
        if (tmp != null) {
            unit = tmp;
        }
    }

    @Override
    public List<Unit> getUnits() {
        List<Unit> tmp = new ArrayList<Unit>();
        tmp.add(unit);
        return tmp;
    }
    
}
