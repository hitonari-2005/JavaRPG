package javaRPG.AllyUnit;

import javaRPG.BattleUnit.BattleUnit;
import javaRPG.Interface.IEquipStaff;
import javaRPG.Interface.IMagicUser;
import javaRPG.item.Staff;
import javaRPG.magic.FireBall;
import javaRPG.magic.IceBall;
import javaRPG.magic.Heal;
import javaRPG.magic.Magic;
import java.util.List;
import java.util.ArrayList;

/**
 * 魔法使いクラス
 * AllyUnitを継承し、IEquipStaffとIMagicUserを実装する
 * 杖と魔法を扱うキャラクター
 */
public class Mage extends AllyUnit implements IEquipStaff, IMagicUser {

    // フィールド（属性）- Mage固有
    private Staff staff;  // 装備している杖

    // 使用可能な魔法リスト
    private Magic fireBall;    // ファイアボール魔法
    private Magic iceBall;     // アイスボール魔法
    private Magic heal;        // ヒール魔法

    // コンストラクタ（全パラメータ指定）
    public Mage(String name, int id, String type, int power, int hp, int speed) {
        // 親クラス（AllyUnit）のコンストラクタを呼び出す
        // Mageの初期ステータス: 攻撃力15, HP80, 素早さ12
        super(name, id, "Mage", 15, 80, 12);
        this.staff = null;  // 初期状態では杖未装備

        // 魔法を初期化
        this.fireBall = new FireBall(30);
        this.iceBall = new IceBall(35);
        this.heal = new Heal(40);
    }

    // デフォルトコンストラクタ
    public Mage() {
        // デフォルトの魔法使いを作成
        super("魔法使い", 2, "Mage", 15, 80, 12);
        this.staff = null;

        // 魔法を初期化
        this.fireBall = new FireBall(30);
        this.iceBall = new IceBall(35);
        this.heal = new Heal(40);
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
        // ※この計算は将来的にMagicクラス内で行うこともできます
        int magicPower = getPower();
        if (staff != null) {
            magicPower += staff.getMagicBonus();
        }

        // 魔法の種類に応じた処理
        // Magicクラスのインスタンスを使用して魔法を発動
        switch (magicName.toLowerCase()) {
            case "ファイア":
            case "fire":
            case "ファイアボール":
            case "fireball":
                // ファイアボール魔法を使用
                fireBall.cast(this, targets);
                break;

            case "ブリザド":
            case "blizzard":
            case "アイスボール":
            case "iceball":
                // アイスボール魔法を使用
                iceBall.cast(this, targets);
                break;

            case "ケアル":
            case "heal":
            case "ヒール":
                // ヒール魔法を使用
                heal.cast(this, targets);
                break;

            case "サンダガ":
            case "thundaga":
                // 全体攻撃魔法（サンダガは特殊なので従来の実装を維持）
                System.out.println(getName() + "はサンダガを唱えた！");
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
