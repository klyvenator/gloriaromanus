package unsw.gloriaromanus.Model;


public class Cavalry extends Unit {
    private int charge = 0;

    public Cavalry(String name) {
        super(name);
        this.setMovementPoints(15);
    }

    @Override
    public int getAttack() {
        return super.getAttack() + charge;
    }

    public int getCharge() {
        return charge;
    }
    
    public void setCharge(int charge) {
        this.charge = charge;
    }
}
