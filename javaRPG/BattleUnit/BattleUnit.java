package javaRPG.BattleUnit;

// Characterクラスをインポート
import javaRPG.Character.Character;
// Battlableインターフェースをインポート
import javaRPG.Interface.Battlable;

// 戦闘に参加するユニットの基底クラス
// CharacterクラスをextendsしてCharacterのメソッドとプロパティを継承
// Battlableインターフェースをimplementsして戦闘に必要なメソッドを実装
public abstract class BattleUnit extends Character implements Battlable {

    // フィールド（属性）- 戦闘に関するステータス
    private int power;      // 攻撃力
    private int hp;         // 現在のHP（ヒットポイント）
    private int maxHp;      // 最大HP
    private int speed;      // 素早さ（行動順を決定）
    private boolean isDefending;  // 防御中フラグ

    // コンストラクタ（全パラメータ指定）
    public BattleUnit(String name, int id, String type, int power, int hp, int speed) {
        // 親クラス（Character）のコンストラクタを呼び出す
        super(name, id, type);
        this.power = power;
        this.hp = hp;
        this.maxHp = hp;  // 初期HPを最大HPとして設定
        this.speed = speed;
        this.isDefending = false;
    }

    // デフォルトコンストラクタ
    public BattleUnit() {
        // Characterのデフォルトコンストラクタを呼び出す
        super();
        this.power = 10;
        this.hp = 50;
        this.maxHp = 50;
        this.speed = 5;
        this.isDefending = false;
    }

    // 攻撃メソッド - 対象にダメージを与える
    // Battlableインターフェースの実装
    public void attack(Object target) {
        // BattleUnitにキャスト
        if (!(target instanceof BattleUnit)) {
            System.out.println("攻撃対象が無効です！");
            return;
        }
        BattleUnit battleTarget = (BattleUnit) target;
        System.out.println(getName() + "の攻撃！");
        // ダメージを計算（基本は攻撃力そのまま）
        int damage = this.power;
        // 対象にダメージを与える
        battleTarget.takeDamage(damage);
    }

    // ダメージを受けるメソッド
    // Battlableインターフェースの実装
    public void takeDamage(int damage) {
        // 防御中の場合、ダメージを半減
        if (isDefending) {
            damage = damage / 2;
            System.out.println(getName() + "は防御している！");
            System.out.println("ダメージが半減した！");
            isDefending = false;  // 防御解除
        }

        this.hp -= damage;
        System.out.println(getName() + "は" + damage + "のダメージを受けた！");

        // HPが0未満にならないようにする
        if (this.hp < 0) {
            this.hp = 0;
        }

        System.out.println(getName() + "の残りHP: " + this.hp + "/" + this.maxHp);

        // HPが0になった場合の処理
        if (this.hp == 0) {
            System.out.println(getName() + "は倒れた！");
        }
    }

    // HPを回復するメソッド
    // Battlableインターフェースの実装
    public void heal(int amount) {
        this.setHp(this.hp + amount);
        
        System.out.println(getName() + "は" + amount + "回復した！");
        System.out.println(getName() + "の現在HP: " + this.hp + "/" + this.maxHp);
    }

    // 生存確認メソッド - HPが0より大きい場合true
    // Battlableインターフェースの実装
    public boolean isAlive() {
        return this.hp > 0;
    }

    // 防御メソッド
    public void defend() {
        this.isDefending = true;
        System.out.println(getName() + "は防御態勢を取った！");
        System.out.println("次に受けるダメージが半減します！");
    }

    // 防御状態を解除（ターン開始時に使用）
    public void clearDefense() {
        this.isDefending = false;
    }

    // 防御中かどうかを取得
    public boolean isDefending() {
        return isDefending;
    }

    // ステータス表示メソッド
    public void displayStatus() {
        System.out.println("====================");
        System.out.println("名前: " + getName());
        System.out.println("タイプ: " + getType());
        System.out.println("HP: " + hp + "/" + maxHp);
        System.out.println("攻撃力: " + power);
        System.out.println("素早さ: " + speed);
        System.out.println("====================");
    }

    // getter・setterメソッド
    // 攻撃力を取得
    public int getPower() {
        return power;
    }

    // 攻撃力を設定
    public void setPower(int power) {
        this.power = power;
    }

    // 現在のHPを取得
    public int getHp() {
        return hp;
    }

    // HPを設定
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

    // 最大HPを取得
    public int getMaxHp() {
        return maxHp;
    }

    // 最大HPを設定
    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    // 素早さを取得
    public int getSpeed() {
        return speed;
    }

    // 素早さを設定
    public void setSpeed(int speed) {
        this.speed = speed;
    }

}
