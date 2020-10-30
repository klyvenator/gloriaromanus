package unsw.gloriaromanus;

import unsw.gloriaromanus.Enums.Range;
import java.util.List;

public class Cavalry extends Unit {

    public Cavalry(String name, Range range, int number, int attack, int defense, int speed, int morale, int turns, int cost, List<Ability> abilities) {
        super(name, range, number, attack, defense, speed, morale, turns, cost, abilities);
        this.setMovementPoints(15);
    }
}