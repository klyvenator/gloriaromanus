package unsw.gloriaromanus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import unsw.gloriaromanus.Enums.Range;

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

    private boolean bothUnitsAlive() {
        return 
            unitA.getNumTroops() > 0 &&
            unitB.getNumTroops() > 0;
    }

    // TODO Add this calculation at initialisation time
    // Since speed doesn't change during a skirmish
    private double calcChanceMeleeEngagement() {
        // Set up probabilities of melee / ranged engagement
        double baseMelee = 0.5; // base-level 50% chance for a melee engagement

        // Adjust according to units' speed values
        double changeMelee;
        if (unitA.getType() == Range.MELEE) {
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

        return chanceMelee;
    }

    private void decideEngagementType() {
        if (
            unitA.getType() == Range.MELEE &&
            unitB.getType() == Range.MELEE
        ) {
            engagement = new MeleeEngagements();
        } else if (
            unitA.getType() == Range.RANGED &&
            unitB.getType() == Range.RANGED
        ) {
            engagement = new RangedEngagements();
        } else {
            
            double chanceMelee = calcChanceMeleeEngagement();

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

    private boolean isRoutingNecessary () {
        if (unitA.isBroken()) {return true;}
        if (unitB.isBroken()) {return true;}
        return false;
    }

    private double chanceOfFleeing(Unit routing, Unit pursuing) {

        double baseChance = 0.5;

        double speedVariation = 0.1 * (routing.getSpeed() - pursuing.getSpeed());

        // TODO Possible speed modifiers?

        double fleeingChance = baseChance + speedVariation;
        // Limit chance, Min = 10%, Max = 100%
        if (fleeingChance < 0.1) {
            fleeingChance = 0.1;
        } else if (fleeingChance > 1) {
            fleeingChance = 1;
        }

        return fleeingChance;
    }

    private boolean attemptToFlee(Unit routing, Unit pursuing) {
        double fleeingChance = chanceOfFleeing(routing, pursuing);

        // Toss the dice and decide
        Random r = new Random();
        double diceRoll = r.nextDouble();
        if (diceRoll < fleeingChance) {
            return true;
        } else {
            return false;
        }
    }

    private void routingActions() {
        if (!unitA.isBroken() && !unitB.isBroken()) {
            return;
        }

        if (unitA.isBroken() && unitB.isBroken()) {
            return;
        }

        Unit routing, pursuing;
        if (unitA.isBroken()) {
            routing = unitA;
            pursuing = unitB;
        } else { // (unitB.isBroken()) {
            routing = unitB;
            pursuing = unitA;
        }

        while (routing.getNumTroops() > 0) {
            
            if (attemptToFlee(routing, pursuing)) {
                break;
            }
            
            decideEngagementType();
          
            engagement.routeEngage(routing, pursuing);
        }

    }
    public Unit startEngagements() {
        
        int firstBeforeSize = 0;
        int secondBeforeSize = 0;
        int firstAfterSize = 0;
        int secondAfterSize = 0;

        while (
            bothUnitsAlive() &&
            !isRoutingNecessary() &&
            numEngagements < maxEngagements
        ) {
            decideEngagementType();
            
            firstBeforeSize = unitA.getNumTroops();
            secondBeforeSize = unitB.getNumTroops();

            Units tmp = engagement.engage(unitA, unitB);

            firstAfterSize = unitA.getNumTroops();
            secondAfterSize = unitB.getNumTroops();
            // TODO Check tmp val to determine if victory...

            unitA.attemptToBreak(
                firstBeforeSize - firstAfterSize, firstBeforeSize,
                secondBeforeSize - secondAfterSize, secondBeforeSize
            );
            unitB.attemptToBreak(
                secondBeforeSize - secondAfterSize, secondBeforeSize,
                firstBeforeSize - firstAfterSize, firstBeforeSize
            );

            if (isRoutingNecessary() && bothUnitsAlive()) {
                routingActions();
                break;
            }

        }


    }
}
