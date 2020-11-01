package unsw.gloriaromanus;

import java.util.Random;
import unsw.gloriaromanus.Enums.Condition;

public class LeafEvaluator implements ComponentEvaluator {
    private int gold;
    private int wealth;
    private Condition condition;

    public LeafEvaluator(int gold, int wealth) {
        this.gold = gold;
        this.wealth = wealth;
        condition = randCondition();
    }
    
    public boolean conditionFulfilled(Faction faction) {
        switch (condition) {
            case CONQUER:
                //implement
                break;
            case WEALTH:
                if (faction.getTotalWealth() > wealth) {
                    return true;
                }
            case TREASURY:
                if (faction.getTotalGold() > gold) {
                    return true;
                }
            
        }
        return false;
    }

    public Condition randCondition() {
        Random random = new Random();
        switch (random.nextInt(3)) {
            case 0: return Condition.CONQUER;
            case 1: return Condition.TREASURY;
            default: return Condition.WEALTH;
        }
    }
}