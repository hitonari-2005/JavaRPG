package rpg.interfaces;

import rpg.base.BattleUnit;
import rpg.item.Sword;

public interface IEquipSword {
    void equipSword(Sword sword);
    void swordAttack(BattleUnit target);
}
