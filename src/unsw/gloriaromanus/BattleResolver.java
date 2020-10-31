package unsw.gloriaromanus;

import java.util.ArrayList;
import java.util.List;

public class BattleResolver {
    private Army armyA;
    private Army armyB;
    private Towns invaderProvince;
    private Towns defenderProvince;

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
        while (
            (armyA.getNumUnits() > 0) &&
            (armyB.getNumUnits() > 0)
            // TODO Maybe some sort of routing condition?
            // TODO How to identify a draw?
        ) {
            Skirmishes skirmish = new Skirmishes(armyA.randomlySelectUnit(), armyB.randomlySelectUnit());
            // TODO: Print "skirmish started with A and B!"
            // TODO: Unit.toString() to print out unit confrontation
            Units tmp = skirmish.startEngagements();
        }
    }