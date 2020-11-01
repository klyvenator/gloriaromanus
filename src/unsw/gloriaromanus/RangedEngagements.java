package unsw.gloriaromanus;

import java.util.Random;

import unsw.gloriaromanus.Enums.Range;

public class RangedEngagements implements Engagements {

    private static final int MIN_DEFENSE = 10;

    @Override
    public void attack(Unit attacker, Unit defender) {
        if (attacker.getType() == Range.MELEE) {
            return; // melee does not inflict damage in ranges engagements
        }

        // original number of troops of defender
        int originalSize = defender.getNumTroops();
        double part1 = 0.1 * originalSize;

        // TODO Apply modifiers?

        double part2;
        try { 
            part2 = attacker.getAttack() / defender.getDefenseRanged();
        } catch (ArithmeticException e) {
            part2 = attacker.getAttack() / MIN_DEFENSE;
        }

        Random r = new Random();
        double part3 = r.nextGaussian() + 1;

        // Apply max = whole army, min = none limits
        int damage = (int) (part1 * part2 * part3);
        if (damage < 0) {
            damage = 0;
        } else if (damage > originalSize) {
            damage = originalSize;
        }

        // Attack
        defender.setNumTroops(originalSize - damage);

    }

    @Override
    public Unit engage(Unit a, Unit b) {
        // No special order
        attack(a, b);
        attack(b, a);

        if ((a.getNumTroops() <= 0) && (b.getNumTroops() <= 0)) {
            // Both defeated, no winner
            return null;
        } else if (a.getNumTroops() > 0) {
            return a;
        } else if (b.getNumTroops() > 0) {
            return b;
        } else {
            // Both alive, no winner
            return null;
        }

    }

    @Override
    public Unit routeEngage(Unit routing, Unit pursuing) {
        attack(pursuing, routing);

        if (routing.getNumTroops() <= 0) {
            return pursuing;
        } else {
            return null;
        }
    }

    
    
}