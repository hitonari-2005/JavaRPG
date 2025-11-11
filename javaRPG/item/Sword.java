package javaRPG.item;

import javaRPG.Interface.Sellable;

/**
 * 剣クラス
 * 剣を扱うキャラクター(Hero、MagicSwordsman)が装備できる武器
 *
 * 要件定義書 2.2, 2.4より:
 * - Itemクラスを継承
 * - Sellableインターフェースを実装（売買可能）
 * - Hero、MagicSwordsmanとHas-A関係にある
 * - 攻撃力ボーナスを提供
 */
public class Sword extends Item implements Sellable {
    /** 攻撃力ボーナス */
    private int attackBonus;

    /** 価格 */
    private int price;

    /**
     * Swordクラスのコンストラクタ
     *
     * @param name 剣の名前
     * @param description 剣の説明
     * @param attackBonus 攻撃力ボーナス
     * @param price 価格
     */
    public Sword(String name, String description, int attackBonus, int price) {
        super(name, description);
        this.attackBonus = attackBonus;
        this.price = price;
    }

    /**
     * 攻撃力ボーナスを取得する
     *
     * @return 攻撃力ボーナス
     */
    public int getAttackBonus() {
        return attackBonus;
    }

    /**
     * 価格を取得する（Sellableインターフェースの実装）
     *
     * @return 価格
     */
    @Override
    public int getPrice() {
        return price;
    }

    /**
     * 剣の詳細情報を表示する
     */
    @Override
    public void displayInfo() {
        System.out.println("====================================");
        System.out.println("【剣】 " + name);
        System.out.println("------------------------------------");
        System.out.println("説明: " + description);
        System.out.println("攻撃力: +" + attackBonus);
        System.out.println("価格: " + price + "G");
        System.out.println("====================================");
    }

    /**
     * 剣の情報を文字列として返す
     *
     * @return 剣の情報文字列
     */
    @Override
    public String toString() {
        return String.format("%s [攻撃+%d] - %dG", name, attackBonus, price);
    }
}
