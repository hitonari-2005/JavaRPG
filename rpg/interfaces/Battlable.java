package rpg.interfaces;

import rpg.base.BattleUnit;

public interface Battlable {
    void attack(BattleUnit target);
    void takeDamage(int damage);
    boolean isAlive();
}
