package javaRPG.BattleUnit;

// 必要なクラスとインターフェースをインポート
import javaRPG.Character.Character;  // 基底キャラクタークラス
import javaRPG.Interface.Battlable;   // 戦闘可能インターフェース

/**
 * 戦闘に参加するすべてのユニットの基底クラス
 *
 * このクラスはCharacterクラスを継承し、戦闘に関する機能を追加した
 * 抽象クラスです。味方・敵を問わず、戦闘に参加できるユニット全てが
 * このクラスを継承します。
 *
 * 主な役割：
 * - 戦闘ステータス（HP、攻撃力、素早さ）の管理
 * - 戦闘アクション（攻撃、防御、回復）の実装
 * - Battlableインターフェースの実装による戦闘システムとの連携
 *
 * 継承関係：
 * Character → BattleUnit → AllyUnit/EnemyUnit
 *
 * 実装するインターフェース：
 * - Battlable（戦闘可能な機能を定義）
 *
 * @author RPG開発チーム
 * @version 1.0
 */
public abstract class BattleUnit extends Character implements Battlable {

    // ========================================
    // フィールド（属性）
    // ========================================

    /**
     * 攻撃力
     * このユニットが攻撃したときに与えるダメージの基本値
     * 装備品やバフ効果で変動する可能性がある
     */
    private int power;

    /**
     * 現在のHP（ヒットポイント）
     * このユニットの残り体力
     * 0になると戦闘不能（倒れる）状態になる
     */
    private int hp;

    /**
     * 最大HP
     * このユニットが持つことのできる最大の体力
     * レベルアップや装備で増加する
     */
    private int maxHp;

    /**
     * 素早さ
     * 行動順序を決定するパラメータ
     * 数値が高いほど先に行動できる
     */
    private int speed;

    /**
     * 現在のMP（マジックポイント）
     * このユニットの残り魔法力
     * 0になると魔法や必殺技が使用できなくなる
     */
    private int mp;

    /**
     * 最大MP
     * このユニットが持つことのできる最大の魔法力
     * レベルアップや装備で増加する
     */
    private int maxMp;

    /**
     * 防御中フラグ
     * trueの場合、次に受けるダメージが半減される
     * ダメージを受けると自動的にfalseに戻る
     */
    private boolean isDefending;

    // ========================================
    // コンストラクタ
    // ========================================

    /**
     * すべてのパラメータを指定してBattleUnitを生成するコンストラクタ
     *
     * 子クラス（AllyUnit、EnemyUnit）から呼び出され、
     * 戦闘ユニットの基本ステータスを初期化します。
     *
     * @param name キャラクターの名前
     * @param id キャラクターの識別ID
     * @param type キャラクターのタイプ
     * @param power 攻撃力
     * @param hp 初期HP（これが最大HPにもなります）
     * @param speed 素早さ
     */
    public BattleUnit(String name, int id, String type, int power, int hp, int speed) {
        // 親クラス（Character）のコンストラクタを呼び出し、基本情報を初期化
        super(name, id, type);

        // 戦闘ステータスを初期化
        this.power = power;
        this.hp = hp;
        this.maxHp = hp;  // 初期HPを最大HPとして設定
        this.speed = speed;

        // MP初期化（基本値30、子クラスで上書き可能）
        this.mp = 30;
        this.maxMp = 30;

        this.isDefending = false;  // 初期状態では防御していない
    }

    /**
     * デフォルトコンストラクタ
     *
     * パラメータなしで戦闘ユニットを生成する場合に使用されます。
     * デフォルト値で初期化されます：
     * - 攻撃力：10
     * - HP/最大HP：50
     * - MP/最大MP：30
     * - 素早さ：5
     */
    public BattleUnit() {
        // Characterのデフォルトコンストラクタを呼び出す
        super();

        // デフォルトの戦闘ステータス
        this.power = 10;
        this.hp = 50;
        this.maxHp = 50;
        this.mp = 30;
        this.maxMp = 30;
        this.speed = 5;
        this.isDefending = false;
    }

    // ========================================
    // 戦闘アクションメソッド
    // ========================================

    /**
     * 通常攻撃を行うメソッド
     *
     * このユニットが対象に通常攻撃を行います。
     * Battlableインターフェースで定義されたメソッドの実装です。
     *
     * 処理の流れ：
     * 1. 対象がBattleUnitのインスタンスかチェック
     * 2. 攻撃メッセージを表示
     * 3. ダメージを計算（基本は攻撃力そのまま）
     * 4. 対象のtakeDamage()メソッドを呼び出してダメージを与える
     *
     * @param target 攻撃対象（Objectとして受け取るがBattleUnitである必要がある）
     */
    @Override
    public void attack(Object target) {
        // 対象がBattleUnitのインスタンスかどうかをチェック
        if (!(target instanceof BattleUnit)) {
            System.out.println("攻撃対象が無効です！");
            return;
        }

        // Objectをbattleunitにキャスト
        BattleUnit battleTarget = (BattleUnit) target;

        // 攻撃メッセージを表示
        System.out.println(getName() + "の攻撃！");

        // ダメージを計算（基本は攻撃力そのまま）
        // 子クラスでオーバーライドして特殊な計算も可能
        int damage = this.power;

        // 対象にダメージを与える
        battleTarget.takeDamage(damage);
    }

    /**
     * ダメージを受けるメソッド
     *
     * このユニットがダメージを受けたときの処理を行います。
     * Battlableインターフェースで定義されたメソッドの実装です。
     *
     * 処理の流れ：
     * 1. 防御中かチェックし、防御中ならダメージを半減
     * 2. HPからダメージを減算
     * 3. ダメージ量とHP状況を表示
     * 4. HPが0以下になったら戦闘不能メッセージを表示
     *
     * @param damage 受けるダメージ量
     */
    @Override
    public void takeDamage(int damage) {
        // 防御中の場合、ダメージを半減
        if (isDefending) {
            damage = damage / 2;  // ダメージを50%カット
            System.out.println(getName() + "は防御している！");
            System.out.println("ダメージが半減した！");
            isDefending = false;  // 防御効果は一度使用したら解除
        }

        // HPからダメージを減算
        this.hp -= damage;

        // ダメージメッセージを表示
        System.out.println(getName() + "は" + damage + "のダメージを受けた！");

        // HPが0未満にならないように調整
        if (this.hp < 0) {
            this.hp = 0;
        }

        // 残りHP状況を表示
        System.out.println(getName() + "の残りHP: " + this.hp + "/" + this.maxHp);

        // HPが0になった場合、戦闘不能メッセージを表示
        if (this.hp == 0) {
            System.out.println(getName() + "は倒れた！");
        }
    }

    /**
     * HPを回復するメソッド
     *
     * このユニットのHPを指定された量だけ回復します。
     * Battlableインターフェースで定義されたメソッドの実装です。
     *
     * 注意：
     * - setHp()メソッドを使用するため、最大HPを超えて回復することはない
     * - 0未満にもならない
     *
     * @param amount 回復するHP量
     */
    @Override
    public void heal(int amount) {
        // setHp()を使用してHPを回復（最大HP制限が自動適用される）
        this.setHp(this.hp + amount);

        // 回復メッセージを表示
        System.out.println(getName() + "は" + amount + "回復した！");
        System.out.println(getName() + "の現在HP: " + this.hp + "/" + this.maxHp);
    }

    /**
     * 生存確認メソッド
     *
     * このユニットが生きているかどうかを判定します。
     * Battlableインターフェースで定義されたメソッドの実装です。
     *
     * @return HPが0より大きい場合true、0以下の場合false
     */
    @Override
    public boolean isAlive() {
        return this.hp > 0;
    }

    /**
     * 防御態勢を取るメソッド
     *
     * このユニットを防御状態にします。
     * 防御状態では、次に受けるダメージが半減されます。
     *
     * 注意：
     * - 防御効果は次にダメージを受けるまで持続
     * - ダメージを受けると自動的に解除される
     */
    public void defend() {
        this.isDefending = true;
        System.out.println(getName() + "は防御態勢を取った！");
        System.out.println("次に受けるダメージが半減します！");
    }

    /**
     * 防御状態を解除するメソッド
     *
     * ターン開始時などに防御状態を強制的に解除する際に使用します。
     * 通常はダメージを受けた時に自動的に解除されます。
     */
    public void clearDefense() {
        this.isDefending = false;
    }

    /**
     * 防御中かどうかを取得
     *
     * @return 防御中の場合true、そうでない場合false
     */
    public boolean isDefending() {
        return isDefending;
    }

    // ========================================
    // MP管理メソッド
    // ========================================

    /**
     * MPを消費するメソッド
     *
     * 魔法や必殺技を使用する際にMPを消費します。
     * MPが足りない場合は消費に失敗し、falseを返します。
     *
     * @param amount 消費するMP量
     * @return MPが足りて消費できた場合true、MPが足りない場合false
     */
    public boolean consumeMp(int amount) {
        // MPが足りるかチェック
        if (this.mp >= amount) {
            this.mp -= amount;  // MPを減算
            return true;        // 消費成功
        } else {
            System.out.println(getName() + "のMPが足りない！");
            return false;       // 消費失敗
        }
    }

    /**
     * MPを回復するメソッド
     *
     * このユニットのMPを指定された量だけ回復します。
     * 最大MPを超えて回復することはありません。
     *
     * @param amount 回復するMP量
     */
    public void restoreMp(int amount) {
        this.mp += amount;

        // 最大MPを超えないようにする
        if (this.mp > this.maxMp) {
            this.mp = this.maxMp;
        }

        // 回復メッセージを表示
        System.out.println(getName() + "のMPが" + amount + "回復した！");
        System.out.println(getName() + "の現在MP: " + this.mp + "/" + this.maxMp);
    }

    // ========================================
    // 表示メソッド
    // ========================================

    /**
     * ステータスを画面に表示するメソッド
     *
     * このユニットの現在の状態（名前、タイプ、HP、MP、攻撃力、素早さ）を
     * わかりやすく整形して表示します。
     *
     * 表示内容：
     * - 名前
     * - タイプ
     * - HP（現在値/最大値）
     * - MP（現在値/最大値）
     * - 攻撃力
     * - 素早さ
     */
    public void displayStatus() {
        System.out.println("====================");
        System.out.println("名前: " + getName());
        System.out.println("タイプ: " + getType());
        System.out.println("HP: " + hp + "/" + maxHp);
        System.out.println("MP: " + mp + "/" + maxMp);
        System.out.println("攻撃力: " + power);
        System.out.println("素早さ: " + speed);
        System.out.println("====================");
    }

    // ========================================
    // getter・setterメソッド
    // ========================================
    // 各ステータスにアクセスするためのメソッド群
    // カプセル化の原則に従い、privateフィールドへの安全なアクセスを提供

    /**
     * 攻撃力を取得
     *
     * @return 現在の攻撃力
     */
    public int getPower() {
        return power;
    }

    /**
     * 攻撃力を設定
     *
     * レベルアップや装備変更時に使用されます。
     *
     * @param power 新しい攻撃力
     */
    public void setPower(int power) {
        this.power = power;
    }

    /**
     * 現在のHPを取得
     *
     * @return 現在のHP
     */
    public int getHp() {
        return hp;
    }

    /**
     * HPを設定
     *
     * 回復や直接HP操作時に使用されます。
     * 自動的に最大HPを超えないよう、また0未満にならないよう調整されます。
     *
     * @param hp 新しいHP値
     */
    public void setHp(int hp) {
        this.hp = hp;

        // 最大HPを超えないようにする
        if (this.hp > this.maxHp) {
            this.hp = this.maxHp;
        }

        // 0未満にならないようにする
        if (this.hp < 0) {
            this.hp = 0;
        }
    }

    /**
     * 最大HPを取得
     *
     * @return 最大HP
     */
    public int getMaxHp() {
        return maxHp;
    }

    /**
     * 最大HPを設定
     *
     * レベルアップ時などに使用されます。
     *
     * @param maxHp 新しい最大HP
     */
    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    /**
     * 素早さを取得
     *
     * @return 現在の素早さ
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * 素早さを設定
     *
     * レベルアップや装備変更時に使用されます。
     *
     * @param speed 新しい素早さ
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * 現在のMPを取得
     *
     * @return 現在のMP
     */
    public int getMp() {
        return mp;
    }

    /**
     * MPを設定
     *
     * MP回復や直接MP操作時に使用されます。
     * 自動的に最大MPを超えないよう、また0未満にならないよう調整されます。
     *
     * @param mp 新しいMP値
     */
    public void setMp(int mp) {
        this.mp = mp;

        // 最大MPを超えないようにする
        if (this.mp > this.maxMp) {
            this.mp = this.maxMp;
        }

        // 0未満にならないようにする
        if (this.mp < 0) {
            this.mp = 0;
        }
    }

    /**
     * 最大MPを取得
     *
     * @return 最大MP
     */
    public int getMaxMp() {
        return maxMp;
    }

    /**
     * 最大MPを設定
     *
     * レベルアップ時などに使用されます。
     *
     * @param maxMp 新しい最大MP
     */
    public void setMaxMp(int maxMp) {
        this.maxMp = maxMp;
    }

}
