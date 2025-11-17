package javaRPG.AllyUnit;

// 必要なクラスをインポート
import javaRPG.BattleUnit.BattleUnit;  // 戦闘ユニットの基底クラス
import javaRPG.Interface.IEquipSword;  // 剣装備インターフェース
import javaRPG.item.Sword;             // 剣アイテムクラス

/**
 * 勇者クラス
 *
 * このクラスはプレイヤーが選択できる職業の一つである「勇者」を表します。
 * AllyUnitを継承し、IEquipSwordインターフェースを実装することで、
 * 剣を装備して強力な物理攻撃を行うことができます。
 *
 * 主な特徴：
 * - 高いHP（120）と攻撃力（25）を持つ物理攻撃特化型
 * - 剣を装備することで攻撃力をさらに強化できる
 * - 剣攻撃は通常攻撃の1.5倍のダメージを与える
 * - 必殺技「勇者の剣」で2倍のダメージを与えられる
 *
 * 初期ステータス：
 * - 名前: 勇者
 * - 攻撃力: 25
 * - HP: 120
 * - 素早さ: 18
 *
 * 実装インターフェース：
 * - IEquipSword：剣の装備と剣攻撃機能を提供
 *
 * 継承関係：
 * BattleUnit → AllyUnit → Hero（このクラス）
 *
 * @author RPG開発チーム
 * @version 1.0
 */
public class Hero extends AllyUnit implements IEquipSword {

    // ========================================
    // フィールド（属性）- Hero固有
    // ========================================

    /** 装備している剣 - nullの場合は剣未装備 */
    private Sword sword;

    // ========================================
    // コンストラクタ
    // ========================================

    /**
     * Heroクラスのコンストラクタ（全パラメータ指定）
     *
     * 指定されたパラメータで勇者を作成します。
     * ただし、typeは常に"Hero"で固定され、ステータスも勇者専用の値が使用されます。
     *
     * @param name キャラクター名（通常は「勇者」）
     * @param id キャラクターの識別番号
     * @param type キャラクタータイプ（"Hero"で上書きされる）
     * @param power 攻撃力（25で上書きされる）
     * @param hp HP（120で上書きされる）
     * @param speed 素早さ（18で上書きされる）
     */
    public Hero(String name, int id, String type, int power, int hp, int speed) {
        // 親クラス（AllyUnit）のコンストラクタを呼び出す
        // 勇者の初期ステータス: 攻撃力25, HP120, 素早さ18
        super(name, id, "Hero", 25, 120, 18);

        // 初期状態では剣未装備
        this.sword = null;
    }

    /**
     * Heroクラスのデフォルトコンストラクタ
     *
     * パラメータなしで呼び出された場合、デフォルトの勇者を作成します。
     * 名前は「勇者」、IDは1、ステータスは標準値で初期化されます。
     */
    public Hero() {
        // デフォルトの勇者を作成
        super("勇者", 1, "Hero", 25, 120, 18);
        this.sword = null;
    }

    // ========================================
    // IEquipSwordインターフェースの実装
    // ========================================

    /**
     * 剣を装備するメソッド
     *
     * ショップで購入した剣を装備します。装備すると攻撃力が上昇します。
     * 剣攻撃や必殺技の威力も剣の攻撃力ボーナスの影響を受けます。
     *
     * @param sword 装備する剣（Swordオブジェクト）
     */
    @Override
    public void equipSword(Sword sword) {
        // 剣を装備
        this.sword = sword;

        // 装備完了メッセージを表示
        System.out.println(getName() + "は" + sword.getName() + "を装備した！");
        System.out.println("攻撃力が" + sword.getAttackBonus() + "アップ！");
    }

    /**
     * 剣による攻撃メソッド
     *
     * 装備している剣を使って強力な攻撃を行います。
     * ダメージは（基本攻撃力 + 剣の攻撃力ボーナス）× 1.5倍となります。
     *
     * ダメージ計算：
     * 1. 基本攻撃力を取得
     * 2. 剣が装備されていれば剣の攻撃力ボーナスを加算
     * 3. 合計値を1.5倍にする
     *
     * @param target 攻撃対象のBattleUnit
     */
    @Override
    public void swordAttack(BattleUnit target) {
        // 攻撃対象が有効かチェック
        if (target == null || !target.isAlive()) {
            System.out.println("攻撃対象が無効です！");
            return;
        }

        // 剣攻撃のメッセージを表示
        System.out.println(getName() + "の剣攻撃！");

        // ダメージを計算：基本攻撃力 + 剣の攻撃力ボーナス
        int damage = getPower();
        if (sword != null) {
            damage += sword.getAttackBonus();
        }

        // 剣攻撃は1.5倍のダメージ
        damage = (int)(damage * 1.5);

        // 対象にダメージを与える
        target.takeDamage(damage);
    }

    // ========================================
    // Hero専用の特殊技
    // ========================================

    /**
     * 必殺技「勇者の剣」
     *
     * 勇者の最強攻撃技です。通常の剣攻撃よりもさらに強力で、
     * （基本攻撃力 + 剣の攻撃力ボーナス）× 2倍のダメージを与えます。
     *
     * ダメージ計算：
     * 1. 基本攻撃力を取得
     * 2. 剣が装備されていれば剣の攻撃力ボーナスを加算
     * 3. 合計値を2倍にする
     *
     * 使用タイミング：
     * - ボス戦で大ダメージを与えたい時
     * - 強敵を早く倒したい時
     *
     * @param target 攻撃対象のBattleUnit
     */
    public void braveSlash(BattleUnit target) {
        // 攻撃対象が有効かチェック
        if (target == null || !target.isAlive()) {
            System.out.println("攻撃対象が無効です！");
            return;
        }

        // 必殺技のメッセージを表示
        System.out.println("★ " + getName() + "の必殺技「勇者の剣」！ ★");

        // ダメージを計算：基本攻撃力 + 剣のボーナスの2倍
        int damage = getPower();
        if (sword != null) {
            damage += sword.getAttackBonus();
        }
        damage = damage * 2;  // 2倍のダメージ

        // 対象にダメージを与える
        target.takeDamage(damage);
    }

    // ========================================
    // ゲッターメソッド
    // ========================================

    /**
     * 装備中の剣を取得
     *
     * 現在装備している剣のオブジェクトを返します。
     * 剣を装備していない場合はnullを返します。
     *
     * @return 装備中のSwordオブジェクト（未装備の場合はnull）
     */
    public Sword getSword() {
        return sword;
    }

    // ========================================
    // ステータス表示
    // ========================================

    /**
     * ステータス表示メソッド（オーバーライド）
     *
     * 勇者専用のフォーマットでステータスを表示します。
     * 親クラスのステータスに加えて、装備している剣の情報も表示されます。
     */
    @Override
    public void displayStatus() {
        System.out.println("========== HERO ==========");

        // 親クラスのステータス表示（HP、攻撃力、経験値など）
        super.displayStatus();

        // 剣を装備している場合は装備情報を表示
        if (sword != null) {
            System.out.println("装備: " + sword.getName() + " (攻撃+" + sword.getAttackBonus() + ")");
            System.out.println("====================");
        }
    }

} // Hero クラス終了
