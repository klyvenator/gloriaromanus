package unsw.gloriaromanus.Model;

import unsw.gloriaromanus.Model.Enums.Condition;

public class LeafEvaluator implements ComponentEvaluator {
    private int gold;
    private int wealth;
    private int totalProvinces;
    private Condition condition;

    public LeafEvaluator(int gold, int wealth, Condition condition, int totalProvinces) {
        this.gold = gold;
        this.wealth = wealth;
        this.condition = condition;
        this.totalProvinces = totalProvinces;
    }
    
    public boolean conditionFulfilled(Faction faction) {
        switch (condition) {
            case CONQUER:
                if (faction.getTowns().size() == totalProvinces) {
                    return true;
                }
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