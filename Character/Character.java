//character　の基底クラス

public class Character {
    protected String name;
    protected int hp;
    protected int maxHp;
    protected int attackPower;
    protected int defense;

    public Character(String name, int maxHp, int attackPower, int defense) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attackPower = attackPower;
        this.defense = defense;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getDefense() {
        return defense;
    }

    public void setHp(int hp) {
        this.hp = Math.max(0, Math.min(hp, maxHp));
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void takeDamage(int damage) {
        int actualDamage = Math.max(0, damage - defense);
        setHp(hp - actualDamage);
    }

    public void heal(int amount) {
        setHp(hp + amount);
    }
}