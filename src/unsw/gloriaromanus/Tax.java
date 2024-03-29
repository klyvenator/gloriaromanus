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

    private void changeTax(String taxName){
        if( taxName.equals("Low") ){
            this.taxRate = 0.10;
            this.growth = 10;
        }
        else if ( taxName.equals("Normal") ){
            this.taxRate = 0.15;
            this.growth = 0;
        }
        else if ( taxName.equals("High") ){
            this.taxRate = 0.20;
            this.growth = -10;
        }
        else if ( taxName.equals("Very high") ){
            this.taxRate = 0.25;
            this.growth = -30;
        } else {
            // No change
            taxName = this.taxType;
        }

        this.taxType = taxName;
    }
    public void updateTax(String taxType){
        changeTax(taxType);
    }
    public double getTaxRate(){
        return this.taxRate;
    }
    public int getTaxGrowth(){
        return this.growth;
    }
    public String getTaxType(){
        return this.taxType;
    }

}
