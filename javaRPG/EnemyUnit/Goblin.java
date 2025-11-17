package javaRPG.EnemyUnit;

// 必要なクラスをインポート
import javaRPG.BattleUnit.BattleUnit;  // 戦闘ユニットの基底クラス

/**
 * ゴブリンクラス
 *
 * このクラスはEnemyUnitを継承した敵キャラクターの一つで、
 * RPGで中級の敵として登場する凶暴なモンスターです。
 *
 * 特殊能力：
 * - HPが最大HPの半分以下になると、1度だけ攻撃力が大幅にアップする
 * - 攻撃力上昇量はレベルに応じて増加（5 + レベル×2）
 * - この能力は1回の戦闘で1度しか発動しない
 * - 瀕死状態になると怒り狂い、強力な攻撃を仕掛けてくる
 *
 * 攻撃パターン：
 * - 棍棒攻撃を行う
 * - ダメージは攻撃力と同じ（パワーアップ後はより強力）
 *
 * 継承関係：
 * BattleUnit → EnemyUnit → Goblin（このクラス）
 *
 * @author RPG開発チーム
 * @version 1.0
 */
public class Goblin extends EnemyUnit {

    // ========================================
    // フィールド（属性）- Goblin固有
    // ========================================

    /**
     * パワーアップ済みフラグ
     * trueの場合、既に攻撃力アップを使用済み
     * falseの場合、まだ攻撃力アップを使用していない
     */
    private boolean hasPoweredUp;

    /**
     * 攻撃力アップ量
     * レベルに応じて計算される（5 + レベル×2）
     * 例：レベル1 = 7、レベル5 = 15、レベル10 = 25
     */
    private int powerUpAmount;

    // ========================================
    // コンストラクタ
    // ========================================

    /**
     * Goblinクラスのコンストラクタ（レベル指定あり）
     *
     * 指定されたレベルでゴブリンを作成します。
     * レベルが高いほど、HP、攻撃力、素早さ、そしてパワーアップ量も増加します。
     *
     * @param level ゴブリンのレベル（1以上の整数）
     */
    public Goblin(int level) {
        // 親クラス（EnemyUnit）のコンストラクタを呼び出す
        // ID: 200 + level（レベル1なら201、レベル2なら202...）
        super("ゴブリン", 200 + level, "Goblin", level);

        // パワーアップフラグを初期化（まだパワーアップしていない状態）
        this.hasPoweredUp = false;

        // レベルに応じてパワーアップ量を計算
        this.powerUpAmount = 5 + (level * 2);
    }

    /**
     * Goblinクラスのデフォルトコンストラクタ
     *
     * パラメータなしで呼び出された場合、レベル1のゴブリンを作成します。
     */
    public Goblin() {
        this(1);  // レベル1でコンストラクタを呼び出す
    }

    // ========================================
    // 特殊能力メソッド
    // ========================================

    /**
     * ダメージを受けた後の特殊処理（オーバーライド）
     *
     * 親クラスのダメージ処理に加えて、ゴブリンの特殊能力を発動させます。
     * HPが最大HPの半分以下になった場合、まだパワーアップしていなければ
     * 攻撃力がアップします。
     *
     * 発動条件：
     * 1. まだパワーアップしていない（hasPoweredUp == false）
     * 2. 生存している（isAlive() == true）
     * 3. HPが最大HPの半分以下
     *
     * @param damage 受けるダメージ量
     */
    @Override
    public void takeDamage(int damage) {
        // 親クラスのダメージ処理を実行
        super.takeDamage(damage);

        // HPが最大HPの半分以下になった場合、まだパワーアップしていなければパワーアップ
        if (!hasPoweredUp && isAlive() && getHp() <= getMaxHp() / 2) {
            hpHalfPowerUp();  // 攻撃力アップを発動
        }
    }

    /**
     * HP半分以下で発動する攻撃力アップ
     *
     * ゴブリンの特殊能力です。HPが最大HPの半分以下になると、
     * 怒り狂って攻撃力が大幅にアップします。
     * この能力は1回の戦闘で1度だけ使用できます。
     *
     * 攻撃力上昇量の計算：
     * 上昇量 = 5 + (レベル × 2)
     * 例：レベル1 = +7、レベル5 = +15、レベル10 = +25
     */
    private void hpHalfPowerUp() {
        // パワーアップ済みフラグを立てる（2回目以降は発動しない）
        hasPoweredUp = true;

        // 攻撃力を上昇させる
        setPower(getPower() + powerUpAmount);

        // パワーアップメッセージを表示
        System.out.println("★ " + getName() + "は怒り狂った！ ★");
        System.out.println("攻撃力が" + powerUpAmount + "アップした！");
    }

    // ========================================
    // 攻撃メソッド
    // ========================================

    /**
     * ゴブリン専用の攻撃（オーバーライド）
     *
     * 棍棒攻撃を行います。ダメージは攻撃力と同じです。
     * パワーアップ後は、上昇した攻撃力でより強力な攻撃を行います。
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
        System.out.println(getName() + "の棍棒攻撃！");

        // ダメージは現在の攻撃力と同じ（パワーアップ後は強化されている）
        int damage = getPower();

        // 対象にダメージを与える
        battleTarget.takeDamage(damage);
    }

} // Goblin クラス終了
