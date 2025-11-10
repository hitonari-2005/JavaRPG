package javaRPG.AllyUnit;

// BattleUnitクラスをインポート
import javaRPG.BattleUnit.BattleUnit;

// 味方キャラクターの基底クラス
// BattleUnitを継承して、経験値やお金の獲得に関するロジックを定義する
public class AllyUnit extends BattleUnit {

    // フィールド（属性）- 味方固有のステータス
    private int currentXp;    // 現在の経験値
    private int remainingXp;  // レベルアップまでの残り経験値

    // コンストラクタ（全パラメータ指定）
    public AllyUnit(String name, int id, String type, int power, int hp, int speed) {
        // 親クラス（BattleUnit）のコンストラクタを呼び出す
        super(name, id, type, power, hp, speed);
        this.currentXp = 0;        // 初期経験値は0
        this.remainingXp = 100;    // レベルアップまで100経験値必要
    }

    // デフォルトコンストラクタ
    public AllyUnit() {
        super();
        this.currentXp = 0;
        this.remainingXp = 100;
    }

    // 経験値を獲得するメソッド
    public void winGetExp(int exp) {
        System.out.println(getName() + "は" + exp + "の経験値を獲得した！");
        this.currentXp += exp;
        this.remainingXp -= exp;

        // レベルアップ判定
        if (this.remainingXp <= 0) {
            levelUp();
        }
    }

    // お金を獲得するメソッド
    public void winGetExp(int exp, int money) {
        winGetExp(exp);
        System.out.println(getName() + "は" + money + "ゴールドを獲得した！");
    }

    // レベルアップ処理
    private void levelUp() {
        System.out.println("★ " + getName() + "はレベルアップした！ ★");

        // ステータスアップ
        int hpUp = 10;
        int powerUp = 5;
        int speedUp = 2;

        setMaxHp(getMaxHp() + hpUp);
        setHp(getMaxHp());  // HPを全回復
        setPower(getPower() + powerUp);
        setSpeed(getSpeed() + speedUp);

        System.out.println("  最大HP +" + hpUp);
        System.out.println("  攻撃力 +" + powerUp);
        System.out.println("  素早さ +" + speedUp);

        // 次のレベルアップまでの経験値を設定
        this.currentXp = 0;
        this.remainingXp = 100;  // 次のレベルアップまで
    }

    // 装備メソッド（将来的に実装）
    public void equip() {
        System.out.println(getName() + "は装備を変更した！");
    }

    // getter・setterメソッド
    // 現在の経験値を取得
    public int getCurrentXp() {
        return currentXp;
    }

    // 残り経験値を取得
    public int getRemainingXp() {
        return remainingXp;
    }

    // ステータス表示メソッド（オーバーライド）
    @Override
    public void displayStatus() {
        super.displayStatus();
        System.out.println("経験値: " + currentXp + " (次のレベルまで: " + remainingXp + ")");
        System.out.println("====================");
    }

}
