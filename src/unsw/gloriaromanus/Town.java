package unsw.gloriaromanus;

import unsw.gloriaromanus.Tax;

public class Town {
    
    private Faction faction;
    private int wealth;
    private Tax tax;

    public Town(Faction faction){
        this.faction = faction;
        this.tax = new Tax(); // set tax rate to low
        this.wealth = 10; // set initial province wealth to 10
    }
    public int getWealth(){
        return this.wealth;
    }
    
    public int getTaxOwed(){
        if(this.wealth <= 0 ){
            return 0;
        }
        int taxOwed = (int)Math.round( this.tax.getTaxRate() * this.wealth);
        return taxOwed;
    }

    public void wealthGrowth(){
        int growth = tax.getTaxGrowth();
        if( this.wealth + growth <= 0 ){
            this.wealth = 0;
        }else {
            this.wealth = this.wealth + growth;
        }
    }
    
}
