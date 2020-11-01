package unsw.gloriaromanus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import unsw.gloriaromanus.Enums.Operation;

public class CompositeEvaluator implements ComponentEvaluator{
    private Operation op;
    private List<ComponentEvaluator> compList;

    public CompositeEvaluator() {
        compList = new ArrayList<ComponentEvaluator>();
        op = randOperation(); 
    }

    public void addChild(ComponentEvaluator c) {
        compList.add(c);
    }

    public void removeChild(ComponentEvaluator c) {
        compList.remove(c);
    }

    public void replaceChild(int position) {
        CompositeEvaluator newComp = new CompositeEvaluator();
        newComp.addChild(compList.get(position));
        compList.add(position, newComp);
        this.removeChild(compList.get(position + 1));
    }

    public boolean conditionFulfilled(Faction faction) {
        if (compList.size() == 0);

        if (compList.size() == 1) {
            return compList.get(0).conditionFulfilled(faction);
        } else {
            if (op == Operation.AND) {
                if (compList.get(0).conditionFulfilled(faction) &&
                    compList.get(1).conditionFulfilled(faction)) {
                    return true;
                }
            } else {
                if (op == Operation.OR) {
                    if (compList.get(0).conditionFulfilled(faction) ||
                        compList.get(1).conditionFulfilled(faction)) {
                            return true;
                        }
                }
            }
            return false;
        }
    }

    public Operation randOperation() {
        Random random = new Random();
        switch (random.nextInt(2)) {
            case 0: return Operation.AND;
            default: return Operation.OR;
        }
    }
    
    public List<ComponentEvaluator> getCompList() {
        return compList;
    }

    public Operation getOp() {
        return op;
    }

}