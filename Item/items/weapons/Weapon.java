package Item.items.weapons;

import Item.items.Item;

public class Weapon extends Item {
    protected int attackPower;

    public Weapon(String name, String description, int attackPower) {
        super(name, description);
        this.attackPower = attackPower;
    }

    public int getAttackPower() {
        return attackPower;
    }
}
