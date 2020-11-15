package unsw.gloriaromanus.Model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Faction {
    private String name;
    private IntegerProperty totalWealth;
    private IntegerProperty totalGold;
    private List<Town> towns;

    public Faction(String name){
        totalGold = new SimpleIntegerProperty();
        totalGold.set(5000);
        totalWealth = new SimpleIntegerProperty();
        totalWealth.set(5000);
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
        totalGold.set(totalGold.get() + tax);
        totalWealth.set(total);
    }
    public void updateWealth(){
        calculateTotalWealth();
    }
    public void setGold(int gold){
        totalGold.set(gold);
    }
    public void setwealth(int wealth){
        totalWealth.set(wealth);
    }
    public int getTotalGold(){
        return totalGold.get();
    }
    public int getTotalWealth(){
        return totalWealth.get();
    }
    public String getFactionName(){
        return this.name;
    }
    public List<Town> getTowns(){
        return this.towns;
    }

    public void reduceTrainingCount() {
        List<Unit> removeList = new ArrayList<Unit>();
        for (Town t: towns) {
            for(Unit u: t.getUnitsInTraining().keySet()) {
                Integer i = t.getUnitsInTraining().get(u);
                i--;
                if (i == 0) {
                    removeList.add(u);
                    t.getArmy().addUnit(u);
                }
            }
            // Avoid concurrency exception
            for (Unit u2 : removeList) {
                t.getUnitsInTraining().remove(u2);
            }
        }


    }

    public IntegerProperty getGoldProperty() {
        return totalGold;
    }

    public IntegerProperty getWealthProperty() {
        return totalWealth;
    }

    public void endTurnUpdate() {
        calculateTotalWealth();
        reduceTrainingCount();
        resetUnitMoves();
    }

    public void resetUnitMoves() {
        for(Town t: towns) {
            t.getArmy().resetMoves();
        }
    }
}
