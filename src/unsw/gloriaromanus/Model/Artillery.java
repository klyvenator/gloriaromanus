package unsw.gloriaromanus.Model;


public class Artillery extends Unit {

    public Artillery(String name) {
        super(name);
        this.setMovementPoints(1);
        this.setMovesLeft(1);
    }
}