package javaRPG.EnemyUnit;

// 必要なクラスをインポート
import javaRPG.BattleUnit.BattleUnit;  // 戦闘ユニットの基底クラス

/**
 * スライムクラス
 *
 * このクラスはEnemyUnitを継承した敵キャラクターの一つで、
 * RPGの序盤によく登場する基本的な敵モンスターです。
 *
 * 特殊能力：
 * - HPが最大HPの半分以下になると、1度だけ自動的にHPを回復する
 * - 回復量は最大HPの1/3
 * - この能力は1回の戦闘で1度しか発動しない
 *
 * 攻撃パターン：
 * - 体当たり攻撃を行う
 * - ダメージは攻撃力と同じ
 *
 * 継承関係：
 * BattleUnit → EnemyUnit → Slime（このクラス）
 *
 * @author RPG開発チーム
 * @version 1.0
 */
public class Slime extends EnemyUnit {

    // ========================================
    // フィールド（属性）- Slime固有
    // ========================================

    /**
     * 回復済みフラグ
     * trueの場合、既に自動回復を使用済み
     * falseの場合、まだ自動回復を使用していない
     */
    private boolean hasRecovered;

    // ========================================
    // コンストラクタ
    // ========================================

    /**
     * Slimeクラスのコンストラクタ（レベル指定あり）
     *
     * 指定されたレベルでスライムを作成します。
     * レベルが高いほど、HP、攻撃力、素早さが増加します。
     *
     * @param level スライムのレベル（1以上の整数）
     */
    public Slime(int level) {
        // 親クラス（EnemyUnit）のコンストラクタを呼び出す
        // ID: 100 + level（レベル1なら101、レベル2なら102...）
        super("スライム", 100 + level, "Slime", level);

        // 回復フラグを初期化（まだ回復していない状態）
        this.hasRecovered = false;
    }

    /**
     * Slimeクラスのデフォルトコンストラクタ
     *
     * パラメータなしで呼び出された場合、レベル1のスライムを作成します。
     */
    public Slime() {
        this(1);  // レベル1でコンストラクタを呼び出す
    }

    // ========================================
    // 特殊能力メソッド
    // ========================================

    /**
     * ダメージを受けた後の特殊処理（オーバーライド）
     *
     * 親クラスのダメージ処理に加えて、スライムの特殊能力を発動させます。
     * HPが最大HPの半分以下になった場合、まだ回復していなければ自動回復します。
     *
     * 発動条件：
     * 1. まだ回復していない（hasRecovered == false）
     * 2. 生存している（isAlive() == true）
     * 3. HPが最大HPの半分以下
     *
     * @param damage 受けるダメージ量
     */
    @Override
    public void takeDamage(int damage) {
        // 親クラスのダメージ処理を実行
        super.takeDamage(damage);

        // HPが最大HPの半分以下になった場合、まだ回復していなければ回復
        if (!hasRecovered && isAlive() && getHp() <= getMaxHp() / 2) {
            hpHalfRecover();  // 自動回復を発動
        }
    }

    /**
     * HP半分以下で発動する自動回復
     *
     * スライムの特殊能力です。HPが最大HPの半分以下になると、
     * 最大HPの1/3を自動的に回復します。この能力は1回の戦闘で1度だけ使用できます。
     *
     * 回復量の計算：
     * 回復量 = 最大HP ÷ 3
     * 例：最大HP60の場合、20回復
     */
    private void hpHalfRecover() {
        // 回復済みフラグを立てる（2回目以降は発動しない）
        hasRecovered = true;

        // 回復量を計算（最大HPの1/3）
        int recoverAmount = getMaxHp() / 3;

        // 回復メッセージを表示
        System.out.println("★ " + getName() + "は身体を再生した！ ★");

        // HPを回復
        heal(recoverAmount);
    }

    // ========================================
    // 攻撃メソッド
    // ========================================

    /**
     * スライム専用の攻撃（オーバーライド）
     *
     * 体当たり攻撃を行います。ダメージは攻撃力と同じです。
     *
     * @param target 攻撃対象（BattleUnitオブジェクト）
     */
    @Override
    public void attack(Object target) {
        // 攻撃対象がBattleUnitのインスタンスかチェック
        if (!(target instanceof BattleUnit)) {
            System.out.println("攻撃対象が無効です！");
            return;
        }

        // Object型からBattleUnit型にキャスト
        BattleUnit battleTarget = (BattleUnit) target;

        // 攻撃メッセージを表示
        System.out.println(getName() + "の体当たり！");

        // ダメージは攻撃力と同じ
        int damage = getPower();

        // 対象にダメージを与える
        battleTarget.takeDamage(damage);
    }

} // Slime クラス終了
