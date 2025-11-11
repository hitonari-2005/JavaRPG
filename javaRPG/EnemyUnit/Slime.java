package javaRPG.EnemyUnit;

import javaRPG.BattleUnit.BattleUnit;

/**
 * スライムクラス
 * EnemyUnitを継承した敵キャラクター
 * 特殊能力: HPが最大HPの半分以下になると自動的にHPを回復する
 */
public class Slime extends EnemyUnit {

    private boolean hasRecovered;  // 回復済みフラグ

    // コンストラクタ
    public Slime(int level) {
        super("スライム", 100 + level, "Slime", level);
        this.hasRecovered = false;
    }

    // デフォルトコンストラクタ（レベル1）
    public Slime() {
        this(1);
    }

    /**
     * ダメージを受けた後の特殊処理
     * HPが半分以下になったら自動回復
     */
    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);

        // HPが最大HPの半分以下になった場合、まだ回復していなければ回復
        if (!hasRecovered && isAlive() && getHp() <= getMaxHp() / 2) {
            hpHalfRecover();
        }
    }

    /**
     * HP半分以下で発動する自動回復
     */
    private void hpHalfRecover() {
        hasRecovered = true;
        int recoverAmount = getMaxHp() / 3;  // 最大HPの1/3を回復

        System.out.println("★ " + getName() + "は身体を再生した！ ★");
        heal(recoverAmount);
    }

    /**
     * スライム専用の攻撃
     * 通常の攻撃をオーバーライド
     */
    @Override
    public void attack(Object target) {
        if (!(target instanceof BattleUnit)) {
            System.out.println("攻撃対象が無効です！");
            return;
        }

        BattleUnit battleTarget = (BattleUnit) target;
        System.out.println(getName() + "の体当たり！");

        int damage = getPower();
        battleTarget.takeDamage(damage);
    }

}
