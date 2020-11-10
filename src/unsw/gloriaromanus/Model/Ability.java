package unsw.gloriaromanus.Model;

public abstract class Ability {
    private Unit unit;

    Ability() {
        unit = null;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
    
    public abstract void apply();
    public abstract void cancel();

}
