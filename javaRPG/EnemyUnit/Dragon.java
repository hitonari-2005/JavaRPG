package javaRPG.EnemyUnit;

import javaRPG.BattleUnit.BattleUnit;
import java.util.List;

/**
 * ドラゴンクラス（ボス敵）
 * EnemyUnitを継承した強力な敵キャラクター
 * 特殊能力:
 * 1. HPが最大HPの1/3以下になると攻撃力がアップ
 * 2. 全体攻撃「ファイアーブレス」を使用できる
 * 3. 通常攻撃「ひっかく」は自身の攻撃力を参照する
 */
public class Dragon extends EnemyUnit {

    private boolean hasPoweredUp;      // パワーアップ済みフラグ
    private int powerUpAmount;         // 攻撃力アップ量

    // コンストラクタ
    public Dragon(int level) {
        super("ドラゴン", 999, "Dragon", level);
        this.hasPoweredUp = false;
        this.powerUpAmount = 10 + (level * 3);  // レベルに応じて強力にパワーアップ

        // ドラゴンは強力なボスなので基本ステータスを調整
        setMaxHp(getMaxHp() + 50);
        setHp(getMaxHp());
        setPower(getPower() + 10);
    }

    // デフォルトコンストラクタ（レベル5のボス）
    public Dragon() {
        this(5);
    }

    /**
     * ダメージを受けた後の特殊処理
     * HPが1/3以下になったら攻撃力アップ
     */
    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);

        // HPが最大HPの1/3以下になった場合、まだパワーアップしていなければパワーアップ
        if (!hasPoweredUp && isAlive() && getHp() <= getMaxHp() / 3) {
            powerUpOnThirdHp();
        }
    }

    /**
     * HP1/3以下で発動する攻撃力アップ
     */
    private void powerUpOnThirdHp() {
        hasPoweredUp = true;
        setPower(getPower() + powerUpAmount);

        System.out.println("★★★ " + getName() + "は激怒した！ ★★★");
        System.out.println("攻撃力が" + powerUpAmount + "アップした！");
    }

    /**
     * ドラゴンの通常攻撃「ひっかく」
     * 自身の攻撃力を参照する
     */
    public void scratch(BattleUnit target) {
        if (target == null || !target.isAlive()) {
            System.out.println("攻撃対象が無効です！");
            return;
        }

        System.out.println(getName() + "の鋭い爪が襲いかかる！");

        int damage = getPower();
        target.takeDamage(damage);
    }

    /**
     * 全体攻撃「ファイアーブレス」
     * すべての対象にダメージを与える
     */
    public void fireBreath(List<BattleUnit> targets) {
        if (targets == null || targets.isEmpty()) {
            System.out.println("攻撃対象が指定されていません！");
            return;
        }

        System.out.println("★★★ " + getName() + "は灼熱の炎を吐いた！ ★★★");
        System.out.println("炎が全体を包み込む！");

        // 全体攻撃は通常攻撃の0.8倍のダメージ
        int damage = (int)(getPower() * 0.8);

        for (BattleUnit target : targets) {
            if (target != null && target.isAlive()) {
                target.takeDamage(damage);
            }
        }
    }

    /**
     * ドラゴンの攻撃（オーバーライド）
     * 通常は「ひっかく」攻撃を行う
     */
    @Override
    public void attack(Object target) {
        if (!(target instanceof BattleUnit)) {
            System.out.println("攻撃対象が無効です！");
            return;
        }

        BattleUnit battleTarget = (BattleUnit) target;
        scratch(battleTarget);
    }

    /**
     * ステータス表示（オーバーライド）
     * ボス敵として特別な表示
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

}
