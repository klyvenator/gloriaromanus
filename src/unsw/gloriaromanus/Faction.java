package unsw.gloriaromanus;

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
    
    private void calculateTotalGold(){
        int total = 0;
        for(Town t : towns){
            total += t.getTaxOwed();
        }
        this.totalGold += total;
    }
    private void calculateTotalWealth(){
        int total = 0;
        for(Town t : towns){
            t.wealthAfterTax();
            total += t.getWealth();
        }
        this.totalWealth = total;
    }
    public void updateGold(){
        calculateTotalGold();
    }
    public void updateWealth(){
        calculateTotalWealth();
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

}