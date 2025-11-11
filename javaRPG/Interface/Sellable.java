package javaRPG.Interface;

/**
 * Sellable interface
 * アイテムの売買機能を提供するインターフェース
 * 商人が販売できるアイテムが実装する
 */
public interface Sellable {

    /**
     * アイテムの価格を取得する
     * @return アイテムの価格（ゴールド）
     */
    int getPrice();

    /**
     * アイテムの名前を取得する
     * @return アイテムの名前
     */
    String getName();

}
