package unsw.gloriaromanus;

public class RangedEngagements implements Engagements {

    @Override
    public Unit attack(Unit a, Unit b) {
        if (a.type == "melee") {
            return; // melee does not inflict damage in ranges engagements
        }
    }

    @Override
    public Unit engage(Unit a, Unit b) {
        // Decide who goes first
        attack(a, b);
        attack(b, a);
    }

    @Override
    public Unit route(Unit routing, Unit pursuing) {
        attack(pursuing, routing);
    }

    
    
}