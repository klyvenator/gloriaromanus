package unsw.gloriaromanus;

public interface Engagements {
    public Unit engage(Unit a, Unit b);
    
    public Unit route(Unit routing, Unit pursuing);
    
    // TODO Should this attack method be a member of the Unit class?
    // To make applying / removing buffs / debuffs easier?
    public Unit attack(Unit a, Unit b);
}
