package javaRPG.AllyUnit;

// 必要なクラスをインポート
import javaRPG.BattleUnit.BattleUnit;       // 戦闘ユニットの基底クラス
import javaRPG.Interface.IEquipSword;       // 剣装備インターフェース
import javaRPG.Interface.IEquipStaff;       // 杖装備インターフェース
import javaRPG.Interface.IMagicUser;        // 魔法使用者インターフェース
import javaRPG.item.Sword;                  // 剣アイテムクラス
import javaRPG.item.Staff;                  // 杖アイテムクラス
import javaRPG.magic.FireBall;              // ファイアボール魔法
import javaRPG.magic.Heal;                  // ヒール魔法
import javaRPG.magic.Magic;                 // 魔法の基底クラス
import java.util.List;                      // リストインターフェース

/**
 * 魔法剣士クラス
 *
 * このクラスはプレイヤーが選択できる職業の一つである「魔法剣士」を表します。
 * AllyUnitを継承し、IEquipSword、IEquipStaff、IMagicUserの3つのインターフェースを
 * すべて実装することで、剣と杖の両方を装備し、物理攻撃と魔法の両方を使える
 * 最も万能なキャラクターです。
 *
 * 主な特徴：
 * - バランスの取れたステータス（HP100、攻撃力20、素早さ15）
 * - 剣と杖の両方を同時に装備可能
 * - 物理攻撃（剣攻撃）と魔法攻撃の両方を使い分けられる
 * - 必殺技「魔法剣」で剣と杖の力を合わせた超強力な攻撃が可能
 * - 二刀流攻撃で剣と杖の連続攻撃ができる
 *
 * 初期ステータス：
 * - 名前: 魔法剣士
 * - 攻撃力: 20
 * - HP: 100
 * - 素早さ: 15
 *
 * 実装インターフェース：
 * - IEquipSword：剣の装備と剣攻撃機能を提供
 * - IEquipStaff：杖の装備と杖攻撃機能を提供
 * - IMagicUser：魔法使用機能を提供
 *
 * 使用可能な魔法：
 * - ファイア：単体に炎属性の魔法ダメージ
 * - サンダー：単体に雷属性の魔法ダメージ（威力1.3倍）
 * - ケアル：単体のHPを回復
 *
 * 特殊技：
 * - 魔法剣：剣と杖の力を合わせた一撃（威力2.5倍）
 * - 二刀流攻撃：剣と杖の連続攻撃
 *
 * 継承関係：
 * BattleUnit → AllyUnit → MagicSwordsman（このクラス）
 *
 * @author RPG開発チーム
 * @version 1.0
 */
public class MagicSwordsman extends AllyUnit implements IEquipSword, IEquipStaff, IMagicUser {

    // ========================================
    // フィールド（属性）- MagicSwordsman固有
    // ========================================

    /** 装備している剣 - nullの場合は剣未装備 */
    private Sword sword;

    /** 装備している杖 - nullの場合は杖未装備 */
    private Staff staff;

    // ========================================
    // 使用可能な魔法リスト
    // ========================================

    /** ファイアボール魔法 - 単体に炎属性のダメージを与える */
    private Magic fireBall;

    /** ヒール魔法 - 単体のHPを回復する */
    private Magic heal;

    // ========================================
    // コンストラクタ
    // ========================================

    /**
     * MagicSwordsmanクラスのコンストラクタ（全パラメータ指定）
     *
     * 指定されたパラメータで魔法剣士を作成します。
     * ただし、typeは常に"MagicSwordsman"で固定され、
     * ステータスも魔法剣士専用の値が使用されます。
     *
     * @param name キャラクター名（通常は「魔法剣士」）
     * @param id キャラクターの識別番号
     * @param type キャラクタータイプ（"MagicSwordsman"で上書きされる）
     * @param power 攻撃力（20で上書きされる）
     * @param hp HP（100で上書きされる）
     * @param speed 素早さ（15で上書きされる）
     */
    public MagicSwordsman(String name, int id, String type, int power, int hp, int speed) {
        // 親クラス（AllyUnit）のコンストラクタを呼び出す
        // MagicSwordsmanの初期ステータス: 攻撃力20, HP100, 素早さ15
        super(name, id, "MagicSwordsman", 20, 100, 15);

        // 魔法剣士専用のMP設定
        setMaxMp(45);  // 魔法剣士の最大MPは45（勇者と魔法使いの中間）
        setMp(45);     // MPを最大値で初期化

        // 初期状態では剣・杖ともに未装備
        this.sword = null;  // 剣未装備
        this.staff = null;  // 杖未装備

        // 魔法オブジェクトを初期化
        // 魔法剣士はファイアとヒールのみ使用可能（Mageより威力は低い）
        this.fireBall = new FireBall(25);   // 炎魔法（基本威力25）
        this.heal = new Heal(35);           // 回復魔法（基本回復量35）
    }

    /**
     * MagicSwordsmanクラスのデフォルトコンストラクタ
     *
     * パラメータなしで呼び出された場合、デフォルトの魔法剣士を作成します。
     * 名前は「魔法剣士」、IDは3、ステータスは標準値で初期化されます。
     */
    public MagicSwordsman() {
        // デフォルトの魔法剣士を作成
        super("魔法剣士", 3, "MagicSwordsman", 20, 100, 15);

        // 魔法剣士専用のMP設定
        setMaxMp(45);  // 魔法剣士の最大MPは45（勇者と魔法使いの中間）
        setMp(45);     // MPを最大値で初期化

        // 初期状態では剣・杖ともに未装備
        this.sword = null;
        this.staff = null;

        // 魔法オブジェクトを初期化
        this.fireBall = new FireBall(25);
        this.heal = new Heal(35);
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
    public boolean useMagic(String magicName, List<BattleUnit> targets) {
        if (targets == null || targets.isEmpty()) {
            System.out.println("対象が指定されていません！");
            return false;
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
                // MP消費チェック（5 MP必要）
                if (!consumeMp(5)) {
                    return false;  // MPが足りない場合は失敗を返す
                }
                System.out.println("5MPを消費した！");
                // ファイアボール魔法を使用
                fireBall.cast(this, targets);
                break;

            case "サンダー":
            case "thunder":
                // MP消費チェック（7 MP必要）
                if (!consumeMp(7)) {
                    return false;  // MPが足りない場合は失敗を返す
                }
                System.out.println("7MPを消費した！");
                // サンダー魔法（従来の実装を維持）
                System.out.println(getName() + "はサンダーを唱えた！");
                if (targets.size() > 0) {
                    BattleUnit target = targets.get(0);
                    int damage = (int)(magicPower * 1.3);
                    System.out.println("雷光が" + target.getName() + "を貫く！");
                    target.takeDamage(damage);
                }
                break;

            case "ケアル":
            case "heal":
            case "ヒール":
                // MP消費チェック（4 MP必要）
                if (!consumeMp(4)) {
                    return false;  // MPが足りない場合は失敗を返す
                }
                System.out.println("4MPを消費した！");
                // ヒール魔法を使用
                heal.cast(this, targets);
                break;

            default:
                System.out.println("その魔法は使えません！");
                return false;
        }
        return true;  // 成功
    }

    // MagicSwordsman専用の特殊技
    public boolean magicBlade(BattleUnit target) {
        if (target == null || !target.isAlive()) {
            System.out.println("攻撃対象が無効です！");
            return false;
        }

        // MP消費チェック（15 MP必要）
        if (!consumeMp(15)) {
            return false;  // MPが足りない場合は失敗を返す
        }

        System.out.println("★★★ " + getName() + "の奥義「魔法剣」！ ★★★");
        System.out.println("15MPを消費した！");
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
        return true;  // 成功
    }

    // 二刀流攻撃（剣と杖を使った連続攻撃）
    public boolean dualWieldAttack(BattleUnit target) {
        if (target == null || !target.isAlive()) {
            System.out.println("攻撃対象が無効です！");
            return false;
        }

        if (sword == null || staff == null) {
            System.out.println("剣と杖の両方を装備していないと使えません！");
            return false;
        }

        // MP消費チェック（12 MP必要）
        if (!consumeMp(12)) {
            return false;  // MPが足りない場合は失敗を返す
        }

        System.out.println("★ " + getName() + "の連続攻撃！ ★");
        System.out.println("12MPを消費した！");

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
        return true;  // 成功
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
