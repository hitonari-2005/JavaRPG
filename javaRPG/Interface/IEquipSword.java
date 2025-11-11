package javaRPG.Interface;

import javaRPG.BattleUnit.BattleUnit;
import javaRPG.item.Sword;

/**
 * IEquipSword interface
 * 剣を装備できるキャラクターが実装するインターフェース
 * Hero や MagicSwordsman が実装する
 */
public interface IEquipSword {

    /**
     * 剣を装備するメソッド
     * @param sword 装備する剣
     */
    void equipSword(Sword sword);

    /**
     * 剣による攻撃メソッド
     * @param target 攻撃対象
     */
    void swordAttack(BattleUnit target);

}
