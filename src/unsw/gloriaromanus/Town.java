package unsw.gloriaromanus;


public class Town {
    
    public Faction faction; 
    public String townName;
    private int wealth;
    private Tax tax;


    public Town(Faction faction, String townName){
        this.faction = faction;
        this.townName = townName;
        this.tax = new Tax(); // set tax rate to low
        this.wealth = 10; // set initial province wealth to 10
    }
    public Town(String townName) {
        this.faction = null;
        this.townName = townName;
        this.tax = new Tax(); // set tax rate to low
        this.wealth = 10; // set initial province wealth to 10
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
    public int getTaxOwed(){
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
    public void wealthAfterTax(){
        growWealth();
        int currWealth = getWealth();
        if( currWealth <= 0 ){
            return;
        }else{ 
            int total = currWealth - getTaxOwed();
            if( total <= 0 ){ 
                this.wealth = 0;
            }else{
                this.wealth = total;
            }
        }
    }
    public String getTaxStatus(){
        return tax.getTaxType();
    }
    public void updateTaxStatus(String taxType){
        tax.updateTax(taxType);
    }
    
}
