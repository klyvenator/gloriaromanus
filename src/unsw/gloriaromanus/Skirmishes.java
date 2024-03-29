package unsw.gloriaromanus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import unsw.gloriaromanus.Enums.Range;
import unsw.gloriaromanus.Enums.FightStatus;

public class Skirmishes {
    private Unit unitA;
    private Unit unitB;
    private int numEngagements;
    private static final int maxEngagements = 200;
    private Engagements engagement;
    private FightStatus status;

    private Skirmishes() {
        unitA = null;
        unitB = null;
        numEngagements = 0;
        engagement = null;
        status = FightStatus.FIGHTING;
    }

    public Skirmishes(Unit a, Unit b) {
        this();
        this.unitA = a;
        this.unitB = b;
    }

    public FightStatus getStatus() {
        return status;
    }

    public void printAfterEngagementMessage(int firstBeforeSize, int secondBeforeSize) {
        System.out.println("Unit A dealt " + (secondBeforeSize - unitB.getNumTroops()) + " damage!");
        System.out.println("Unit B dealt " + (firstBeforeSize - unitA.getNumTroops()) + " damage!");
    }

    public void printEngagementType() {
        System.out.print("It's a ");
        if (engagement.getClass() == RangedEngagements.class) {
            System.out.print("Ranged Engagement");
        } else if (engagement.getClass() == MeleeEngagements.class) {
            System.out.print("Melee Engagement");
        }
        System.out.println("!");
    }

    public void printUnitsHealth() {
        System.out.println("UnitA: " + unitA.getNumTroops());
        System.out.println("UnitB: " + unitB.getNumTroops());
    }

    public boolean bothUnitsAlive() {
        return 
            (unitA.getNumTroops() > 0) &&
            (unitB.getNumTroops() > 0);
    }

    public boolean bothUnitsDefeated() {
        return 
            (unitA.getNumTroops() <= 0) &&
            (unitB.getNumTroops() <= 0);
    }

    public boolean eitherUnitsBroken () {
        return unitA.isBroken() || unitB.isBroken();
    }

    public void activateSkirmishAbilities() {
        if (unitA.isAbilityType("skirmish")) {
            unitA.activateAbility();
        }
        if (unitB.isAbilityType("skirmish")) {
            unitB.activateAbility();
        }
    }

    public void cancelSkirmishAbilities() {
        if (unitA.isAbilityType("skirmish")) {
            unitA.cancelAbility();
        }
        if (unitB.isAbilityType("skirmish")) {
            unitB.cancelAbility();
        }
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

    /**
     * Perform necessary routing actions
     * @return winner, if applicable
     */
    private Unit routingActions() {
        // both units intact - no routing actions
        if (!unitA.isBroken() && !unitB.isBroken()) {
            status = FightStatus.FIGHTING;
            return null;
        }

        // Both broken, and flee without inflicitng further damage
        if (unitA.isBroken() && unitB.isBroken()) {
            status = FightStatus.FLEE_ALL;
            return null;
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
            
            System.out.println("unit is trying to route...");
            
            if (attemptToFlee(routing, pursuing)) {
                decideStatus();
                return null;
            }
            
            decideEngagementType();
          
            Unit winner = engagement.routeEngage(routing, pursuing);
            
            if (winner != null) {
                decideStatus();
                return winner;
            }
            
        }

        return pursuing;
        /*
            Either:
            1. Fleeing succesful
            2. Routing unit defeated
        */
    }

    /**
     * Start a series of engagements between 2 units
     * With possible breaking and routing
     * @return winner, if applicable
     */
    public Unit startEngagements() {
        
        int firstBeforeSize = 0;
        int secondBeforeSize = 0;
        int damageA = 0;
        int damageB = 0;

        Unit winner = null;
        while (
            bothUnitsAlive() &&
            !eitherUnitsBroken() && // If either broken, assume fleed
            numEngagements < maxEngagements
        ) {
            decideEngagementType();
            printEngagementType();

            firstBeforeSize = unitA.getNumTroops();
            secondBeforeSize = unitB.getNumTroops();

            unitA.activateAbility();
            unitB.activateAbility();

            System.out.println("Units health before engagement:");
            printUnitsHealth();

            winner = engagement.engage(unitA, unitB);

            printAfterEngagementMessage(firstBeforeSize, secondBeforeSize);

            System.out.println("Units health after engagement:");
            printUnitsHealth();

            unitA.cancelAbility();
            unitB.cancelAbility();

            /*
                Cases:
                1. One unit won, other defeated
                2. Both alive
                3. Both defeated
            */

            if (winner == unitA) {
                status = FightStatus.WIN_A;
            } else if (winner == unitB) {
                status = FightStatus.WIN_B;
            } else if (bothUnitsAlive()) {
                status = FightStatus.FIGHTING;
                // Keep fighting
            } else {
                // Both units dead - no winner
                status = FightStatus.DRAW;
            }

            if (status != FightStatus.FIGHTING) {
                return winner;
            }

            damageA = firstBeforeSize - unitA.getNumTroops();
            damageB = secondBeforeSize - unitB.getNumTroops();

            // Try breaking
            unitA.attemptToBreak(
                damageA, firstBeforeSize,
                damageB, secondBeforeSize
            );
            unitB.attemptToBreak(
                damageB, secondBeforeSize,
                damageA, firstBeforeSize
            );

            if (eitherUnitsBroken() && bothUnitsAlive()) {
                return routingActions();
            }

            // Just finished an engagement
            numEngagements++;

            // Give some time between engagements
            try {TimeUnit.SECONDS.sleep(1);}
            catch (Exception e) {
                // just continue
            }

        }

        decideStatus();

        if (bothUnitsAlive() && !eitherUnitsBroken()) {
            status = FightStatus.DRAW;
            return null;
        }

        return winner;
    }

    private void decideStatus() {
        if (bothUnitsAlive()) {
            if (unitA.isBroken() && unitB.isBroken()) {
                status = FightStatus.FLEE_ALL;
            } else if (unitA.isBroken()) {
                status = FightStatus.FLEE_A;
            } else if (unitB.isBroken()) {
                status = FightStatus.FLEE_B;
            } else { // neither broken
                status = FightStatus.FIGHTING;
            }
        } else if (bothUnitsDefeated()) {
            status = FightStatus.DRAW;
        } else if (unitA.getNumTroops() > 0) {
            status = FightStatus.WIN_A;
        } else {// unitB.getNumTroops() > 0
            status = FightStatus.WIN_B;
        }
    }
}
