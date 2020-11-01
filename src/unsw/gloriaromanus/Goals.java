package unsw.gloriaromanus;

import java.util.Random;

import org.graalvm.compiler.lir.CompositeValue.Component;

import unsw.gloriaromanus.Enums.Condition;
import unsw.gloriaromanus.Enums.Operation;

public class Goals {
    private int wealth = 400000;
    private int gold = 100000;
    private ComponentEvaluator winConditions;

    // updates the wealth/gold amount from json

    public void initialise() {

    }

    // randomly generates winning condition.
    public void generateConditions() {
        Random random = new Random();
        int numConditions = random.nextInt(2) + 1;
        switch(numConditions) {
            case 1: winConditions = new LeafEvaluator();
                break;
            case 2: condition = new TwoConditions(wealth, gold);
                    break;
            case 3: condition = new ThreeConditions(wealth, gold);
                    break;            
        }
    }





}