package unsw.gloriaromanus;

import java.util.ArrayList;
import java.util.List;

public class BattleResolver {
    private Army armyA;
    private Army armyB;
    private Town invaderProvince;
    private Town defenderProvince;

    BattleResolver() {
        armyA = null;
        armyB = null;
        invaderProvince = null;
        defenderProvince = null;
    }

    BattleResolver(Army a, Army b) {
        this();
        this.armyA = a;
        this.armyB = b;
    }

    BattleResolver(Army a, Army b, Towns attackerTown, Towns defenderTown) {
        this(a, b);
        this.invaderProvince = attackerTown;
        this.defenderProvince = defenderTown;
    }

    /**
     * Fights the battle!
     * Returns the Army which won
     * @return winning army
     */
    public Army startBattle() {
        
        // TODO Print battle start
        Unit selectedA = null, selectedB = null;

        while (
            (armyA.numAvailableUnits() > 0) &&
            (armyB.numAvailableUnits() > 0)
            // TODO How to identify a draw?
        ) {
            selectedA = armyA.randomlySelectAvailableUnit();
            selectedB = armyB.randomlySelectAvailableUnit();
            Skirmishes skirmish = new Skirmishes(selectedA, selectedB);
            
            // TODO Print "skirmish started with A and B!"
            
            // TODO Print Unit.toString() to print out unit confrontation
            Unit winner = skirmish.startEngagements();
            // TODO Determine:
            //  1. Winner
            //  2. Both alive & broken units =>
            //      one unit broken
            //      both units broken
            //  3. Both alive, neither broken
            //      Draw
            if (winner != null) {
                // TODO Print winner won!
            } {
                if (skirmish.bothUnitsBroken()) {
                    // TODO both fleed successfully
                }   // TODO Either fleed succesffully
                    // TODO Draw
            }
        }

        Army winningArmy = null;
        if ((armyA.numAvailableUnits() == 0) && (armyB.numAvailableUnits() == 0)) {
            // BOTH defeated
            winningArmy = null;
        } else if (armyA.numAvailableUnits() == 0) {
            winningArmy = armyB;
        } else if (armyB.numAvailableUnits() == 0) {
            winningArmy = armyA;
        } else {
            // Shouldn't reach here
        }

        return winningArmy;
    }