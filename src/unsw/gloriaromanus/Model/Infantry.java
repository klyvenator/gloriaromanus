package unsw.gloriaromanus.Model;


public class Infantry extends Unit {

    public Infantry(String name) {
        super(name);
        this.setMovementPoints(2);
        this.setMovesLeft(2);
    }
}