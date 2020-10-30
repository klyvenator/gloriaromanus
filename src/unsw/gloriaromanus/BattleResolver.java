package unsw.gloriaromanus;

import java.util.ArrayList;
import java.util.List;

public class BattleResolver {
    private Army armyA;
    private Army armyB;

    BattleResolver() {
        armyA = null;
        armyB = null;
    }

    BattleResolver(Army a, Army b) {
        this();
        this.armyA = a;
        this.armyB = b;
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
        ) {
            Skirmishes skirmish = new Skirmishes(armyA.randomlySelectUnit(), armyB.randomlySelectUnit());
            Untis tmp = skirmish.startEngagements();
        }
    }