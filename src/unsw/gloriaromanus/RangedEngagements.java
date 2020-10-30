package unsw.gloriaromanus;

public class RangedEngagements implements Engagements {

    @Override
    public Unit attack(Unit a, Unit b) {
        if (a.type == "melee") {
            return; // melee does not inflict damage in ranges engagements
        }
    }

    @Override
    public Unit startEngagement(Unit a, Unit b) {
        attack(a, b);
        attack(b, a);
    }
    
}