package unsw.gloriaromanus.Model;

import javafx.scene.control.TextArea;

public class MultiplierAbility extends Ability {
    private double multiplier;

    public MultiplierAbility(String stat, String number) {
        super(stat);
        try {
            multiplier = Double.parseDouble(number);
        } catch (NumberFormatException e) {
            if (number.contains("/")) {
                try {
                    int divIndex = number.indexOf("/");
                    int numerator = Integer.parseInt(number.substring(0, divIndex));
                    int denominator = Integer.parseInt(number.substring(divIndex + 1));
                    multiplier = 1.0 * numerator / denominator;
                } catch (NumberFormatException e2) {
                    multiplier = 1;
                }
            } else {
                multiplier = 1;
            }
        }
    }

    public MultiplierAbility(String stat, double number) {
        super(stat);
        multiplier = number;
    }

    @Override
    public void apply(Unit unit, TextArea terminal) {
        int oldValue = super.getAttrValue(unit);

        double newValue = 1.0 * oldValue * multiplier;
        if (terminal != null) {terminal.appendText(getAttrName() + " multiplied by " + multiplier);}

        if (newValue > 999) {
            newValue = 999;
        } else if (newValue < -999) {
            newValue = -999;
        }

        super.setAttrValue(unit, (int)newValue);
    }

    @Override
    public void cancel(Unit unit) {
        int oldValue = super.getAttrValue(unit);

        double newValue = 1.0 * oldValue / multiplier;

        if (newValue > 999) {
            newValue = 999;
        } else if (newValue < -999) {
            newValue = -999;
        }

        super.setAttrValue(unit, (int)newValue);
    }
}
