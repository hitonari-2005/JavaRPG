package javaRPG.EnemyUnit;

import javaRPG.BattleUnit.BattleUnit;

public class EnemyUnit extends BattleUnit {

    private int level;
    private int baseExp;
    private int baseMoney;

    public EnemyUnit(String name, int id, String type, int level) {
        super(name, id, type,
              calculatePower(level),
              calculateHp(level),
              calculateSpeed(level));

        this.level = level;
        this.baseExp = 10;
        this.baseMoney = 15;
    }

    public EnemyUnit() {
        super();
        this.level = 1;
        this.baseExp = 10;
        this.baseMoney = 15;
    }

    private static int calculatePower(int level) {
        return 8 + (level * 2);
    }

    private static int calculateHp(int level) {
        return 30 + (level * 10);
    }

    private static int calculateSpeed(int level) {
        return 5 + level;
    }

    public int getExp() {
        return baseExp * level;
    }

    public int getMoney() {
        return baseMoney * level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        setPower(calculatePower(level));
        setMaxHp(calculateHp(level));
        setHp(calculateHp(level));
        setSpeed(calculateSpeed(level));
    }

    @Override
    public void displayStatus() {
        System.out.println("========== ENEMY ==========");
        System.out.println("Name: " + getName());
        System.out.println("Type: " + getType());
        System.out.println("Level: " + level);
        System.out.println("HP: " + getHp() + "/" + getMaxHp());
        System.out.println("Power: " + getPower());
        System.out.println("Speed: " + getSpeed());
        System.out.println("Exp: " + getExp());
        System.out.println("Money: " + getMoney());
        System.out.println("====================");
    }

    public void defeated() {
        System.out.println(getName() + " defeated!");
        System.out.println("Get Exp: " + getExp());
        System.out.println("Get Money: " + getMoney() + " gold");
    }

}
