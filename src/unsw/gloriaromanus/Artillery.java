package unsw.gloriaromanus;

import java.util.List;
import unsw.gloriaromanus.Enums.Range;

public class Artillery extends Unit {

    public Artillery(String name, Range range, int number, int attack, int defense, int speed, int morale, int turns, int cost, List<Ability> abilities) {
        super(name, range, number, attack, defense, speed, morale, turns, cost, abilities);
        this.setMovementPoints(4);
    }
}