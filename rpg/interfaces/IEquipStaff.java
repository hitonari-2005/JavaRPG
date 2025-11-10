package rpg.interfaces;

import rpg.base.BattleUnit;
import rpg.item.Staff;

public interface IEquipStaff {
    void equipStaff(Staff staff);
    void staffAttack(BattleUnit target);
}
