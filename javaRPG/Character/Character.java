package javaRPG.Character;

// RPGゲームのキャラクターを表現するクラス
public abstract class Character {

    // フィールド（属性）
    private String name;  // キャラクターの名前
    private int id;       // キャラクターの識別ID
    private String type;  // キャラクターのタイプ（勇者、魔法使いなど）

    // コンストラクタ（キャラクター生成時に呼ばれる）
    public Character(String name, int id, String type) {
        this.name = name;
        this.id = id;
        this.type = type;
    }

    // デフォルトコンストラクタ
    public Character() {
        this.name = "名無し";
        this.id = 0;
        this.type = "一般人";
    }

    // talk()メソッド - キャラクターが話す
    public void talk() {
        System.out.println(name + "が話しかけてきた！");
        System.out.println("「こんにちは！私は" + type + "の" + name + "です。」");
    }

    // move()メソッド - キャラクターが移動する
    public void move() {
        System.out.println(name + "が移動した！");
    }

    // getter・setterメソッド
    // 名前を取得
    public String getName() {
        return name;
    }

    // 名前を設定
    public void setName(String name) {
        this.name = name;
    }

    // IDを取得
    public int getId() {
        return id;
    }

    // IDを設定
    public void setId(int id) {
        this.id = id;
    }

    // タイプを取得
    public String getType() {
        return type;
    }

    // タイプを設定
    public void setType(String type) {
        this.type = type;
    }

}