package unsw.gloriaromanus;

public class DefenseStat {
    private int defenseSkill = 5;
    private int armour = 5;
    private int shield = 0;

    public int getArmour() {
        return armour;
    }
    public void setArmour(int armour) {
        this.armour = armour;
    }

    public int getDefenseSkill() {
        return defenseSkill;
    }
    public void setDefenseSkill(int defenseSkill) {
        this.defenseSkill = defenseSkill;
    }

    public int getShield() {
        return shield;
    }
    public void setShield(int shield) {
        this.shield = shield;
    }

    public int getRangedDef() {
        return armour + shield;
    }

    public int getMeleeDef() {
        return armour + shield + defenseSkill;
    }

}
