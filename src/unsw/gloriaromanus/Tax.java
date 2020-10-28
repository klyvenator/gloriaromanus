package unsw.gloriaromanus;

public class Tax {
    
    private String taxType;
    private double taxRate;
    private int growth;

    public Tax(){
        // set a new tax type to low with 10% taxrate and +10 gold growth
        this.taxType = "Low";
        this.taxRate = 0.10;
        this.growth = 10;
    }
}
