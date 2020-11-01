package unsw.gloriaromanus;

import unsw.gloriaromanus.Enums.Condition;

public class LeafEvaluator implements ComponentEvaluator {
    private int gold;
    private int wealth;
    private Condition condition;

    public LeafEvaluator(int gold, int wealth, Condition condition) {
        this.gold = gold;
        this.wealth = wealth;
        this.condition = condition;
    }
    
    public boolean conditionFulfilled(Faction faction) {
        switch (condition) {
            case CONQUER:
                //implement
                return true;
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

    public Condition getCondition() {
        return condition;
    }

}