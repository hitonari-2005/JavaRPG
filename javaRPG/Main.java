package javaRPG;

// 必要なクラスをインポート
import javaRPG.AllyUnit.Hero;              // 勇者クラス
import javaRPG.AllyUnit.Mage;              // 魔法使いクラス
import javaRPG.AllyUnit.MagicSwordsman;    // 魔法剣士クラス
import javaRPG.AllyUnit.AllyUnit;          // 味方ユニットの基底クラス
import javaRPG.battle.BattleSystem;        // バトルシステム
import javaRPG.merchant.CommonMerchant;    // 商人クラス
import java.util.Scanner;                  // ユーザー入力用

/**
 * RPGゲームのメインクラス
 *
 * このクラスはゲーム全体の流れを管理します。
 *
 * ゲームの流れ：
 * 1. ゲーム開始時にキャラクター選択（勇者/魔法使い/魔法剣士）
 * 2. メインループ：
 *    - アイテム購入（ショップ）
 *    - 冒険（戦闘）
 *    - ステータス確認
 *    - ゲーム終了
 * 3. 戦闘後は経験値とお金を獲得し、レベルアップ可能
 * 4. 継続するか選択して、冒険を続けるかゲームを終了する
 *
 * @author RPG開発チーム
 * @version 1.0
 */
public class Main {
    /**
     * プログラムのエントリーポイント
     * ゲームの初期化と実行を行います
     *
     * @param args コマンドライン引数（使用しません）
     */
    public static void main(String[] args) {
        // ユーザー入力を受け取るためのScannerオブジェクトを作成
        Scanner scanner = new Scanner(System.in);

        // ========================================
        // ゲームタイトル表示
        // ========================================
        System.out.println("==========================================");
        System.out.println("     RPG アドベンチャー");
        System.out.println("==========================================\n");

        // ========================================
        // キャラクター選択フェーズ
        // ========================================
        // プレイヤーに3つのキャラクタークラスから選択させる
        System.out.println("キャラクターを選択してください:");
        System.out.println("1. 勇者（Hero） - 剣を扱う戦士");
        System.out.println("2. 魔法使い（Mage） - 杖と魔法を扱う魔術師");
        System.out.println("3. 魔法剣士（MagicSwordsman） - 剣と杖、魔法を扱う万能戦士");
        System.out.print("選択 (1-3): ");

        // ユーザーの選択を取得
        int choice = scanner.nextInt();
        scanner.nextLine();  // 改行文字を消費

        // 選択に応じてキャラクターを作成
        // player変数には、選択されたキャラクターのインスタンスが格納される
        AllyUnit player;
        switch (choice) {
            case 2:
                // 魔法使いを選択
                player = new Mage();
                break;
            case 3:
                // 魔法剣士を選択
                player = new MagicSwordsman();
                break;
            default:
                // デフォルトまたは1を選択した場合は勇者
                player = new Hero();
                break;
        }

        // 選択したキャラクターの確認と初期ステータス表示
        System.out.println("\n" + player.getName() + "を選択しました！");
        player.displayStatus();

        // ========================================
        // ゲームシステムの初期化
        // ========================================
        // バトルシステムを作成し、プレイヤーを登録
        BattleSystem battleSystem = new BattleSystem();
        battleSystem.addAlly(player);  // 選択したキャラクターをパーティに追加

        // 商人（ショップ）を作成
        CommonMerchant merchant = new CommonMerchant();

        // ========================================
        // メインゲームループ
        // ========================================
        // このループがゲームの中心となり、プレイヤーの選択に応じて
        // 様々な行動（ショップ、戦闘、ステータス確認など）を実行する
        boolean adventureContinue = true;  // ゲーム継続フラグ

        while (adventureContinue) {
            // メインメニュー表示
            System.out.println("\n==========================================");
            System.out.println("何をしますか？");
            System.out.println("==========================================");
            System.out.println("1. アイテムを購入");
            System.out.println("2. 冒険する（戦闘）");
            System.out.println("3. ステータス確認");
            System.out.println("0. ゲーム終了");
            System.out.print("選択: ");

            // プレイヤーの行動選択を取得
            int action = scanner.nextInt();
            scanner.nextLine();  // 改行文字を消費

            // プレイヤーの選択に応じて処理を分岐
            switch (action) {
                case 1:
                    // ========================================
                    // オプション1: アイテムショップ
                    // ========================================
                    // 商人の店を開き、剣や杖を購入できる
                    merchant.showShop(player, scanner);
                    break;

                case 2:
                    // ========================================
                    // オプション2: 冒険（戦闘）
                    // ========================================
                    System.out.println("\n冒険に出発します...");

                    // 戦闘を開始し、結果を取得
                    // true = 勝利、false = 敗北（全滅）
                    boolean battleResult = battleSystem.startBattle(scanner);

                    // 戦闘に敗北した場合はゲームオーバー
                    if (!battleResult) {
                        System.out.println("\nゲームオーバー...");
                        System.out.println("戦闘回数: " + battleSystem.getBattleCount());
                        adventureContinue = false;  // ゲームループを終了
                        continue;  // 次の処理をスキップ
                    }

                    // 戦闘に勝利した場合、冒険を続けるか確認
                    System.out.println("\n冒険を続けますか？ (y/n)");
                    System.out.print("選択: ");
                    String continueChoice = scanner.nextLine();

                    // 'y' または 'Y' 以外が入力された場合はゲーム終了
                    if (!continueChoice.equalsIgnoreCase("y")) {
                        System.out.println("\n今日はここまで！");
                        System.out.println("戦闘回数: " + battleSystem.getBattleCount());
                        adventureContinue = false;  // ゲームループを終了
                    }
                    break;

                case 3:
                    // ========================================
                    // オプション3: ステータス確認
                    // ========================================
                    // 現在のHP、攻撃力、経験値、所持金などを表示
                    player.displayStatus();
                    break;

                case 0:
                    // ========================================
                    // オプション0: ゲーム終了
                    // ========================================
                    System.out.println("\nゲームを終了します。");
                    System.out.println("戦闘回数: " + battleSystem.getBattleCount());
                    adventureContinue = false;  // ゲームループを終了
                    break;

                default:
                    // 無効な選択肢が入力された場合
                    System.out.println("無効な選択です。");
                    break;
            }
        } // メインゲームループ終了

        // ========================================
        // ゲーム終了メッセージ
        // ========================================
        System.out.println("\n==========================================");
        System.out.println("     プレイありがとうございました！");
        System.out.println("==========================================");

        // Scannerリソースを解放
        scanner.close();
    } // main メソッド終了
} // Main クラス終了
