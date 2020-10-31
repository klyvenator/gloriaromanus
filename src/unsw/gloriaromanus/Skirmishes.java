package unsw.gloriaromanus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Skirmishes {
    private Unit unitA;
    private Unit unitB;
    private int numEngagements;
    private static final int maxEngagements = 200;
    private Engagements engagement;

    Skirmishes() {
        unitA = null;
        unitB = null;
        numEngagements = 0;
        engagement = null;
    }

    Skirmishes(Unit a, Unit b) {
        this();
        this.unitA = a;
        this.unitB = b;
    }

    private void decideEngagementType() {
        if (unitA == "melee" && unitB == "melee") {
            engagement = new MeleeEngagements();
        } else if (unitA == "ranged" && unitB == "ranged") {
            engagement = new RangedEngagements();
        } else {
            // Set up probabilities of melee / ranged engagement
            double baseMelee = 0.5; // base-level 50% chance for a melee engagement

            // Adjust according to units' speed values
            double changeMelee;
            if (unitA == "melee") {
                // A is melee
                changeMelee = unitA.getSpeed() - unitB.getSpeed();
            } else {
                // B is melee
                changeMelee = unitB.getSpeed() - unitA.getSpeed();
            }
            changeMelee *= 0.1;

            double chanceMelee = baseMelee + changeMelee;

            // Set the boundaries for the chance of a Melee engagement
            if (chanceMelee < 0.05) {
                chanceMelee = 0.05;
            } else if (chanceMelee > 0.95) {
                chanceMelee = 0.95;
            }

            // Toss the dice and decide
            Random r = new Random();
            double diceRoll = r.nextDouble();
            if (diceRoll < chanceMelee) {
                engagement = new MeleeEngagements();
            } else {
                engagement = new RangedEngagements();
            }
        }
    }
    public Unit startEngagements() {
        while (numEngagements < maxEngagements) {
            decideEngagementType();
            Units tmp = engagement.startEngagement(unitA, unitB);
            // TODO Check tmp val to determine if victory...
        }
    }
}