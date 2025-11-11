package javaRPG.EnemyUnit;

import javaRPG.BattleUnit.BattleUnit;

/**
 * ゴブリンクラス
 * EnemyUnitを継承した敵キャラクター
 * 特殊能力: HPが最大HPの半分以下になると攻撃力がアップする
 */
public class Goblin extends EnemyUnit {

    private boolean hasPoweredUp;      // パワーアップ済みフラグ
    private int powerUpAmount;         // 攻撃力アップ量

    // コンストラクタ
    public Goblin(int level) {
        super("ゴブリン", 200 + level, "Goblin", level);
        this.hasPoweredUp = false;
        this.powerUpAmount = 5 + (level * 2);  // レベルに応じてパワーアップ量も増加
    }

    // デフォルトコンストラクタ（レベル1）
    public Goblin() {
        this(1);
    }

    /**
     * ダメージを受けた後の特殊処理
     * HPが半分以下になったら攻撃力アップ
     */
    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);

        // HPが最大HPの半分以下になった場合、まだパワーアップしていなければパワーアップ
        if (!hasPoweredUp && isAlive() && getHp() <= getMaxHp() / 2) {
            hpHalfPowerUp();
        }
    }

    /**
     * HP半分以下で発動する攻撃力アップ
     */
    private void hpHalfPowerUp() {
        hasPoweredUp = true;
        setPower(getPower() + powerUpAmount);

        System.out.println("★ " + getName() + "は怒り狂った！ ★");
        System.out.println("攻撃力が" + powerUpAmount + "アップした！");
    }

    /**
     * ゴブリン専用の攻撃
     * 通常の攻撃をオーバーライド
     */
    @Override
    public void attack(Object target) {
        if (!(target instanceof BattleUnit)) {
            System.out.println("攻撃対象が無効です！");
            return;
        }

        BattleUnit battleTarget = (BattleUnit) target;
        System.out.println(getName() + "の棍棒攻撃！");

        int damage = getPower();
        battleTarget.takeDamage(damage);
    }

}
