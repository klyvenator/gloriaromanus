package unsw.gloriaromanus.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Town {
    
    public Faction faction; 
    public String townName;
    private int wealth;
    private Tax tax;
    private Map<Unit, Integer> unitsInTraining;
    public Army army;

    public Town(Faction faction, String townName){
        this.faction = faction;
        this.townName = townName;
        this.tax = new Tax(); // set tax rate to low
        this.wealth = 10; // set initial province wealth to 10
        this.army = new Army();
    }
    public Town(String townName) {
        this.faction = null;
        this.townName = townName;
        this.tax = new Tax(); // set tax rate to low
        this.wealth = 10; // set initial province wealth to 10
    }
    public void setWealth(int wealth){
        this.wealth = wealth;
    }
    public int getWealth(){
        return this.wealth;
    }
    public String getFaction(){
        return this.faction.getFactionName();
    }
    public void setFaction(Faction faction) {
        this.faction = faction;
    }
    public String getTownName(){
        return this.townName;
    }
    public void addUnit(Unit unit) {
        army.addUnit(unit);
    }
    public Army getArmy(){
        return this.army;
    }
    private int getTaxOwed(){
        int currWealth = getWealth();
        if( currWealth <= 0 ) return 0;

        int taxOwed = (int)Math.round( this.tax.getTaxRate() * currWealth);
        if( taxOwed < 1 ) return 0; 
        else return taxOwed;
    }
    private void growWealth(){
        int growth = tax.getTaxGrowth();
        int total = getWealth() + growth;
        if( total <= 0 ){
            this.wealth = 0;
        }else {
            this.wealth = total;
        }
    }
    // calculates and sets wealth and then returns
    // how much tax you owe Big Brother
    public int wealthAfterTax(){
        growWealth();
        int tax = 0;
        int currWealth = getWealth();
        if( currWealth <= 0 ){
            return tax;
        }else{ 
            tax = getTaxOwed();
            int total = currWealth - tax;
            if( total <= 0 ){ 
                this.wealth = 0;
            }else{
                this.wealth = total;
            }
        }
        return tax;
    }
    public String getTaxStatus(){
        return tax.getTaxType();
    }
    public void updateTaxStatus(String taxType){
        tax.updateTax(taxType);
    }

    
}
