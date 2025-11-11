package javaRPG.magic;

import javaRPG.BattleUnit.BattleUnit;
import java.util.List;

/**
 * ファイアボール魔法クラス
 * 火属性の攻撃魔法です
 *
 * 敵単体または複数に火属性ダメージを与えます
 * 威力は使用者の魔力と魔法の基本威力によって決まります
 *
 * @author RPG開発チーム
 * @version 1.0
 */
public class FireBall extends Magic {

    /**
     * FireBallクラスのコンストラクタ
     * デフォルトの威力30で火属性の攻撃魔法を作成します
     */
    public FireBall() {
        super("ファイアボール", 30, 'F', "攻撃");
    }

    /**
     * カスタム威力でFireBallを作成するコンストラクタ
     *
     * @param power 魔法の威力
     */
    public FireBall(int power) {
        super("ファイアボール", power, 'F', "攻撃");
    }

    /**
     * ファイアボール魔法を発動する
     * 対象に火属性のダメージを与えます
     *
     * @param caster 魔法を使用するキャラクター
     * @param targets 魔法の対象リスト（複数可）
     */
    @Override
    public void cast(BattleUnit caster, List<BattleUnit> targets) {
        System.out.println(caster.getName() + "は" + magicName + "を唱えた！");
        System.out.println("灼熱の炎が敵を包み込む！");

        // 全ての対象にダメージを与える
        for (BattleUnit target : targets) {
            // 生存している対象のみにダメージを与える
            if (target.isAlive()) {
                // ダメージ計算：基本威力 + 使用者の攻撃力の半分
                int damage = this.power + (caster.getPower() / 2);

                // 対象にダメージを与える
                target.takeDamage(damage);
            }
        }
    }
}
