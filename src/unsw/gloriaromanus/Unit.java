package unsw.gloriaromanus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private String category;
    private Range type = Range.MELEE;  // range of the unit
    private int numTroops = 100;  // the number of troops in this unit (should reduce based on depletion)
    private int attack = 10;  // can be either missile or melee attack to simplify. Could improve implementation by differentiating!
    private DefenseStat defense;   // armour defense
    private int morale = 10;  // resistance to fleeing
    private int speed = 10;  // ability to disengage from disadvantageous battle
    private int cost = 100;
    private int turnsToMake = 1;
    private int movementPoints;
    
    private Town currentlyOn;
    private Army army;
    private Faction faction;
    
    private Ability ability;

    private boolean broken;

    public Unit(String name) {
        this.name = name;
        this.type = Range.MELEE;
        this.defense = new DefenseStat();

        this.army = null;

        broken = false;
    }

    /*
        Getters and Setters
    */

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

    public int getDefenseMelee() {
        return defense.getMeleeDef();
    }

    public int getDefenseRanged() {
        return defense.getRangedDef();
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

    public Ability getAbility() {
        return ability;
    }
    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    public boolean isAbilityType(String type) {
        if (ability == null) {
            return false;
        }
        
        switch (type) {
            case "faction":
                if (ability.getClass() == FactionAbility.class) {
                    return true;
                } else {
                    return false;
                }
                break;
            case "army":
                if (ability.getClass() == ArmyAbility.class) {
                    return true;
                } else {
                    return false;
                }
                break;
            case "skirmish":
                if (ability.getClass() == SkirmishAbility.class) {
                    return true;
                } else {
                    return false;
                }
                break;

            case "engagement":
                if (ability.getClass() == EngagementAbility.class) {
                    return true;
                } else {
                    return false;
                }
                break;

            case "unit":
                if (ability.getClass() == UnitAbility.class) {
                    return true;
                } else {
                    return false;
                }
                break;
            
            default:
                return false;
        }
    }

    public void activateAbility() {
        if (ability != null) {
            ability.apply();
        }
    }

    public void cancelAbility() {
        if (ability != null) {
            ability.cancel();
        }
    }
    
    public int getMovementPoints() {
        return movementPoints;
    }
    public void setMovementPoints(int movementPoints) {
        this.movementPoints = movementPoints;
    }

    public Town getCurrentlyOn() {
        return currentlyOn;
    }
    public void setCurrentlyOn(Town currentlyOn) {
        this.currentlyOn = currentlyOn;
    }

    public Faction getFaction() {
        return faction;
    }
    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    // Actual Methods

    public boolean isBroken() {
        return broken;
    }
    
    public void setBroken(boolean broken) {
        this.broken = broken;
    }

    private double chanceOfBreaking(
        int thisCasualties, int thisStartSize,
        int otherCasualties, int otherStartSize
    ) {
        // Base level probability
        // TODO Apply morale modifier adjustments
        // >>> For beserker
        // if (getMorale() == Integer.MAX_VALUE) {
        //     return 0;
        // }

        double baseChance = 1 - (getMorale() * 0.1);

        double casualtiesChance =
        (((double) thisCasualties) / thisStartSize) /
        (((double) otherCasualties) / otherStartSize);

        double breakingChance = baseChance + casualtiesChance;
        
        // Limit chance, Min = 5%, Max = 100%
        if (breakingChance < 0.05) {
            breakingChance = 0.05;
        } else if (breakingChance > 1) {
            breakingChance = 1;
        }

        return breakingChance;
    }

    /**
     * Attempt to break this unit using adjustments
     * from proportions of this and enemy unit lost
     * @param thisCasualties
     * @param thisStartSize
     * @param otherCasualties
     * @param otherStartSize
     */
    public void attemptToBreak(
        int thisCasualties, int thisStartSize,
        int otherCasualties, int otherStartSize
    ) {
        double breakingChance = chanceOfBreaking(
            thisCasualties, thisStartSize,
            otherCasualties, otherStartSize
        );
        
        // Toss the dice and decide
        Random r = new Random();
        double diceRoll = r.nextDouble();
        if (diceRoll < breakingChance) {
            broken = true;
        } else {
            broken = false;
        }

    }
}
