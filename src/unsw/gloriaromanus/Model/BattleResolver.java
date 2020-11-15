package unsw.gloriaromanus.Model;

import java.util.concurrent.TimeUnit;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextArea;
import unsw.gloriaromanus.Model.Enums.BattleStatus;
import unsw.gloriaromanus.Model.Enums.FightStatus;

public class BattleResolver {
    private Army armyA; // attacker
    private Army armyB; // defender
    private IntegerProperty armyAsize;
    private IntegerProperty armyBsize;
    private DoubleProperty unitAbinding;
    private DoubleProperty unitBbinding;
    private ChangeListener<? super Number> listenerA;
    private ChangeListener<? super Number> listenerB;
    private Town invaderProvince;
    private Town defenderProvince;
    private BattleStatus status;
    private TextArea terminal;

    private BattleResolver() {
        armyA = null;
        armyB = null;
        armyAsize = new SimpleIntegerProperty();
        armyBsize = new SimpleIntegerProperty();
        unitAbinding = null;
        unitBbinding = null;
        invaderProvince = null;
        defenderProvince = null;
        status = BattleStatus.FIGHTING;
        terminal = null;
    }

    public BattleResolver(Army a, Army b) {
        this();
        this.armyA = a;
        this.armyB = b;
        armyAsize.setValue(a.numAvailableUnits());
        armyBsize.setValue(b.numAvailableUnits());
    }

    public BattleResolver(Army a, Army b, TextArea terminal) {
        this(a, b);
        this.terminal = terminal;
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

    public void updateArmyNumAvailableUnits() {
        armyA.updateNumAvailableUnits();
        armyB.updateNumAvailableUnits();
    }

    public void setArmyBindings(DoubleProperty p1, DoubleProperty p2) {
        p1.bind(armyAsize);
        p2.bind(armyBsize);
    }

    // Deprecated
    public void removeArmyBindings() {
        armyAsize = new SimpleIntegerProperty();
        armyAsize.setValue(armyA.numAvailableUnits());

        armyBsize = new SimpleIntegerProperty();
        armyBsize.setValue(armyB.numAvailableUnits());
    }

    public void setUnitBindings(DoubleProperty p1, DoubleProperty p2) {
        unitAbinding = p1;
        unitBbinding = p2;
    }

    public void setNumTroopsListeners(ChangeListener<? super Number> listener1, ChangeListener<? super Number> listener2) {
        this.listenerA = listener1;
        this.listenerB = listener2;
    }

    private void writeToTerminal(String msg) {
        if (terminal != null) {
            terminal.appendText(msg + "\n");
        }
    }

    private void printStartSkirmishMessage(Skirmishes fight) {
        // TODO Print "skirmish started with A and B!"
        writeToTerminal("Skirmish START!");
    }
    private void printEndSkirmishMessage(Skirmishes fight) {
        FightStatus status = fight.getStatus();
        switch (status) {
            case WIN_A:
                writeToTerminal("Unit A wins!");
                break;
            
            case WIN_B:
                writeToTerminal("Unit B wins!");
                break;

            case FLEE_A:
                writeToTerminal("Unit A successfully routed!");
                break;

            case FLEE_B:
                writeToTerminal("Unit B successfully routed!");
                break;

            case FLEE_ALL:
                writeToTerminal("Both units successfully routed!");
                break;

            case DRAW:
                writeToTerminal("It's a draw!");
                break;

            case FIGHTING: default:
                // This should never happen!
            break;
        }
        writeToTerminal("-------------------------");
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
            
            Skirmishes skirmish = new Skirmishes(selectedA, selectedB, terminal);
            
            printStartSkirmishMessage(skirmish);
            
            skirmish.activateSkirmishAbilities();

            skirmish.setUnitBindings(unitAbinding, unitBbinding);

            //skirmish.setNumTroopsListeners(listenerA, listenerB);
            // TODO skirmish.setUnitBindings(..., ...)
            // TODO Print Unit.toString() to print out unit name in confrontation
            skirmish.startEngagements();

            //skirmish.removeNumTroopsListeners(listenerA, listenerB);
            
            skirmish.removeUnitBindings(unitAbinding, unitBbinding);

            // TODO skirmish.removeUnitBindings()
            
            skirmish.cancelSkirmishAbilities();
        
            printEndSkirmishMessage(skirmish);

            updateArmyNumAvailableUnits();

            // Wait after each skirmish
            try {TimeUnit.SECONDS.sleep(2);}
            catch (Exception e) {
                // just continue
            }
            
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

        // Wait after each battle
        try {TimeUnit.SECONDS.sleep(3);}
        catch (Exception e) {
            // just continue
        }

    }

}