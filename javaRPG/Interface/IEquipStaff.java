package javaRPG.Interface;

import javaRPG.BattleUnit.BattleUnit;
import javaRPG.item.Staff;

/**
 * IEquipStaff interface
 * 杖を装備できるキャラクターが実装するインターフェース
 * Mage や MagicSwordsman が実装する
 */
public interface IEquipStaff {

    /**
     * 杖を装備するメソッド
     * @param staff 装備する杖
     */
    void equipStaff(Staff staff);

    /**
     * 杖による攻撃メソッド
     * @param target 攻撃対象
     */
    void staffAttack(BattleUnit target);

}
