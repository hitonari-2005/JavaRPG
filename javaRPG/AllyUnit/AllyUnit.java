package javaRPG.AllyUnit;

// 必要なクラスをインポート
import javaRPG.BattleUnit.BattleUnit;  // 戦闘ユニットの基底クラス

/**
 * 味方キャラクターの基底クラス
 *
 * このクラスはプレイヤーが操作する味方キャラクター（勇者、魔法使い、魔法剣士など）の
 * 共通機能を提供します。BattleUnitを継承し、経験値システム、お金の管理、
 * レベルアップ機能などのRPG要素を実装しています。
 *
 * 主な機能：
 * - 経験値の獲得とレベルアップシステム
 * - お金の獲得と使用（ショップでの購入など）
 * - 戦闘勝利時の報酬処理
 * - レベルアップ時のステータス上昇
 *
 * 継承関係：
 * BattleUnit（親クラス）
 *   └─ AllyUnit（このクラス）
 *       ├─ Hero（勇者）
 *       ├─ Mage（魔法使い）
 *       └─ MagicSwordsman（魔法剣士）
 *
 * @author RPG開発チーム
 * @version 1.0
 */
public class AllyUnit extends BattleUnit {

    // ========================================
    // フィールド（属性）- 味方固有のステータス
    // ========================================

    /** 現在の経験値 - 戦闘に勝利すると増加する */
    private int currentXp;

    /** レベルアップまでの残り経験値 - 0以下になるとレベルアップ */
    private int remainingXp;

    /** 所持金（ゴールド） - ショップでアイテムを購入する際に使用 */
    private int money;

    // ========================================
    // コンストラクタ
    // ========================================

    /**
     * AllyUnitクラスのコンストラクタ（全パラメータ指定）
     *
     * キャラクターの基本ステータスを指定して味方ユニットを作成します。
     * 初期状態では経験値0、所持金100ゴールドでスタートします。
     *
     * @param name キャラクター名（例：「勇者」「魔法使い」など）
     * @param id キャラクターの識別番号
     * @param type キャラクタータイプ（例：「Hero」「Mage」など）
     * @param power 基本攻撃力
     * @param hp 初期HP（最大HPとしても設定される）
     * @param speed 素早さ（行動順序の決定に使用）
     */
    public AllyUnit(String name, int id, String type, int power, int hp, int speed) {
        // 親クラス（BattleUnit）のコンストラクタを呼び出す
        super(name, id, type, power, hp, speed);

        // 経験値システムの初期化
        this.currentXp = 0;        // 初期経験値は0からスタート
        this.remainingXp = 100;    // レベルアップまで100経験値必要

        // 所持金の初期化
        this.money = 100;          // 初期所持金は100ゴールド
    }

    /**
     * AllyUnitクラスのデフォルトコンストラクタ
     *
     * パラメータなしで呼び出された場合、デフォルト値で味方ユニットを作成します。
     */
    public AllyUnit() {
        super();  // 親クラスのデフォルトコンストラクタを呼び出す
        this.currentXp = 0;
        this.remainingXp = 100;
        this.money = 100;
    }

    // ========================================
    // 戦闘報酬関連メソッド
    // ========================================

    /**
     * 経験値を獲得するメソッド
     *
     * 戦闘に勝利した際に呼び出され、経験値を獲得します。
     * 獲得した経験値が一定量に達するとレベルアップします。
     *
     * @param exp 獲得する経験値の量
     */
    public void winGetExp(int exp) {
        System.out.println(getName() + "は" + exp + "の経験値を獲得した！");

        // 経験値を加算
        this.currentXp += exp;

        // 残り経験値を減算
        this.remainingXp -= exp;

        // レベルアップ判定：残り経験値が0以下になったらレベルアップ
        if (this.remainingXp <= 0) {
            levelUp();
        }
    }

    /**
     * お金を獲得するメソッド
     *
     * 戦闘に勝利した際に呼び出され、お金（ゴールド）を獲得します。
     * 獲得したお金はショップでアイテムを購入する際に使用できます。
     *
     * @param money 獲得するお金の量（ゴールド）
     */
    public void winGetMoney(int money) {
        this.money += money;  // 所持金に加算
        System.out.println(getName() + "は" + money + "ゴールドを獲得した！");
        System.out.println("所持金: " + this.money + "G");
    }

    // ========================================
    // レベルアップシステム
    // ========================================

    /**
     * レベルアップ処理
     *
     * 経験値が一定量に達したときに自動的に呼び出されます。
     * レベルアップすると以下のステータスが上昇します：
     * - 最大HP: +10
     * - 攻撃力: +5
     * - 素早さ: +2
     *
     * レベルアップ時にはHPが全回復し、次のレベルアップまでの経験値がリセットされます。
     */
    private void levelUp() {
        System.out.println("★ " + getName() + "はレベルアップした！ ★");

        // ステータス上昇量を定義
        int hpUp = 10;      // 最大HPの上昇量
        int powerUp = 5;    // 攻撃力の上昇量
        int speedUp = 2;    // 素早さの上昇量

        // ステータスを上昇させる
        setMaxHp(getMaxHp() + hpUp);      // 最大HPを増加
        setHp(getMaxHp());                // HPを全回復
        setPower(getPower() + powerUp);   // 攻撃力を増加
        setSpeed(getSpeed() + speedUp);   // 素早さを増加

        // 上昇したステータスを表示
        System.out.println("  最大HP +" + hpUp);
        System.out.println("  攻撃力 +" + powerUp);
        System.out.println("  素早さ +" + speedUp);

        // 次のレベルアップまでの経験値をリセット
        this.currentXp = 0;
        this.remainingXp = 100;  // 次のレベルアップまで100経験値必要
    }

    // ========================================
    // 装備関連メソッド
    // ========================================

    /**
     * 装備変更メソッド（将来的に実装予定）
     *
     * 現在は基本的な実装のみで、実際の装備変更処理は
     * 各サブクラス（Hero、Mageなど）で実装されています。
     */
    public void equip() {
        System.out.println(getName() + "は装備を変更した！");
    }

    // ========================================
    // ゲッター・セッターメソッド
    // ========================================

    /**
     * 現在の経験値を取得
     *
     * @return 現在の経験値
     */
    public int getCurrentXp() {
        return currentXp;
    }

    /**
     * レベルアップまでの残り経験値を取得
     *
     * @return 残り経験値（0以下になるとレベルアップ）
     */
    public int getRemainingXp() {
        return remainingXp;
    }

    /**
     * 所持金を取得
     *
     * @return 現在の所持金（ゴールド）
     */
    public int getMoney() {
        return money;
    }

    /**
     * お金を使用する（支払い処理）
     *
     * ショップでアイテムを購入する際などに使用します。
     * 所持金が足りない場合は支払いに失敗します。
     *
     * @param amount 支払う金額
     * @return 支払いに成功した場合true、所持金不足の場合false
     */
    public boolean spendMoney(int amount) {
        // 所持金が支払い額以上あるかチェック
        if (this.money >= amount) {
            this.money -= amount;  // 所持金から減算
            return true;           // 支払い成功
        }
        return false;  // 所持金不足で支払い失敗
    }

    /**
     * お金を追加する
     *
     * 戦闘報酬やアイテム売却などでお金を獲得する際に使用します。
     *
     * @param amount 追加する金額
     */
    public void addMoney(int amount) {
        this.money += amount;
    }

    // ========================================
    // ステータス表示
    // ========================================

    /**
     * ステータス表示メソッド（オーバーライド）
     *
     * 親クラスのステータス表示に加えて、経験値と所持金の情報を表示します。
     * ゲーム内でプレイヤーが現在の状態を確認する際に使用されます。
     */
    @Override
    public void displayStatus() {
        super.displayStatus();  // 親クラスのステータス（HP、攻撃力など）を表示
        System.out.println("経験値: " + currentXp + " (次のレベルまで: " + remainingXp + ")");
        System.out.println("所持金: " + money + "G");
        System.out.println("====================");
    }

} // AllyUnit クラス終了
