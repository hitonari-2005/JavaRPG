package javaRPG.EnemyUnit;

// 必要なクラスをインポート
import javaRPG.BattleUnit.BattleUnit;  // 戦闘ユニットの基底クラス
import java.util.List;                 // リストコレクション

/**
 * ドラゴンクラス（ボス敵）
 *
 * このクラスはEnemyUnitを継承した最強クラスのボス敵です。
 * 約4回の戦闘ごとに登場し、プレイヤーに強大な脅威を与えます。
 *
 * 特殊能力：
 * 1. HPが最大HPの1/3以下になると攻撃力が大幅にアップ
 *    - 上昇量はレベルに応じて増加（10 + レベル×3）
 *    - この能力は1回の戦闘で1度しか発動しない
 *
 * 2. 全体攻撃「ファイアーブレス」を使用できる
 *    - すべての味方に同時にダメージを与える
 *    - ダメージは攻撃力の0.8倍
 *    - 戦闘中30%の確率で使用
 *
 * 3. 通常攻撃「ひっかく」
 *    - 鋭い爪で単体を攻撃
 *    - ダメージは攻撃力と同じ
 *
 * ボス特性：
 * - 基本ステータスが通常の敵より高い
 * - 最大HPに+50ボーナス
 * - 攻撃力に+10ボーナス
 * - 倒すと経験値1.5倍、お金2倍のボーナス
 *
 * 継承関係：
 * BattleUnit → EnemyUnit → Dragon（このクラス）
 *
 * @author RPG開発チーム
 * @version 1.0
 */
public class Dragon extends EnemyUnit {

    // ========================================
    // フィールド（属性）- Dragon固有
    // ========================================

    /**
     * パワーアップ済みフラグ
     * trueの場合、既に攻撃力アップを使用済み
     * falseの場合、まだ攻撃力アップを使用していない
     */
    private boolean hasPoweredUp;

    /**
     * 攻撃力アップ量
     * レベルに応じて計算される（10 + レベル×3）
     * 例：レベル3 = 19、レベル5 = 25、レベル10 = 40
     */
    private int powerUpAmount;

    // ========================================
    // コンストラクタ
    // ========================================

    /**
     * Dragonクラスのコンストラクタ（レベル指定あり）
     *
     * 指定されたレベルでドラゴンを作成します。
     * ボス敵なので、通常の敵より高いステータスで初期化されます。
     *
     * ボーナスステータス：
     * - 最大HPに+50
     * - 攻撃力に+10
     *
     * @param level ドラゴンのレベル（通常3以上）
     */
    public Dragon(int level) {
        // 親クラス（EnemyUnit）のコンストラクタを呼び出す
        // ID: 999（ボス専用の特別なID）
        super("ドラゴン", 999, "Dragon", level);

        // パワーアップフラグを初期化（まだパワーアップしていない状態）
        this.hasPoweredUp = false;

        // レベルに応じてパワーアップ量を計算
        // ボスなので通常の敵より強力（10 + レベル×3）
        this.powerUpAmount = 10 + (level * 3);

        // ドラゴンは強力なボスなので基本ステータスにボーナスを加算
        setMaxHp(getMaxHp() + 50);  // 最大HPに+50
        setHp(getMaxHp());          // HPを最大値に設定
        setPower(getPower() + 10);  // 攻撃力に+10
    }

    /**
     * Dragonクラスのデフォルトコンストラクタ
     *
     * パラメータなしで呼び出された場合、レベル5のドラゴンを作成します。
     * レベル5はボスとして適切な強さです。
     */
    public Dragon() {
        this(5);  // レベル5でコンストラクタを呼び出す
    }

    // ========================================
    // 特殊能力メソッド
    // ========================================

    /**
     * ダメージを受けた後の特殊処理（オーバーライド）
     *
     * 親クラスのダメージ処理に加えて、ドラゴンの特殊能力を発動させます。
     * HPが最大HPの1/3以下になった場合、まだパワーアップしていなければ
     * 攻撃力が大幅にアップします。
     *
     * 発動条件：
     * 1. まだパワーアップしていない（hasPoweredUp == false）
     * 2. 生存している（isAlive() == true）
     * 3. HPが最大HPの1/3以下
     *
     * @param damage 受けるダメージ量
     */
    @Override
    public void takeDamage(int damage) {
        // 親クラスのダメージ処理を実行
        super.takeDamage(damage);

        // HPが最大HPの1/3以下になった場合、まだパワーアップしていなければパワーアップ
        if (!hasPoweredUp && isAlive() && getHp() <= getMaxHp() / 3) {
            powerUpOnThirdHp();  // 攻撃力アップを発動
        }
    }

    /**
     * HP1/3以下で発動する攻撃力アップ
     *
     * ドラゴンの特殊能力です。HPが最大HPの1/3以下になると、
     * 激怒して攻撃力が大幅にアップします。
     * この能力は1回の戦闘で1度だけ使用できます。
     *
     * 攻撃力上昇量の計算：
     * 上昇量 = 10 + (レベル × 3)
     * 例：レベル3 = +19、レベル5 = +25、レベル10 = +40
     */
    private void powerUpOnThirdHp() {
        // パワーアップ済みフラグを立てる（2回目以降は発動しない）
        hasPoweredUp = true;

        // 攻撃力を上昇させる
        setPower(getPower() + powerUpAmount);

        // 激怒メッセージを表示（ボス専用の派手な演出）
        System.out.println("★★★ " + getName() + "は激怒した！ ★★★");
        System.out.println("攻撃力が" + powerUpAmount + "アップした！");
    }

    // ========================================
    // 攻撃メソッド
    // ========================================

    /**
     * ドラゴンの通常攻撃「ひっかく」
     *
     * 鋭い爪で単体を攻撃します。ダメージは現在の攻撃力と同じです。
     * パワーアップ後は、上昇した攻撃力でより強力な攻撃を行います。
     *
     * @param target 攻撃対象のBattleUnit
     */
    public void scratch(BattleUnit target) {
        // 攻撃対象が有効かチェック
        if (target == null || !target.isAlive()) {
            System.out.println("攻撃対象が無効です！");
            return;
        }

        // 攻撃メッセージを表示
        System.out.println(getName() + "の鋭い爪が襲いかかる！");

        // ダメージは現在の攻撃力と同じ
        int damage = getPower();

        // 対象にダメージを与える
        target.takeDamage(damage);
    }

    /**
     * 全体攻撃「ファイアーブレス」
     *
     * ドラゴンの必殺技です。灼熱の炎を吐き、すべての味方に同時にダメージを与えます。
     * BattleSystemから30%の確率で使用されます。
     *
     * ダメージ計算：
     * - 各対象に攻撃力の0.8倍のダメージ
     * - 全体攻撃なので単体攻撃より威力は低いが、複数を同時に攻撃できる
     *
     * 使用例：
     * - 味方が複数いる場合に効果的
     * - 戦闘を一気に有利に進められる強力な技
     *
     * @param targets 攻撃対象のリスト（通常はすべての味方）
     */
    public void fireBreath(List<BattleUnit> targets) {
        // 攻撃対象が有効かチェック
        if (targets == null || targets.isEmpty()) {
            System.out.println("攻撃対象が指定されていません！");
            return;
        }

        // 派手な演出メッセージを表示
        System.out.println("★★★ " + getName() + "は灼熱の炎を吐いた！ ★★★");
        System.out.println("炎が全体を包み込む！");

        // 全体攻撃は通常攻撃の0.8倍のダメージ
        int damage = (int)(getPower() * 0.8);

        // すべての対象にダメージを与える
        for (BattleUnit target : targets) {
            // 対象が有効で生存している場合のみダメージを与える
            if (target != null && target.isAlive()) {
                target.takeDamage(damage);
            }
        }
    }

    /**
     * ドラゴンの攻撃（オーバーライド）
     *
     * 通常は「ひっかく」攻撃を行います。
     * 全体攻撃「ファイアーブレス」はBattleSystemから確率的に呼び出されます。
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

        // 「ひっかく」攻撃を実行
        scratch(battleTarget);
    }

    // ========================================
    // ステータス表示
    // ========================================

    /**
     * ステータス表示メソッド（オーバーライド）
     *
     * ボス敵専用の派手なフォーマットでステータスを表示します。
     * 通常の敵とは異なり、「★ BOSS ★」の表示でボスであることを強調します。
     */
    @Override
    public void displayStatus() {
        System.out.println("========== ★ BOSS ★ ==========");
        System.out.println("Name: " + getName());
        System.out.println("Type: " + getType());
        System.out.println("Level: " + getLevel());
        System.out.println("HP: " + getHp() + "/" + getMaxHp());
        System.out.println("Power: " + getPower());
        System.out.println("Speed: " + getSpeed());
        System.out.println("Exp: " + getExp());
        System.out.println("Money: " + getMoney());
        System.out.println("==============================");
    }

} // Dragon クラス終了

