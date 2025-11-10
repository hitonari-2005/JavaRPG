package javaRPG.AllyUnit;

// AllyUnitクラスをインポート
import javaRPG.AllyUnit.AllyUnit;
// IEquipSwordインターフェースをインポート
import javaRPG.Interface.IEquipSword;

// 勇者クラス
// AllyUnitを継承し、IEquipSwordを実装する
// 剣を装備して戦う主人公キャラクター
public class Hero extends AllyUnit implements IEquipSword {

    // フィールド（属性）- Hero固有
    // private Sword sword;  // 装備している剣（Itemクラス実装後に使用）

    // コンストラクタ（全パラメータ指定）
    public Hero(String name, int id, String type, int power, int hp, int speed) {
        // 親クラス（AllyUnit）のコンストラクタを呼び出す
        // Heroの初期ステータス: 攻撃力25, HP120, 素早さ18
        super(name, id, "Hero", 25, 120, 18);
    }

    // デフォルトコンストラクタ
    public Hero() {
        // デフォルトの勇者を作成
        super("勇者", 1, "Hero", 25, 120, 18);
    }

    // IEquipSwordインターフェースの実装
    @Override
    public void equipSword() {
        // 剣を装備するメソッド
        // Itemクラス実装後に詳細を実装
        System.out.println(getName() + "は剣を装備した！");
    }

    @Override
    public void swordAttack(Object target) {
        // 剣による攻撃
        if (!(target instanceof AllyUnit)) {
            System.out.println("攻撃対象が無効です！");
            return;
        }

        AllyUnit battleTarget = (AllyUnit) target;
        System.out.println(getName() + "の剣攻撃！");

        // 剣攻撃は通常攻撃の1.5倍のダメージ
        int damage = (int)(getPower() * 1.5);
        battleTarget.takeDamage(damage);
    }

    // Hero専用の特殊技
    public void braveSlash(Object target) {
        if (!(target instanceof AllyUnit)) {
            System.out.println("攻撃対象が無効です！");
            return;
        }

        AllyUnit battleTarget = (AllyUnit) target;
        System.out.println("★ " + getName() + "の必殺技「勇者の剣」！ ★");

        // 必殺技は攻撃力の2倍のダメージ
        int damage = getPower() * 2;
        battleTarget.takeDamage(damage);
    }

    // ステータス表示メソッド（オーバーライド）
    @Override
    public void displayStatus() {
        System.out.println("========== HERO ==========");
        super.displayStatus();
    }

}
