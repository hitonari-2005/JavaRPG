package javaRPG.item;

import javaRPG.Interface.Sellable;

/**
 * SellableUnit クラス
 * アイテムの売買機能を持つユニットの基底クラス
 *
 * 要件定義書 2.1より:
 * - Sellableインターフェースを実装
 * - 売買可能なアイテムの共通機能を提供
 */
public abstract class SellableUnit extends Item implements Sellable {

    protected int price;  // 価格

    /**
     * SellableUnitのコンストラクタ
     *
     * @param name アイテム名
     * @param description アイテムの説明
     * @param price 価格
     */
    public SellableUnit(String name, String description, int price) {
        super(name, description);
        this.price = price;
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
     * 価格を設定する
     *
     * @param price 新しい価格
     */
    public void setPrice(int price) {
        this.price = price;
    }

}
