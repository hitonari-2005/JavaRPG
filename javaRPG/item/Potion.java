package javaRPG.item;

import javaRPG.Interface.Sellable;

/**
 * 回復薬クラス
 * HPを回復するための消費アイテム
 *
 * - Itemクラスを継承
 * - Sellableインターフェースを実装（売買可能）
 * - すべてのキャラクターが使用可能
 * - 購入時に即座にHPを回復
 */
public class Potion extends Item implements Sellable {
    /** 回復量 */
    private int healAmount;

    /** 価格 */
    private int price;

    /**
     * Potionクラスのコンストラクタ
     *
     * @param name 回復薬の名前
     * @param description 回復薬の説明
     * @param healAmount 回復量
     * @param price 価格
     */
    public Potion(String name, String description, int healAmount, int price) {
        super(name, description);
        this.healAmount = healAmount;
        this.price = price;
    }

    /**
     * 回復量を取得する
     *
     * @return 回復量
     */
    public int getHealAmount() {
        return healAmount;
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
     * 回復薬の詳細情報を表示する
     */
    @Override
    public void displayInfo() {
        System.out.println("====================================");
        System.out.println("【回復薬】 " + name);
        System.out.println("------------------------------------");
        System.out.println("説明: " + description);
        System.out.println("回復量: " + healAmount + " HP");
        System.out.println("価格: " + price + "G");
        System.out.println("====================================");
    }

    /**
     * 回復薬の情報を文字列として返す
     *
     * @return 回復薬の情報文字列
     */
    @Override
    public String toString() {
        return String.format("%s [HP+%d] - %dG", name, healAmount, price);
    }
}
