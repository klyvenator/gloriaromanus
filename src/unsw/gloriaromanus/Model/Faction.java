package unsw.gloriaromanus.Model;

import java.util.ArrayList;
import java.util.List;

public class Faction {
    private String name;
    private int totalWealth;
    private int totalGold;
    private List<Town> towns;

    public Faction(String name){
        this.totalGold = 0;
        this.totalWealth = 0;
        this.name = name;
        this.towns = new ArrayList<>();
    }
    
    public void addTown(Town town) {
        towns.add(town);
    }

    public Town addTown(Faction faction, String townName){
        Town newTown = new Town(faction, townName);
        addTown(newTown);
        return newTown;
    }
    private void calculateTotalWealth(){
        int total = 0;
        int tax = 0;
        for(Town t : towns){
            tax += t.wealthAfterTax();
            total += t.getWealth();
        }
        this.totalGold += tax;
        this.totalWealth = total;
    }
    public void updateWealth(){
        calculateTotalWealth();
    }
    public void setGold(int gold){
        this.totalGold = gold;
    }
    public void setwealth(int wealth){
        this.totalWealth = wealth;
    }
    public int getTotalGold(){
        return this.totalGold;
    }
    public int getTotalWealth(){
        return this.totalWealth;
    }
    public String getFactionName(){
        return this.name;
    }
    public List<Town> getTowns(){
        return this.towns;
    }

    public void reduceTrainingCount() {
        for (Town t: towns) {
            for(Unit u: t.getUnitsInTraining().keySet()) {
                Integer i = t.getUnitsInTraining().get(u);
                i--;
                if (i <= 0) {
                    t.getUnitsInTraining().remove(u);
                    t.getArmy().addUnit(u);
                }
            }
        }
    }

    public void endTurnUpdate() {
        calculateTotalWealth();
        reduceTrainingCount();
    }
}
