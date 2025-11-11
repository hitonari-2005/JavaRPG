package javaRPG.AllyUnit;

import javaRPG.BattleUnit.BattleUnit;
import javaRPG.Interface.IEquipSword;
import javaRPG.Interface.IEquipStaff;
import javaRPG.Interface.IMagicUser;
import javaRPG.item.Sword;
import javaRPG.item.Staff;
import java.util.List;

/**
 * 魔法剣士クラス
 * AllyUnitを継承し、IEquipSword、IEquipStaff、IMagicUserを実装する
 * 剣と杖の両方を扱い、魔法も使える万能キャラクター
 */
public class MagicSwordsman extends AllyUnit implements IEquipSword, IEquipStaff, IMagicUser {

    // フィールド（属性）- MagicSwordsman固有
    private Sword sword;  // 装備している剣
    private Staff staff;  // 装備している杖

    // コンストラクタ（全パラメータ指定）
    public MagicSwordsman(String name, int id, String type, int power, int hp, int speed) {
        // 親クラス（AllyUnit）のコンストラクタを呼び出す
        // MagicSwordsmanの初期ステータス: 攻撃力20, HP100, 素早さ15
        super(name, id, "MagicSwordsman", 20, 100, 15);
        this.sword = null;  // 初期状態では剣未装備
        this.staff = null;  // 初期状態では杖未装備
    }

    // デフォルトコンストラクタ
    public MagicSwordsman() {
        // デフォルトの魔法剣士を作成
        super("魔法剣士", 3, "MagicSwordsman", 20, 100, 15);
        this.sword = null;
        this.staff = null;
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

        // 魔法剣士は剣攻撃時に1.2倍のダメージ
        damage = (int)(damage * 1.2);
        target.takeDamage(damage);
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
                    int damage = (int)(magicPower * 1.3);
                    System.out.println("灼熱の炎が" + target.getName() + "を包む！");
                    target.takeDamage(damage);
                }
                break;

            case "サンダー":
            case "thunder":
                // 単体攻撃魔法（雷系）
                if (targets.size() > 0) {
                    BattleUnit target = targets.get(0);
                    int damage = (int)(magicPower * 1.3);
                    System.out.println("雷光が" + target.getName() + "を貫く！");
                    target.takeDamage(damage);
                }
                break;

            case "ケアル":
            case "heal":
                // 単体回復魔法
                if (targets.size() > 0) {
                    BattleUnit target = targets.get(0);
                    int healAmount = magicPower;
                    System.out.println("癒しの光が" + target.getName() + "を包む！");
                    target.heal(healAmount);
                }
                break;

            default:
                System.out.println("その魔法は使えません！");
                break;
        }
    }

    // MagicSwordsman専用の特殊技
    public void magicBlade(BattleUnit target) {
        if (target == null || !target.isAlive()) {
            System.out.println("攻撃対象が無効です！");
            return;
        }

        System.out.println("★★★ " + getName() + "の奥義「魔法剣」！ ★★★");
        System.out.println("剣に魔力を込めた一撃！");

        // 剣の攻撃力 + 杖の魔法力を合わせた超威力攻撃
        int totalPower = getPower();
        if (sword != null) {
            totalPower += sword.getAttackBonus();
        }
        if (staff != null) {
            totalPower += staff.getMagicBonus();
        }

        int damage = (int)(totalPower * 2.5);
        target.takeDamage(damage);
    }

    // 二刀流攻撃（剣と杖を使った連続攻撃）
    public void dualWieldAttack(BattleUnit target) {
        if (target == null || !target.isAlive()) {
            System.out.println("攻撃対象が無効です！");
            return;
        }

        if (sword == null || staff == null) {
            System.out.println("剣と杖の両方を装備していないと使えません！");
            return;
        }

        System.out.println("★ " + getName() + "の連続攻撃！ ★");

        // 剣攻撃
        System.out.println("1撃目: 剣による斬撃！");
        int swordDamage = getPower() + sword.getAttackBonus();
        target.takeDamage(swordDamage);

        if (target.isAlive()) {
            // 杖攻撃
            System.out.println("2撃目: 杖による魔法打撃！");
            int staffDamage = getPower() + staff.getMagicBonus();
            target.takeDamage(staffDamage);
        }
    }

    // 装備中の剣を取得
    public Sword getSword() {
        return sword;
    }

    // 装備中の杖を取得
    public Staff getStaff() {
        return staff;
    }

    // ステータス表示メソッド（オーバーライド）
    @Override
    public void displayStatus() {
        System.out.println("===== MAGIC SWORDSMAN =====");
        super.displayStatus();
        if (sword != null) {
            System.out.println("装備(剣): " + sword.getName() + " (攻撃+" + sword.getAttackBonus() + ")");
        }
        if (staff != null) {
            System.out.println("装備(杖): " + staff.getName() + " (魔法+" + staff.getMagicBonus() + ")");
        }
        System.out.println("====================");
    }

}
