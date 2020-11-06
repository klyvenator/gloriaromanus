package unsw.gloriaromanus.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import unsw.gloriaromanus.Model.Enums.Condition;



public class Goals {
    private int wealth = 400000;
    private int gold = 100000;
    private int totalProvinces;
    private CompositeEvaluator winConditions;
    private List<Condition> conditionList;

    public Goals(int numProvinces) {
        conditionList = new ArrayList<Condition>();
        winConditions = new CompositeEvaluator();
        totalProvinces = numProvinces;
        fillShuffle();
        generateConditions();
    }

    // Deserialises json config for win conditions
    public void deserialise() {
        // to do
    }

    // randomly generates winning condition.
    public void generateConditions() {
        Random random = new Random();
        int numConditions = random.nextInt(3) + 1;

        for (int i = 0; i < numConditions; i++) {
            insert(winConditions, conditionList.get(i));
        }

    }

    public void fillShuffle() {
        conditionList.add(Condition.CONQUER);
        conditionList.add(Condition.TREASURY);
        conditionList.add(Condition.WEALTH);
        Collections.shuffle(conditionList);
    }

    public boolean checkWin(Faction f) {
        return winConditions.conditionFulfilled(f);
    }

    public void printWinConditions(ComponentEvaluator c) {
        if (c instanceof CompositeEvaluator) {
            CompositeEvaluator composite = (CompositeEvaluator)c;
            if(composite.getCompList().size() == 1) {
                printWinConditions(composite.getCompList().get(0));
            } else if (composite.getCompList().size() == 2) {
                System.out.print("(");
                printWinConditions(composite.getCompList().get(0));
                System.out.print(composite.getOp());
                printWinConditions(composite.getCompList().get(1));
                System.out.print(")");
            }
        } else {
            LeafEvaluator leaf = (LeafEvaluator)c;
            System.out.print(leaf.getCondition());
        }
    }

    public void insert(ComponentEvaluator c, Condition condition) {
        if (c == null) {
            c = new CompositeEvaluator();
        }

        Queue<ComponentEvaluator> q = new LinkedList<ComponentEvaluator>();
            q.add(c);
            while(!q.isEmpty()) {
                c = q.peek();
                q.remove();
                if (c instanceof CompositeEvaluator) {
                    CompositeEvaluator newComp = (CompositeEvaluator)c;
                    if (newComp.getCompList().size() < 2) {
                        newComp.addChild(new LeafEvaluator(gold, wealth, condition, totalProvinces));
                        return;
                    } else {
                        if (newComp.getCompList().get(0) instanceof LeafEvaluator) {
                            newComp.replaceChild(0);
                            q.add(newComp.getCompList().get(0));
                            q.add(newComp);
                        } else if (newComp.getCompList().get(1) instanceof LeafEvaluator){
                            newComp.replaceChild(1);
                            q.add(newComp.getCompList().get(1));
                        } else {
                            q.add(newComp.getCompList().get(0));
                            q.add(newComp.getCompList().get(1));
                        }
                    }
                } 
            }
    }

    public CompositeEvaluator getWinConditions() {
        return winConditions;
    }

    public static void main(String[] args) {
        Faction f = new Faction("Kly Faction");
        Goals goal = new Goals(0);
        goal.printWinConditions((ComponentEvaluator)goal.winConditions);
        System.out.println(goal.checkWin(f));
    }


}