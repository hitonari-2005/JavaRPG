package javaRPG;

import javaRPG.AllyUnit.Hero;
import javaRPG.AllyUnit.Mage;
import javaRPG.AllyUnit.MagicSwordsman;
import javaRPG.AllyUnit.AllyUnit;
import javaRPG.battle.BattleSystem;
import javaRPG.merchant.CommonMerchant;
import java.util.Scanner;

/**
 * RPGゲームのメインクラス
 * ゲームループを管理する
 *
 * 流れ：
 * 1. キャラクター選択
 * 2. 冒険→戦闘→報酬→継続確認→ショップの循環
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // ゲームタイトル
        System.out.println("==========================================");
        System.out.println("     RPG アドベンチャー");
        System.out.println("==========================================\n");

        // キャラクター選択
        System.out.println("キャラクターを選択してください:");
        System.out.println("1. 勇者（Hero） - 剣を扱う戦士");
        System.out.println("2. 魔法使い（Mage） - 杖と魔法を扱う魔術師");
        System.out.println("3. 魔法剣士（MagicSwordsman） - 剣と杖、魔法を扱う万能戦士");
        System.out.print("選択 (1-3): ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        // 選択したキャラクターを作成
        AllyUnit player;
        switch (choice) {
            case 2:
                player = new Mage();
                break;
            case 3:
                player = new MagicSwordsman();
                break;
            default:
                player = new Hero();
                break;
        }

        System.out.println("\n" + player.getName() + "を選択しました！");
        player.displayStatus();

        // バトルシステムと商人を初期化
        BattleSystem battleSystem = new BattleSystem();
        battleSystem.addAlly(player);
        CommonMerchant merchant = new CommonMerchant();

        // ゲームループ
        boolean adventureContinue = true;
        while (adventureContinue) {
            System.out.println("\n==========================================");
            System.out.println("何をしますか？");
            System.out.println("==========================================");
            System.out.println("1. アイテムを購入");
            System.out.println("2. 冒険する（戦闘）");
            System.out.println("3. ステータス確認");
            System.out.println("0. ゲーム終了");
            System.out.print("選択: ");

            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1:
                    // 商人の店
                    merchant.showShop(player, scanner);
                    break;

                case 2:
                    // 冒険（戦闘）
                    System.out.println("\n冒険に出発します...");
                    boolean battleResult = battleSystem.startBattle(scanner);

                    if (!battleResult) {
                        System.out.println("\nゲームオーバー...");
                        System.out.println("戦闘回数: " + battleSystem.getBattleCount());
                        adventureContinue = false;
                        continue;
                    }

                    // 戦闘後、継続確認
                    System.out.println("\n冒険を続けますか？ (y/n)");
                    System.out.print("選択: ");
                    String continueChoice = scanner.nextLine();

                    if (!continueChoice.equalsIgnoreCase("y")) {
                        System.out.println("\n今日はここまで！");
                        System.out.println("戦闘回数: " + battleSystem.getBattleCount());
                        adventureContinue = false;
                    }
                    break;

                case 3:
                    // ステータス確認
                    player.displayStatus();
                    break;

                case 0:
                    // ゲーム終了
                    System.out.println("\nゲームを終了します。");
                    System.out.println("戦闘回数: " + battleSystem.getBattleCount());
                    adventureContinue = false;
                    break;

                default:
                    System.out.println("無効な選択です。");
                    break;
            }
        }

        System.out.println("\n==========================================");
        System.out.println("     プレイありがとうございました！");
        System.out.println("==========================================");

        scanner.close();
    }
}
