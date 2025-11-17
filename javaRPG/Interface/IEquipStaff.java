package javaRPG.Interface;

// 必要なクラスをインポート
import javaRPG.BattleUnit.BattleUnit;  // 戦闘ユニットの基底クラス
import javaRPG.item.Staff;             // 杖アイテムクラス

/**
 * IEquipStaff（杖装備）インターフェース
 *
 * このインターフェースは、杖を装備して魔法攻撃を行うことができる
 * キャラクターが実装すべき機能を定義します。
 *
 * 実装クラス：
 * - Mage（魔法使い）: 杖と魔法を扱う魔法特化型
 * - MagicSwordsman（魔法剣士）: 剣と杖の両方を扱える万能型
 *
 * 定義する機能：
 * 1. equipStaff - 杖を装備する
 * 2. staffAttack - 杖による攻撃を行う
 *
 * 杖攻撃の特徴：
 * - 基本攻撃力に杖の魔法力ボーナスが加算される
 * - 魔法使用時も杖の魔法力ボーナスが適用される
 * - 物理攻撃よりも魔法攻撃に適している
 *
 * @author RPG開発チーム
 * @version 1.0
 */
public interface IEquipStaff {

    /**
     * 杖を装備するメソッド
     *
     * ショップで購入した杖を装備します。
     * 装備すると、杖攻撃や魔法の威力が上昇します。
     *
     * 処理内容：
     * 1. 杖オブジェクトを保持
     * 2. 装備メッセージを表示
     * 3. 魔法力ボーナスを表示
     *
     * @param staff 装備する杖（Staffオブジェクト）
     */
    void equipStaff(Staff staff);

    /**
     * 杖による攻撃メソッド
     *
     * 装備している杖を使って対象を攻撃します。
     * 魔法力ボーナスが攻撃力に加算されます。
     *
     * ダメージ計算：
     * - ダメージ = 基本攻撃力 + 杖の魔法力ボーナス
     *
     * 使用例：
     * - MPを消費せずに攻撃したい時
     * - 魔法が使えない状況での攻撃手段
     *
     * 備考：
     * - 杖攻撃は物理攻撃扱いですが、魔法力ボーナスが適用されます
     * - 魔法使用時も杖の魔法力ボーナスが魔法の威力に加算されます
     *
     * @param target 攻撃対象のBattleUnit
     */
    void staffAttack(BattleUnit target);

} // IEquipStaff インターフェース終了
