package javaRPG.Interface;

import javaRPG.BattleUnit.BattleUnit;
import java.util.List;

/**
 * IMagicUser interface
 * 魔法を使用できるキャラクターが実装するインターフェース
 * Mage や MagicSwordsman が実装する
 */
public interface IMagicUser {

    /**
     * 魔法を使用するメソッド
     * @param magicName 魔法の名前
     * @param targets 対象のリスト（単体または複数対象）
     */
    void useMagic(String magicName, List<BattleUnit> targets);

}
