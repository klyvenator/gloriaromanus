package unsw.gloriaromanus;

import java.util.ArrayList;
import java.util.List;

import unsw.gloriaromanus.Enums.BattleStatus;
import unsw.gloriaromanus.Enums.FightStatus;

public class BattleResolver {
    private Army armyA;
    private Army armyB;
    private Town invaderProvince;
    private Town defenderProvince;
    private BattleStatus status;

    private BattleResolver() {
        armyA = null;
        armyB = null;
        invaderProvince = null;
        defenderProvince = null;
        status = BattleStatus.FIGHTING;
    }

    public BattleResolver(Army a, Army b) {
        this();
        this.armyA = a;
        this.armyB = b;
    }

    public BattleResolver(Army a, Army b, Town defenderTown) {
        this(a, b);
        this.defenderProvince = defenderTown;
    }

    public BattleResolver(Army a, Army b, Town attackerTown, Town defenderTown) {
        this(a, b, defenderTown);
        this.invaderProvince = attackerTown;
    }

    public BattleStatus getStatus() {
        return status;
    }

    private void printStartSkirmishMessage(Skirmishes fight) {
        // TODO Print "skirmish started with A and B!"
    }
    private void printEndSkirmishMessage(Skirmishes fight) {
        FightStatus status = fight.getStatus();
        switch (status) {
            case WIN_A:
                // TODO Print A has won!
                break;
            
            case WIN_B:
                // TODO Print B has won!
            break;

            case FLEE_A:
                // TODO Print A successfully ran away!
            break;

            case FLEE_B:
                // TODO Print B successfully ran away!
            break;

            case FLEE_ALL:
                // TODO Print A & B successfully ran away!
            break;

            case DRAW:
                // TODO Print It's a draw!
            break;

            case FIGHTING: default:
                // This should never happen!
            break;
        }
    }

    /**
     * Fights the battle!
     * Returns the Army which won
     * @return winning army
     */
    public void startBattle() {
        
        // TODO Print battle start

        armyA.activateArmyAbilities();
        armyB.activateArmyAbilities();

        Unit selectedA = null, selectedB = null;

        while (
            (armyA.numAvailableUnits() > 0) &&
            (armyB.numAvailableUnits() > 0)
        ) {
            selectedA = armyA.randomlySelectAvailableUnit();
            selectedB = armyB.randomlySelectAvailableUnit();
            
            Skirmishes skirmish = new Skirmishes(selectedA, selectedB);
            
            printStartSkirmishMessage(skirmish);
            
            skirmish.activateSkirmishAbilities();

            // TODO Print Unit.toString() to print out unit name in confrontation
            skirmish.startEngagements();
            
            skirmish.cancelSkirmishAbilities();
            // TODO Cancel abilities
        
            printEndSkirmishMessage(skirmish);
            
        }

        if ((armyA.numAvailableUnits() == 0) && (armyB.numAvailableUnits() == 0)) {
            // BOTH defeated
            status = BattleStatus.DRAW;
        } else if (armyA.numAvailableUnits() == 0) {
            status = BattleStatus.WIN_B;
        } else if (armyB.numAvailableUnits() == 0) {
            status = BattleStatus.WIN_A;
        } else {
            // Shouldn't reach here
            status = BattleStatus.FIGHTING;
        }

        armyA.cancelArmyAbilities();
        armyB.cancelArmyAbilities();

    }

}