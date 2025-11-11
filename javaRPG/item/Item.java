package javaRPG.item;

/**
 * 装備品や道具の基底クラス
 * 全てのアイテムの基本属性を定義する
 *
 * 要件定義書 2.4より:
 * - 剣(Sword)と杖(Staff)の基底クラスとなる
 * - 全てのアイテムの基本属性を定義する
 */
public abstract class Item {
    /** アイテム名 */
    protected String name;

    /** アイテムの説明 */
    protected String description;

    /**
     * Itemクラスのコンストラクタ
     *
     * @param name アイテム名
     * @param description アイテムの説明
     */
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * アイテム名を取得する
     *
     * @return アイテム名
     */
    public String getName() {
        return name;
    }

    /**
     * アイテムの説明を取得する
     *
     * @return アイテムの説明
     */
    public String getDescription() {
        return description;
    }

    /**
     * アイテムの情報を表示する
     *
     * @return アイテムの情報文字列
     */
    @Override
    public String toString() {
        return String.format("%s: %s", name, description);
    }

    /**
     * アイテムを使用した際の効果を定義する抽象メソッド
     * サブクラスで具体的な実装を行う
     */
    public abstract void displayInfo();
}
