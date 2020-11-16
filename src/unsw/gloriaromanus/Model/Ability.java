package unsw.gloriaromanus.Model;

import javafx.scene.control.TextArea;

public abstract class Ability {
    private String attrName;

    public Ability(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrName() {
        return attrName;
    }
    
    protected int getAttrValue(Unit unit) {
        return unit.getAttrValue(attrName);
    }

    protected void setAttrValue(Unit unit, int value) {
        unit.setAttrValue(attrName, value);
    }

    public abstract void apply(Unit unit, TextArea terminal);
    public abstract void cancel(Unit unit);

    

}
