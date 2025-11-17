package javaRPG.merchant;

// 必要なクラスをインポート
import javaRPG.AllyUnit.AllyUnit;       // 味方ユニットの基底クラス
import javaRPG.item.Sword;              // 剣アイテムクラス
import javaRPG.item.Staff;              // 杖アイテムクラス
import javaRPG.item.Potion;             // 回復薬アイテムクラス
import javaRPG.Interface.Sellable;      // 売買可能インターフェース
import javaRPG.Interface.IEquipSword;   // 剣装備インターフェース
import javaRPG.Interface.IEquipStaff;   // 杖装備インターフェース
import java.util.ArrayList;             // ArrayListコレクション
import java.util.List;                  // Listインターフェース
import java.util.Scanner;               // ユーザー入力用

/**
 * CommonMerchant（一般商人）クラス
 *
 * このクラスは、プレイヤーに剣と杖を販売する商人の機能を提供します。
 * ゲーム内のショップシステムを担当し、アイテムの購入、装備、
 * 所持金の管理などを行います。
 *
 * 取り扱い商品：
 *
 * 【剣】- Hero、MagicSwordsmanが装備可能
 * 1. ブロンズソード: 攻撃力+5、価格100G
 * 2. アイアンソード: 攻撃力+10、価格300G
 * 3. ミスリルソード: 攻撃力+20、価格800G
 *
 * 【杖】- Mage、MagicSwordsmanが装備可能
 * 4. 木の杖: 魔法力+8、価格150G
 * 5. 魔導杖: 魔法力+15、価格400G
 * 6. 賢者の杖: 魔法力+25、価格1000G
 *
 * 主な機能：
 * - アイテムの在庫管理
 * - アイテムリストの表示
 * - 購入処理（所持金チェック、装備可否チェック）
 * - 自動装備機能
 *
 * @author RPG開発チーム
 * @version 1.0
 */
public class CommonMerchant {

    // ========================================
    // フィールド（属性）
    // ========================================

    /**
     * 在庫リスト
     * 販売可能なアイテム（SwordとStaff）を保持するリスト
     */
    private List<Sellable> inventory;

    // ========================================
    // コンストラクタ
    // ========================================

    /**
     * CommonMerchantクラスのコンストラクタ
     *
     * 商人を作成し、在庫を初期化します。
     * コンストラクタ内でinitializeInventory()を呼び出し、
     * すべての商品を在庫に追加します。
     */
    public CommonMerchant() {
        this.inventory = new ArrayList<>();
        initializeInventory();  // 在庫を初期化
    }

    // ========================================
    // 在庫管理メソッド
    // ========================================

    /**
     * 在庫を初期化するメソッド
     *
     * 商人が販売するすべての商品（剣3種類、杖3種類、回復薬3種類）を在庫リストに追加します。
     * このメソッドはコンストラクタから自動的に呼び出されます。
     *
     * 追加される商品：
     * - 剣: ブロンズソード、アイアンソード、ミスリルソード
     * - 杖: 木の杖、魔導杖、賢者の杖
     * - 回復薬: 薬草、回復薬、ハイポーション
     */
    private void initializeInventory() {
        // 剣を追加
        inventory.add(new Sword("ブロンズソード", "初心者用の剣", 5, 100));
        inventory.add(new Sword("アイアンソード", "鉄製の剣", 10, 300));
        inventory.add(new Sword("ミスリルソード", "ミスリル製の高級な剣", 20, 800));

        // 杖を追加
        inventory.add(new Staff("木の杖", "初心者用の杖", 8, 150));
        inventory.add(new Staff("魔導杖", "魔力を増幅する杖", 15, 400));
        inventory.add(new Staff("賢者の杖", "賢者が使う強力な杖", 25, 1000));

        // 回復薬を追加
        inventory.add(new Potion("薬草", "HPを30回復する", 30, 50));
        inventory.add(new Potion("回復薬", "HPを50回復する", 50, 100));
        inventory.add(new Potion("ハイポーション", "HPを100回復する", 100, 200));
    }

    // ========================================
    // ショップメイン処理
    // ========================================

    /**
     * ショップを開くメソッド
     *
     * 商人の店を開き、プレイヤーがアイテムを購入できるようにします。
     * このメソッドはループ処理で、プレイヤーが店を出るまで繰り返されます。
     *
     * 処理の流れ：
     * 1. 所持金とアイテムリストを表示
     * 2. プレイヤーが購入するアイテムを選択
     * 3. 所持金チェック
     * 4. 装備可否チェック
     * 5. 購入・装備処理
     * 6. ループ継続 or 終了
     *
     * @param ally 購入するプレイヤーキャラクター（AllyUnit）
     * @param scanner ユーザー入力を受け取るScannerオブジェクト
     */
    public void showShop(AllyUnit ally, Scanner scanner) {
        // ショップループ：プレイヤーが「0」を選択するまで繰り返す
        while (true) {
            // ========================================
            // ショップヘッダーと所持金表示
            // ========================================
            System.out.println("\n=== 商人の店 ===");
            System.out.println("所持金: " + ally.getMoney() + " ゴールド");

            // ========================================
            // 商品リスト表示
            // ========================================
            System.out.println("\n商品一覧:");
            for (int i = 0; i < inventory.size(); i++) {
                Sellable item = inventory.get(i);
                // アイテム番号、名前、価格を表示
                System.out.println((i + 1) + ". " + item.getName() + " - " + item.getPrice() + "G");
            }

            // 店を出るオプションを表示
            System.out.println("0. 店を出る");
            System.out.print("\n購入する商品を選択してください: ");

            // ========================================
            // プレイヤーの選択を取得
            // ========================================
            int choice = scanner.nextInt();
            scanner.nextLine();  // 改行文字を消費

            // 「0」が選択された場合は店を出る
            if (choice == 0) {
                System.out.println("またのご来店をお待ちしております！");
                break;  // ループを終了
            }

            // ========================================
            // 入力値チェック
            // ========================================
            // 無効な選択肢の場合はエラーメッセージを表示してループを継続
            if (choice < 1 || choice > inventory.size()) {
                System.out.println("無効な選択です。");
                continue;  // ループの先頭に戻る
            }

            // ========================================
            // 選択されたアイテムを取得
            // ========================================
            Sellable selectedItem = inventory.get(choice - 1);  // インデックスは0始まり

            // ========================================
            // 所持金チェック
            // ========================================
            if (ally.getMoney() < selectedItem.getPrice()) {
                System.out.println("お金が足りません！");
                continue;  // ループの先頭に戻る
            }

            // ========================================
            // 装備可否チェック
            // ========================================
            // 剣を選択した場合
            if (selectedItem instanceof Sword && !(ally instanceof IEquipSword)) {
                System.out.println(ally.getName() + " は剣を装備できません！");
                continue;  // ループの先頭に戻る
            }

            // 杖を選択した場合
            if (selectedItem instanceof Staff && !(ally instanceof IEquipStaff)) {
                System.out.println(ally.getName() + " は杖を装備できません！");
                continue;  // ループの先頭に戻る
            }

            // ========================================
            // 購入処理
            // ========================================
            // 所持金から価格を減算
            ally.spendMoney(selectedItem.getPrice());
            System.out.println(selectedItem.getName() + " を購入しました！");

            // ========================================
            // アイテムの種類に応じた処理
            // ========================================
            if (selectedItem instanceof Potion) {
                // 回復薬の場合：即座にHPを回復
                Potion potion = (Potion) selectedItem;
                ally.heal(potion.getHealAmount());
                System.out.println(ally.getName() + "のHPが回復した！");
            } else if (selectedItem instanceof Sword && ally instanceof IEquipSword) {
                // 剣の場合：自動装備
                ((IEquipSword) ally).equipSword((Sword) selectedItem);
            } else if (selectedItem instanceof Staff && ally instanceof IEquipStaff) {
                // 杖の場合：自動装備
                ((IEquipStaff) ally).equipStaff((Staff) selectedItem);
            }

            // ループを継続して次の購入を待つ
        } // while ループ終了
    } // showShop メソッド終了

} // CommonMerchant クラス終了
