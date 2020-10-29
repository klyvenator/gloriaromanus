package unsw.gloriaromanus;


public class Town {
    
    // TO DO uncomment when faction has been created 
    //private Faction faction; 
    private String faction;
    private int wealth;
    private Tax tax;

    // TO DO uncomment when faction has been created 
    // public Town(Faction faction){
    public Town(){
        this.faction = "Temp Faction";
        this.tax = new Tax(); // set tax rate to low
        this.wealth = 10; // set initial province wealth to 10
    }
    public int getWealth(){
        return this.wealth;
    }
    public String getFaction(){
        return this.faction;
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
        tax.changeTax(taxType);
    }
    
}
