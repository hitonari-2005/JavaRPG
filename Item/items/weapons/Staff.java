package Item.items.weapons;

public class Staff extends Weapon {
    protected int magicPower;

    public Staff(String name, String description, int attackPower, int magicPower) {
        super(name, description, attackPower);
        this.magicPower = magicPower;
    }

    public int getMagicPower() {
        return magicPower;
    }
}
