package javaRPG.AllyUnit;

import javaRPG.BattleUnit.BattleUnit;
import javaRPG.Interface.IEquipStaff;
import javaRPG.Interface.IMagicUser;
import javaRPG.item.Staff;
import java.util.List;

/**
 * 魔法使いクラス
 * AllyUnitを継承し、IEquipStaffとIMagicUserを実装する
 * 杖と魔法を扱うキャラクター
 */
public class Mage extends AllyUnit implements IEquipStaff, IMagicUser {

    // フィールド（属性）- Mage固有
    private Staff staff;  // 装備している杖

    // コンストラクタ（全パラメータ指定）
    public Mage(String name, int id, String type, int power, int hp, int speed) {
        // 親クラス（AllyUnit）のコンストラクタを呼び出す
        // Mageの初期ステータス: 攻撃力15, HP80, 素早さ12
        super(name, id, "Mage", 15, 80, 12);
        this.staff = null;  // 初期状態では杖未装備
    }

    // デフォルトコンストラクタ
    public Mage() {
        // デフォルトの魔法使いを作成
        super("魔法使い", 2, "Mage", 15, 80, 12);
        this.staff = null;
    }

    // IEquipStaffインターフェースの実装
    @Override
    public void equipStaff(Staff staff) {
        // 杖を装備するメソッド
        this.staff = staff;
        System.out.println(getName() + "は" + staff.getName() + "を装備した！");
        System.out.println("魔法力が" + staff.getMagicBonus() + "アップ！");
    }

    @Override
    public void staffAttack(BattleUnit target) {
        // 杖による攻撃
        if (target == null || !target.isAlive()) {
            System.out.println("攻撃対象が無効です！");
            return;
        }

        System.out.println(getName() + "の杖攻撃！");

        // 杖攻撃は基本攻撃力 + 杖の魔法力ボーナス
        int damage = getPower();
        if (staff != null) {
            damage += staff.getMagicBonus();
        }

        target.takeDamage(damage);
    }

    // IMagicUserインターフェースの実装
    @Override
    public void useMagic(String magicName, List<BattleUnit> targets) {
        if (targets == null || targets.isEmpty()) {
            System.out.println("対象が指定されていません！");
            return;
        }

        // 魔法力の計算（基本攻撃力 + 杖のボーナス）
        int magicPower = getPower();
        if (staff != null) {
            magicPower += staff.getMagicBonus();
        }

        System.out.println("★ " + getName() + "は" + magicName + "を唱えた！ ★");

        // 魔法の種類に応じた処理
        switch (magicName.toLowerCase()) {
            case "ファイア":
            case "fire":
                // 単体攻撃魔法
                if (targets.size() > 0) {
                    BattleUnit target = targets.get(0);
                    int damage = (int)(magicPower * 1.5);
                    System.out.println("灼熱の炎が" + target.getName() + "を包む！");
                    target.takeDamage(damage);
                }
                break;

            case "ブリザド":
            case "blizzard":
                // 単体攻撃魔法（氷系）
                if (targets.size() > 0) {
                    BattleUnit target = targets.get(0);
                    int damage = (int)(magicPower * 1.5);
                    System.out.println("氷の刃が" + target.getName() + "を切り裂く！");
                    target.takeDamage(damage);
                }
                break;

            case "ケアル":
            case "heal":
                // 単体回復魔法
                if (targets.size() > 0) {
                    BattleUnit target = targets.get(0);
                    int healAmount = (int)(magicPower * 1.2);
                    System.out.println("癒しの光が" + target.getName() + "を包む！");
                    target.heal(healAmount);
                }
                break;

            case "サンダガ":
            case "thundaga":
                // 全体攻撃魔法
                System.out.println("雷鳴が轟き、稲妻が敵を貫く！");
                for (BattleUnit target : targets) {
                    if (target != null && target.isAlive()) {
                        int damage = magicPower;
                        target.takeDamage(damage);
                    }
                }
                break;

            default:
                System.out.println("その魔法は使えません！");
                break;
        }
    }

    // Mage専用の特殊技
    public void meteorStrike(List<BattleUnit> targets) {
        if (targets == null || targets.isEmpty()) {
            System.out.println("対象が指定されていません！");
            return;
        }

        System.out.println("★★★ " + getName() + "の最強魔法「メテオ」！ ★★★");
        System.out.println("天から巨大な隕石が降り注ぐ！");

        int magicPower = getPower();
        if (staff != null) {
            magicPower += staff.getMagicBonus();
        }

        // 全体に大ダメージ
        for (BattleUnit target : targets) {
            if (target != null && target.isAlive()) {
                int damage = (int)(magicPower * 2.0);
                target.takeDamage(damage);
            }
        }
    }

    // 装備中の杖を取得
    public Staff getStaff() {
        return staff;
    }

    // ステータス表示メソッド（オーバーライド）
    @Override
    public void displayStatus() {
        System.out.println("========== MAGE ==========");
        super.displayStatus();
        if (staff != null) {
            System.out.println("装備: " + staff.getName() + " (魔法力+" + staff.getMagicBonus() + ")");
            System.out.println("====================");
        }
    }

}
