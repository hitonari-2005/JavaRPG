package javaRPG.AllyUnit;

import javaRPG.BattleUnit.BattleUnit;
import javaRPG.Interface.IEquipSword;
import javaRPG.item.Sword;

// 勇者クラス
// AllyUnitを継承し、IEquipSwordを実装する
// 剣を装備して戦う主人公キャラクター
public class Hero extends AllyUnit implements IEquipSword {

    // フィールド（属性）- Hero固有
    private Sword sword;  // 装備している剣

    // コンストラクタ（全パラメータ指定）
    public Hero(String name, int id, String type, int power, int hp, int speed) {
        // 親クラス（AllyUnit）のコンストラクタを呼び出す
        // Heroの初期ステータス: 攻撃力25, HP120, 素早さ18
        super(name, id, "Hero", 25, 120, 18);
        this.sword = null;  // 初期状態では剣未装備
    }

    // デフォルトコンストラクタ
    public Hero() {
        // デフォルトの勇者を作成
        super("勇者", 1, "Hero", 25, 120, 18);
        this.sword = null;
    }

    // IEquipSwordインターフェースの実装
    @Override
    public void equipSword(Sword sword) {
        // 剣を装備するメソッド
        this.sword = sword;
        System.out.println(getName() + "は" + sword.getName() + "を装備した！");
        System.out.println("攻撃力が" + sword.getAttackBonus() + "アップ！");
    }

    @Override
    public void swordAttack(BattleUnit target) {
        // 剣による攻撃
        if (target == null || !target.isAlive()) {
            System.out.println("攻撃対象が無効です！");
            return;
        }

        System.out.println(getName() + "の剣攻撃！");

        // 剣攻撃は基本攻撃力 + 剣の攻撃力ボーナス
        int damage = getPower();
        if (sword != null) {
            damage += sword.getAttackBonus();
        }

        // 剣攻撃は1.5倍のダメージ
        damage = (int)(damage * 1.5);
        target.takeDamage(damage);
    }

    // Hero専用の特殊技
    public void braveSlash(BattleUnit target) {
        if (target == null || !target.isAlive()) {
            System.out.println("攻撃対象が無効です！");
            return;
        }

        System.out.println("★ " + getName() + "の必殺技「勇者の剣」！ ★");

        // 必殺技は基本攻撃力 + 剣のボーナスの2倍のダメージ
        int damage = getPower();
        if (sword != null) {
            damage += sword.getAttackBonus();
        }
        damage = damage * 2;
        target.takeDamage(damage);
    }

    // 装備中の剣を取得
    public Sword getSword() {
        return sword;
    }

    // ステータス表示メソッド（オーバーライド）
    @Override
    public void displayStatus() {
        System.out.println("========== HERO ==========");
        super.displayStatus();
        if (sword != null) {
            System.out.println("装備: " + sword.getName() + " (攻撃+" + sword.getAttackBonus() + ")");
            System.out.println("====================");
        }
    }

}
