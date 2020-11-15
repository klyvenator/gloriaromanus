package unsw.gloriaromanus.Model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class AbilityContainer {
    private String name;
    private List<Ability> abilities;
    private TargetType target;

    public AbilityContainer() {
        abilities = new ArrayList<Ability>();
    }

    public AbilityContainer(String name, String targetType, JSONObject buffs) {
        this();
        this.name = name;

        assignTargetType(targetType);

        for (String stat : buffs.keySet()) {
            String value = buffs.getString(stat);
            Ability statAbility = createStatAbility(stat, value);
            if (statAbility != null) {abilities.add(statAbility);}
        }

    }

    private Ability createStatAbility(String stat, String value) {
        char valueType = value.charAt(0);
        String number = value.substring(1);
        if ((valueType == '+') || (valueType == '-')) {
            return new ScalarAbility(stat, number);
        } else if (valueType == 'x') {
            return new MultiplierAbility(stat, number);
        } else {
            return null;
        }
    }

    private void assignTargetType(String targetType) {
        switch (targetType) {
            case "unit":
                target = new UnitTarget();
                break;
            case "army":
                target = new ArmyTarget();
                break;

            default:
                target = null;
                break;
        }
    }

    public String getName() {
        return name;
    }

    public <T> void activate(Class<T> targetClass) {
        if (target == null) {return;}
        if (target.getClass() == targetClass) {
            apply(target.getUnits());
        }
    }

    public <T> void deactivate(Class<T> targetClass) {
        if (target == null) {return;}
        if (target.getClass() == targetClass) {
            cancel(target.getUnits());
        }
    }

    public void apply(List<Unit> units) {
        if (units == null) {return;}
        for (Unit unit : units) {
            if (unit == null) {continue;}
            for (Ability ability : abilities) {
                ability.apply(unit);
            }
        }
    }

    public void cancel(List<Unit> units) {
        if (units == null) {return;}
        for (Unit unit : units) {
            if (unit == null) {continue;}
            for (Ability ability : abilities) {
                ability.cancel(unit);
            }
        }
    }

    public void setTarget(Unit unit) {
        if (
            (target == null) ||
            (unit == null)
        ) {return;}
        if (target.getClass() == UnitTarget.class) {
            ArrayList<Unit> unitList = new ArrayList<Unit>();
            unitList.add(unit);
            target.addUnits(unitList);
        }
    }

    public void setTarget(Army army) {
        if (
            (target == null) ||
            (army == null)
        ) {return;}
        if (target.getClass() == ArmyTarget.class) {
            target.addUnits(army.getAllUnits());
        }
    }
}
