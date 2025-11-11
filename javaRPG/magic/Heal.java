package javaRPG.magic;

import javaRPG.BattleUnit.BattleUnit;
import java.util.List;

/**
 * ヒール魔法クラス
 * 回復属性の補助魔法です
 *
 * 味方単体または複数のHPを回復します
 * 回復量は魔法の基本威力と使用者の能力によって決まります
 *
 * @author RPG開発チーム
 * @version 1.0
 */
public class Heal extends Magic {

    /**
     * Healクラスのコンストラクタ
     * デフォルトの回復量40で回復魔法を作成します
     */
    public Heal() {
        super("ヒール", 40, 'H', "回復");
    }

    /**
     * カスタム回復量でHealを作成するコンストラクタ
     *
     * @param power 回復量（魔法の威力）
     */
    public Heal(int power) {
        super("ヒール", power, 'H', "回復");
    }

    /**
     * ヒール魔法を発動する
     * 対象のHPを回復します
     *
     * @param caster 魔法を使用するキャラクター
     * @param targets 魔法の対象リスト（複数可）
     */
    @Override
    public void cast(BattleUnit caster, List<BattleUnit> targets) {
        System.out.println(caster.getName() + "は" + magicName + "を唱えた！");
        System.out.println("温かい光が仲間を包み込む！");

        // 全ての対象を回復する
        for (BattleUnit target : targets) {
            // 生存している対象のみを回復する
            if (target.isAlive()) {
                // 回復量計算：基本威力 + 使用者の攻撃力の1/3
                int healAmount = this.power + (caster.getPower() / 3);

                // 対象を回復する
                target.heal(healAmount);
            }
        }
    }
}
