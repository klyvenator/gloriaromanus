package unsw.gloriaromanus;

public interface Engagements {
    
    /**
     * An engagement between to 2 units
     * where each attack the other
     * in no particular order
     * returns a victorious unit, if applicable
     * @param a
     * @param b
     * @return unit which defeated another, if applicable
     */
    public Unit engage(Unit a, Unit b);
    
    /**
     * An engagement when one unit is routing, the other pursuing
     * Only the pursuing unit attacks
     * @param routing
     * @param pursuing
     * @return
     */
    public Unit routeEngage(Unit routing, Unit pursuing);
    
    // TODO Applying / removing buffs / debuffs
    public void attack(Unit attacker, Unit defender);
}
