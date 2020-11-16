package unsw.gloriaromanus.Model;

import javafx.scene.control.TextArea;

public class ScalarAbility extends Ability {
    private int value;

    public ScalarAbility(String stat, String number) {
        super(stat);
        try {
            value = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            value = 0;
        }
    }

    public ScalarAbility(String stat, int number) {
        super(stat);
        value = number;
    }

    @Override
    public void apply(Unit unit, TextArea terminal) {
        int oldValue = super.getAttrValue(unit);

        int newValue = oldValue + value;
        if (terminal != null) {terminal.appendText(getAttrName() + " increased (scalar) by " + value);}

        if (newValue > 999) {
            newValue = 999;
        } else if (newValue < -999) {
            newValue = -999;
        }

        super.setAttrValue(unit, newValue);
    }

    @Override
    public void cancel(Unit unit) {
        int oldValue = super.getAttrValue(unit);

        int newValue = oldValue - value;

        if (newValue > 999) {
            newValue = 999;
        } else if (newValue < -999) {
            newValue = -999;
        }

        super.setAttrValue(unit, newValue);
    }

    
}
