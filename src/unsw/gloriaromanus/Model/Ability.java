package unsw.gloriaromanus.Model;

public abstract class Ability {
    private String attrName;

    public Ability(String attrName) {
        this.attrName = attrName;
    }

    protected int getAttrValue(Unit unit) {
        return unit.getAttrValue(attrName);
    }

    protected void setAttrValue(Unit unit, int value) {
        unit.setAttrValue(attrName, value);
    }

    public abstract void apply(Unit unit);
    public abstract void cancel(Unit unit);

    

}
