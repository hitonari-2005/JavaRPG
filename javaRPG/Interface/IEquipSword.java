package javaRPG.Interface;

// 必要なクラスをインポート
import javaRPG.BattleUnit.BattleUnit;  // 戦闘ユニットの基底クラス
import javaRPG.item.Sword;             // 剣アイテムクラス

/**
 * IEquipSword（剣装備）インターフェース
 *
 * このインターフェースは、剣を装備して戦うことができるキャラクターが
 * 実装すべき機能を定義します。
 *
 * 実装クラス：
 * - Hero（勇者）: 剣を扱う物理攻撃特化型
 * - MagicSwordsman（魔法剣士）: 剣と杖の両方を扱える万能型
 *
 * 定義する機能：
 * 1. equipSword - 剣を装備する
 * 2. swordAttack - 剣による攻撃を行う
 *
 * 剣攻撃の特徴：
 * - 基本攻撃力に剣の攻撃力ボーナスが加算される
 * - 通常攻撃よりも高いダメージを与えられる
 * - Heroは1.5倍、MagicSwordsmanは1.2倍のダメージ補正
 *
 * @author RPG開発チーム
 * @version 1.0
 */
public interface IEquipSword {

    /**
     * 剣を装備するメソッド
     *
     * ショップで購入した剣を装備します。
     * 装備すると、剣攻撃や必殺技の威力が上昇します。
     *
     * 処理内容：
     * 1. 剣オブジェクトを保持
     * 2. 装備メッセージを表示
     * 3. 攻撃力ボーナスを表示
     *
     * @param sword 装備する剣（Swordオブジェクト）
     */
    void equipSword(Sword sword);

    /**
     * 剣による攻撃メソッド
     *
     * 装備している剣を使って対象を攻撃します。
     * 基本攻撃よりも強力なダメージを与えられます。
     *
     * ダメージ計算：
     * - Hero: （基本攻撃力 + 剣の攻撃力ボーナス）× 1.5
     * - MagicSwordsman: （基本攻撃力 + 剣の攻撃力ボーナス）× 1.2
     *
     * 使用例：
     * - 強敵との戦闘で高いダメージを与えたい時
     * - 通常攻撃より効率的に敵を倒したい時
     *
     * @param target 攻撃対象のBattleUnit
     */
    void swordAttack(BattleUnit target);

} // IEquipSword インターフェース終了
