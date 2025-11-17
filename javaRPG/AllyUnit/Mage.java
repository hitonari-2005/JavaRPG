package javaRPG.AllyUnit;

// 必要なクラスをインポート
import javaRPG.BattleUnit.BattleUnit;       // 戦闘ユニットの基底クラス
import javaRPG.Interface.IEquipStaff;       // 杖装備インターフェース
import javaRPG.Interface.IMagicUser;        // 魔法使用者インターフェース
import javaRPG.item.Staff;                  // 杖アイテムクラス
import javaRPG.magic.FireBall;              // ファイアボール魔法
import javaRPG.magic.IceBall;               // アイスボール魔法
import javaRPG.magic.Heal;                  // ヒール魔法
import javaRPG.magic.Magic;                 // 魔法の基底クラス
import java.util.List;                      // リストインターフェース
import java.util.ArrayList;                 // ArrayListコレクション

/**
 * 魔法使いクラス
 *
 * このクラスはプレイヤーが選択できる職業の一つである「魔法使い」を表します。
 * AllyUnitを継承し、IEquipStaffとIMagicUserインターフェースを実装することで、
 * 杖を装備し、多彩な魔法を使って戦うことができます。
 *
 * 主な特徴：
 * - 低いHP（80）と攻撃力（15）だが、魔法で補える魔法特化型
 * - 杖を装備することで魔法力を強化できる
 * - 6種類の魔法を使用可能（ファイア、ブリザド、サンダー、ケアル、サンダガ、メテオ）
 * - 必殺技「メテオ」で全体に2倍のダメージを与えられる
 *
 * 初期ステータス：
 * - 名前: 魔法使い
 * - 攻撃力: 15
 * - HP: 80
 * - 素早さ: 12
 *
 * 実装インターフェース：
 * - IEquipStaff：杖の装備と杖攻撃機能を提供
 * - IMagicUser：魔法使用機能を提供
 *
 * 使用可能な魔法：
 * - ファイア：単体に炎属性の魔法ダメージ
 * - ブリザド：単体に氷属性の魔法ダメージ
 * - サンダー：単体に雷属性の魔法ダメージ（実装予定）
 * - ケアル：単体のHPを回復
 * - サンダガ：全体に雷属性の魔法ダメージ
 * - メテオ：全体に隕石を落とす強力な魔法（威力2倍）
 *
 * 継承関係：
 * BattleUnit → AllyUnit → Mage（このクラス）
 *
 * @author RPG開発チーム
 * @version 1.0
 */
public class Mage extends AllyUnit implements IEquipStaff, IMagicUser {

    // ========================================
    // フィールド（属性）- Mage固有
    // ========================================

    /** 装備している杖 - nullの場合は杖未装備 */
    private Staff staff;

    // ========================================
    // 使用可能な魔法リスト
    // ========================================

    /** ファイアボール魔法 - 単体に炎属性のダメージを与える */
    private Magic fireBall;

    /** アイスボール魔法 - 単体に氷属性のダメージを与える */
    private Magic iceBall;

    /** ヒール魔法 - 単体のHPを回復する */
    private Magic heal;

    // ========================================
    // コンストラクタ
    // ========================================

    /**
     * Mageクラスのコンストラクタ（全パラメータ指定）
     *
     * 指定されたパラメータで魔法使いを作成します。
     * ただし、typeは常に"Mage"で固定され、ステータスも魔法使い専用の値が使用されます。
     *
     * @param name キャラクター名（通常は「魔法使い」）
     * @param id キャラクターの識別番号
     * @param type キャラクタータイプ（"Mage"で上書きされる）
     * @param power 攻撃力（15で上書きされる）
     * @param hp HP（80で上書きされる）
     * @param speed 素早さ（12で上書きされる）
     */
    public Mage(String name, int id, String type, int power, int hp, int speed) {
        // 親クラス（AllyUnit）のコンストラクタを呼び出す
        // Mageの初期ステータス: 攻撃力15, HP80, 素早さ12
        super(name, id, "Mage", 15, 80, 12);

        // 初期状態では杖未装備
        this.staff = null;

        // 魔法オブジェクトを初期化
        // 各魔法には基本威力が設定されている
        this.fireBall = new FireBall(30);   // 炎魔法（基本威力30）
        this.iceBall = new IceBall(35);     // 氷魔法（基本威力35）
        this.heal = new Heal(40);           // 回復魔法（基本回復量40）
    }

    /**
     * Mageクラスのデフォルトコンストラクタ
     *
     * パラメータなしで呼び出された場合、デフォルトの魔法使いを作成します。
     * 名前は「魔法使い」、IDは2、ステータスは標準値で初期化されます。
     */
    public Mage() {
        // デフォルトの魔法使いを作成
        super("魔法使い", 2, "Mage", 15, 80, 12);

        // 初期状態では杖未装備
        this.staff = null;

        // 魔法オブジェクトを初期化
        this.fireBall = new FireBall(30);
        this.iceBall = new IceBall(35);
        this.heal = new Heal(40);
    }

    // ========================================
    // IEquipStaffインターフェースの実装
    // ========================================

    /**
     * 杖を装備するメソッド
     *
     * ショップで購入した杖を装備します。装備すると魔法力が上昇します。
     * 杖攻撃や魔法使用時の威力も杖の魔法力ボーナスの影響を受けます。
     *
     * @param staff 装備する杖（Staffオブジェクト）
     */
    @Override
    public void equipStaff(Staff staff) {
        // 杖を装備
        this.staff = staff;

        // 装備完了メッセージを表示
        System.out.println(getName() + "は" + staff.getName() + "を装備した！");
        System.out.println("魔法力が" + staff.getMagicBonus() + "アップ！");
    }

    /**
     * 杖による攻撃メソッド
     *
     * 装備している杖を使って攻撃を行います。
     * ダメージは基本攻撃力 + 杖の魔法力ボーナスとなります。
     *
     * ダメージ計算：
     * ダメージ = 基本攻撃力 + 杖の魔法力ボーナス
     *
     * @param target 攻撃対象のBattleUnit
     */
    @Override
    public void staffAttack(BattleUnit target) {
        // 攻撃対象が有効かチェック
        if (target == null || !target.isAlive()) {
            System.out.println("攻撃対象が無効です！");
            return;
        }

        // 杖攻撃のメッセージを表示
        System.out.println(getName() + "の杖攻撃！");

        // ダメージを計算：基本攻撃力 + 杖の魔法力ボーナス
        int damage = getPower();
        if (staff != null) {
            damage += staff.getMagicBonus();
        }

        // 対象にダメージを与える
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
