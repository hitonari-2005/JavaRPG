package javaRPG.EnemyUnit;

// 必要なクラスをインポート
import javaRPG.BattleUnit.BattleUnit;  // 戦闘ユニットの基底クラス

/**
 * 敵キャラクターの基底クラス
 *
 * このクラスはプレイヤーと戦う敵キャラクター（スライム、ゴブリン、ドラゴンなど）の
 * 共通機能を提供します。BattleUnitを継承し、レベルシステム、経験値・お金のドロップ、
 * レベルに応じた自動ステータス計算などを実装しています。
 *
 * 主な機能：
 * - レベルに応じた自動ステータス計算
 * - 倒された時の経験値とお金のドロップ
 * - レベルアップ機能（動的なレベル変更）
 * - 敵専用のステータス表示
 *
 * ステータス計算式：
 * - 攻撃力 = 8 + (レベル × 2)
 * - HP = 30 + (レベル × 10)
 * - 素早さ = 5 + レベル
 * - 経験値 = 基本経験値 × レベル
 * - お金 = 基本お金 × レベル
 *
 * 継承関係：
 * BattleUnit（親クラス）
 *   └─ EnemyUnit（このクラス）
 *       ├─ Slime（スライム）
 *       ├─ Goblin（ゴブリン）
 *       └─ Dragon（ドラゴン）
 *
 * @author RPG開発チーム
 * @version 1.0
 */
public class EnemyUnit extends BattleUnit {

    // ========================================
    // フィールド（属性）- 敵固有のステータス
    // ========================================

    /** 敵のレベル - 強さの指標となり、各ステータスの計算に使用される */
    private int level;

    /** 基本経験値 - この値にレベルを掛けて実際の獲得経験値を算出 */
    private int baseExp;

    /** 基本お金（ゴールド） - この値にレベルを掛けて実際の獲得金額を算出 */
    private int baseMoney;

    // ========================================
    // コンストラクタ
    // ========================================

    /**
     * EnemyUnitクラスのコンストラクタ（レベル指定あり）
     *
     * 敵キャラクターをレベルに応じたステータスで作成します。
     * レベルが高いほど、HP、攻撃力、素早さが自動的に増加します。
     *
     * @param name 敵の名前（例：「スライム」「ゴブリン」など）
     * @param id 敵の識別番号
     * @param type 敵のタイプ（例：「Slime」「Goblin」など）
     * @param level 敵のレベル（1以上の整数）
     */
    public EnemyUnit(String name, int id, String type, int level) {
        // 親クラス（BattleUnit）のコンストラクタを呼び出す
        // ステータスはレベルから自動計算される
        super(name, id, type,
              calculatePower(level),   // レベルに応じた攻撃力
              calculateHp(level),      // レベルに応じたHP
              calculateSpeed(level));  // レベルに応じた素早さ

        // レベルと報酬の初期化
        this.level = level;
        this.baseExp = 10;     // 基本経験値は10
        this.baseMoney = 15;   // 基本お金は15ゴールド
    }

    /**
     * EnemyUnitクラスのデフォルトコンストラクタ
     *
     * パラメータなしで呼び出された場合、レベル1の敵を作成します。
     */
    public EnemyUnit() {
        super();  // 親クラスのデフォルトコンストラクタを呼び出す
        this.level = 1;
        this.baseExp = 10;
        this.baseMoney = 15;
    }

    // ========================================
    // ステータス計算メソッド（private static）
    // ========================================

    /**
     * レベルに応じた攻撃力を計算
     *
     * 計算式：攻撃力 = 8 + (レベル × 2)
     * 例：レベル1 = 10、レベル5 = 18、レベル10 = 28
     *
     * @param level 敵のレベル
     * @return 計算された攻撃力
     */
    private static int calculatePower(int level) {
        return 8 + (level * 2);
    }

    /**
     * レベルに応じた最大HPを計算
     *
     * 計算式：HP = 30 + (レベル × 10)
     * 例：レベル1 = 40、レベル5 = 80、レベル10 = 130
     *
     * @param level 敵のレベル
     * @return 計算された最大HP
     */
    private static int calculateHp(int level) {
        return 30 + (level * 10);
    }

    /**
     * レベルに応じた素早さを計算
     *
     * 計算式：素早さ = 5 + レベル
     * 例：レベル1 = 6、レベル5 = 10、レベル10 = 15
     *
     * @param level 敵のレベル
     * @return 計算された素早さ
     */
    private static int calculateSpeed(int level) {
        return 5 + level;
    }

    // ========================================
    // 報酬関連メソッド
    // ========================================

    /**
     * 倒された時に得られる経験値を取得
     *
     * 計算式：獲得経験値 = 基本経験値 × レベル
     * 例：レベル1 = 10、レベル5 = 50、レベル10 = 100
     *
     * @return 獲得できる経験値
     */
    public int getExp() {
        return baseExp * level;
    }

    /**
     * 倒された時に得られるお金を取得
     *
     * 計算式：獲得お金 = 基本お金 × レベル
     * 例：レベル1 = 15G、レベル5 = 75G、レベル10 = 150G
     *
     * @return 獲得できるお金（ゴールド）
     */
    public int getMoney() {
        return baseMoney * level;
    }

    // ========================================
    // ゲッター・セッターメソッド
    // ========================================

    /**
     * 敵のレベルを取得
     *
     * @return 現在のレベル
     */
    public int getLevel() {
        return level;
    }

    /**
     * 敵のレベルを設定
     *
     * レベルを変更すると、それに応じて攻撃力、HP、素早さが自動的に再計算されます。
     * これにより、戦闘回数が増えるごとに敵を強くすることができます。
     *
     * @param level 新しいレベル
     */
    public void setLevel(int level) {
        this.level = level;

        // レベル変更に伴い、全ステータスを再計算
        setPower(calculatePower(level));   // 攻撃力を更新
        setMaxHp(calculateHp(level));      // 最大HPを更新
        setHp(calculateHp(level));         // 現在のHPも最大値に設定
        setSpeed(calculateSpeed(level));   // 素早さを更新
    }

    // ========================================
    // ステータス表示
    // ========================================

    /**
     * 敵のステータス表示メソッド（オーバーライド）
     *
     * 敵専用のフォーマットでステータスを表示します。
     * レベル、経験値、お金などの情報も含まれます。
     */
    @Override
    public void displayStatus() {
        System.out.println("========== ENEMY ==========");
        System.out.println("Name: " + getName());
        System.out.println("Type: " + getType());
        System.out.println("Level: " + level);
        System.out.println("HP: " + getHp() + "/" + getMaxHp());
        System.out.println("Power: " + getPower());
        System.out.println("Speed: " + getSpeed());
        System.out.println("Exp: " + getExp());
        System.out.println("Money: " + getMoney());
        System.out.println("====================");
    }

    // ========================================
    // 戦闘関連メソッド
    // ========================================

    /**
     * 敵が倒された時の処理
     *
     * 敵が倒された際に表示されるメッセージと、
     * プレイヤーが獲得できる経験値とお金の情報を表示します。
     */
    public void defeated() {
        System.out.println(getName() + " defeated!");
        System.out.println("Get Exp: " + getExp());
        System.out.println("Get Money: " + getMoney() + " gold");
    }

} // EnemyUnit クラス終了
