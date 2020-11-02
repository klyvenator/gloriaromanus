package unsw.gloriaromanus;

public class Enums {
    public enum Range {
        MELEE,
        RANGED;
    }

    public enum BattleStatus {
        FIGHTING,
        WIN_A,
        WIN_B,
        DRAW;
    }

    public enum FightStatus {
        FIGHTING,
        WIN_A,
        WIN_B,
        FLEE_A,
        FLEE_B,
        FLEE_ALL,
        DRAW;
    }

    public enum Condition {
        TREASURY,
        WEALTH,
        CONQUER
    }

    public enum Operation {
        AND,
        OR
    }
}

