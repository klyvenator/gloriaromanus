package unsw.gloriaromanus;

import java.util.ArrayList;
import java.util.List;
import unsw.gloriaromanus.Enums.Range;

/**
 * Represents a basic unit of soldiers
 * 
 * incomplete - should have heavy infantry, skirmishers, spearmen, lancers, heavy cavalry, elephants, chariots, archers, slingers, horse-archers, onagers, ballista, etc...
 * higher classes include ranged infantry, cavalry, infantry, artillery
 * 
 * current version represents a heavy infantry unit (almost no range, decent armour and morale)
 */
public abstract class Unit {

    private String name;
    private int numTroops = 100;  // the number of troops in this unit (should reduce based on depletion)
    private Range type = Range.MELEE;  // range of the unit
    private DefenseStat defense;   // armour defense
    private int morale = 10;  // resistance to fleeing
    private int speed = 10;  // ability to disengage from disadvantageous battle
    private int attack = 10;  // can be either missile or melee attack to simplify. Could improve implementation by differentiating!
    private int cost = 100;
    private int turnsToMake = 1;
    private int movementPoints;
    private List<Ability> abilities;
    private List<Buff> buffs;


    public Unit(String name) {
        this.name = name;
        this.defense = new DefenseStat();
        abilities = new ArrayList<Ability>();
        buffs = new ArrayList<Buff>();
    }

    public String getName() {
        return name;
    }

    public int getNumTroops(){
        return numTroops;
    }
    public void setNumTroops(int numTroops) {
        this.numTroops = numTroops;
    }

    public Range getType() {
        return type;
    }
    public void setType(Range type) {
        this.type = type;
    }
    
    public DefenseStat getDefense() {
        return defense;
    }
    public void setDefense(DefenseStat defense) {
        this.defense = defense;
    }

    public int getMorale() {
        return morale;
    }
    public void setMorale(int morale) {
        this.morale = morale;
    }

    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAttack() {
        return attack;
    }
    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getTurnsToMake() {
        return turnsToMake;
    }
    public void setTurnsToMake(int turnsToMake) {
        this.turnsToMake = turnsToMake;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public List<Buff> getBuffs() {
        return buffs;
    }

    public int getMovementPoints() {
        return movementPoints;
    }
    public void setMovementPoints(int movementPoints) {
        this.movementPoints = movementPoints;
    }
}
