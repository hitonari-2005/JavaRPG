package rpg.item;

import rpg.interfaces.Sellable;

/**
 * 杖クラス
 * 魔法を扱うキャラクター(Mage、MagicSwordsman)が装備できる武器
 *
 * 要件定義書 2.2, 2.4より:
 * - Itemクラスを継承
 * - Sellableインターフェースを実装（売買可能）
 * - Mage、MagicSwordsmanとHas-A関係にある
 * - 魔法力ボーナスを提供
 */
public class Staff extends Item implements Sellable {
    /** 魔法力ボーナス */
    private int magicBonus;

    /** 価格 */
    private int price;

    /**
     * Staffクラスのコンストラクタ
     *
     * @param name 杖の名前
     * @param description 杖の説明
     * @param magicBonus 魔法力ボーナス
     * @param price 価格
     */
    public Staff(String name, String description, int magicBonus, int price) {
        super(name, description);
        this.magicBonus = magicBonus;
        this.price = price;
    }

    /**
     * 魔法力ボーナスを取得する
     *
     * @return 魔法力ボーナス
     */
    public int getMagicBonus() {
        return magicBonus;
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
     * 杖の詳細情報を表示する
     */
    @Override
    public void displayInfo() {
        System.out.println("====================================");
        System.out.println("【杖】 " + name);
        System.out.println("------------------------------------");
        System.out.println("説明: " + description);
        System.out.println("魔法力: +" + magicBonus);
        System.out.println("価格: " + price + "G");
        System.out.println("====================================");
    }

    /**
     * 杖の情報を文字列として返す
     *
     * @return 杖の情報文字列
     */
    @Override
    public String toString() {
        return String.format("%s [魔法+%d] - %dG", name, magicBonus, price);
    }
}
