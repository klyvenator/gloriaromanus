package unsw.gloriaromanus;

public abstract class Engagements {
    
    /**
     * An engagement between to 2 units
     * where each attack the other
     * in no particular order
     * returns a victorious unit, if applicable
     * @param a
     * @param b
     * @return unit which defeated another, if applicable
     */
    public Unit engage(Unit a, Unit b) {
        
        if (a.isAbilityType("engagement")) {
            a.activateAbility();
        }
        if (b.isAbilityType("engagement")) {
            b.activateAbility();
        }
        
        // No special order
        attack(a, b);
        attack(b, a);

        if (a.isAbilityType("engagement")) {
            a.cancelAbility();
        }
        if (b.isAbilityType("engagement")) {
            b.cancelAbility();
        }
    
        if ((a.getNumTroops() <= 0) && (b.getNumTroops() <= 0)) {
            // Both defeated, no winner
            return null;
        } else if ((a.getNumTroops() > 0) && (b.getNumTroops() > 0)) {
            // both alive - no winner
            return null;
        } else if (a.getNumTroops() > 0) {
            return a;
        } else {
            return b;
        }
    }
    
    /**
     * An engagement when one unit is routing, the other pursuing
     * Only the pursuing unit attacks
     * @param routing
     * @param pursuing
     * @return
     */
    public Unit routeEngage(Unit routing, Unit pursuing) {
        
        if (pursuing.isAbilityType("engagement")) {
            pursuing.activateAbility();
        }

        attack(pursuing, routing);

        if (pursuing.isAbilityType("engagement")) {
            pursuing.cancelAbility();
        }

        if (routing.getNumTroops() <= 0) {
            return pursuing;
        } else {
            return null;
        }
    }
    
    // TODO Applying / removing buffs / debuffs
    public abstract void attack(Unit attacker, Unit defender);
}
