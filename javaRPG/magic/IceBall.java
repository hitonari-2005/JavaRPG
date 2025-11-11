package javaRPG.magic;

import javaRPG.BattleUnit.BattleUnit;
import java.util.List;

/**
 * アイスボール魔法クラス
 * 氷属性の攻撃魔法です
 *
 * 敵単体または複数に氷属性ダメージを与えます
 * 威力は使用者の魔力と魔法の基本威力によって決まります
 * 火属性魔法より若干威力が高く設定されています
 *
 * @author RPG開発チーム
 * @version 1.0
 */
public class IceBall extends Magic {

    /**
     * IceBallクラスのコンストラクタ
     * デフォルトの威力35で氷属性の攻撃魔法を作成します
     */
    public IceBall() {
        super("アイスボール", 35, 'I', "攻撃");
    }

    /**
     * カスタム威力でIceBallを作成するコンストラクタ
     *
     * @param power 魔法の威力
     */
    public IceBall(int power) {
        super("アイスボール", power, 'I', "攻撃");
    }

    /**
     * アイスボール魔法を発動する
     * 対象に氷属性のダメージを与えます
     *
     * @param caster 魔法を使用するキャラクター
     * @param targets 魔法の対象リスト（複数可）
     */
    @Override
    public void cast(BattleUnit caster, List<BattleUnit> targets) {
        System.out.println(caster.getName() + "は" + magicName + "を唱えた！");
        System.out.println("凍てつく氷の弾が敵を襲う！");

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
