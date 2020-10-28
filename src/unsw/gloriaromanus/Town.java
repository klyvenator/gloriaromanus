package unsw.gloriaromanus;

public class Town {
    
    private Faction faction;
    private int wealth;
    private double taxRate; 

    public Town(Faction faction, int wealth, double taxRate){
        
        this.faction = faction;
        this.wealth = 0; //set initial town wealth to zero
        this.taxRate = 0.10; //set initial tax rate to low, 0.10

    }

    public int getTax(){
        if(this.wealth <= 0 ){
            return 0;
        }
        int tax = (int)Math.round(this.taxRate * this.wealth);
        return tax;
    }

    public changeTaxRate(String rate){
        //TO DO 
        if( rate.equals("Low") ){
            this.taxRate = 0.10;
        }
        else if( rate.equals("Normal") ){
            this.taxRate = 0.10;
        }
    }
}
