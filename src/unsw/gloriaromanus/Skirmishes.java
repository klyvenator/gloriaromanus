package unsw.gloriaromanus;

import java.util.ArrayList;
import java.util.List;

public class Skirmishes {
    private Unit unitA;
    private Unit unitB;
    private int numEngagements;
    private static final int maxEngagements = 200;
    private Engagements engagement;

    Skirmishes() {
        unitA = null;
        unitB = null;
        numEngagements = 0;
        engagement = null;
    }

    Skirmishes(Unit a, Unit b) {
        this();
        this.unitA = a;
        this.unitB = b;
    }

    private void decideEngagementType() {
        if (unitA == "melee" && unitB == "melee") {
            engagement = new MeleeEngagement();
        } else if (unitA == "ranged" && unitB == "ranged") {
            engagement = new RangedEngagement();
        } else {

        }
    }
    public Unit startEngagements() {
        while (numEngagements < maxEngagements) {
            decideEngagementType();
            Units tmp = engagement.startEngagement(unitA, unitB);
            // TODO Check tmp val to determine if victory...
        }
    }
}