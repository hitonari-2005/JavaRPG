import rpg.ally.Hero;
import rpg.ally.Mage;
import rpg.ally.MagicSwordsman;
import rpg.base.AllyUnit;
import rpg.battle.BattleSystem;
import rpg.merchant.CommonMerchant;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=============================================================");
        System.out.println("           RPGゲームへようこそ！");
        System.out.println("=============================================================\n");

        // キャラクター選択
        System.out.println("キャラクタークラスを選択してください：");
        System.out.println("1. Hero（勇者） - 剣を扱う主人公");
        System.out.println("   HP:100 攻撃:20 素早さ:10 防御:10");
        System.out.println("2. Mage（魔法使い） - 杖と魔法を扱うキャラクター");
        System.out.println("   HP:70 攻撃:15 素早さ:8 防御:5 MP:100");
        System.out.println("3. MagicSwordsman（魔法剣士） - 剣と杖、両方を扱う");
        System.out.println("   HP:85 攻撃:18 素早さ:12 防御:8 MP:80");
        System.out.print("\n選択（1-3）: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        System.out.print("キャラクターの名前を入力してください: ");
        String name = scanner.nextLine();

        AllyUnit player = createCharacter(choice, name);

        if (player == null) {
            System.out.println("無効な選択です。");
            scanner.close();
            return;
        }

        System.out.println("\n=============================================================");
        System.out.println("  " + player.getName() + " の冒険が始まった！");
        System.out.println("=============================================================\n");

        // 味方パーティー（現在は1人）
        List<AllyUnit> party = new ArrayList<>();
        party.add(player);

        // 商人
        CommonMerchant merchant = new CommonMerchant();

        // ゲームループ
        boolean gameOver = false;
        while (!gameOver) {
            System.out.println("\n--- メインメニュー ---");
            System.out.println("1. アイテムを購入");
            System.out.println("2. 冒険する（戦闘）");
            System.out.println("3. ステータス確認");
            System.out.println("0. ゲーム終了");
            System.out.print("選択: ");

            int menuChoice = scanner.nextInt();
            scanner.nextLine();

            switch (menuChoice) {
                case 1:
                    // アイテム購入
                    merchant.showShop(player, scanner);
                    break;

                case 2:
                    // 戦闘
                    BattleSystem battleSystem = new BattleSystem(party, scanner);
                    battleSystem.generateEnemies();
                    boolean victory = battleSystem.startBattle();

                    if (!victory) {
                        System.out.println("\n" + player.getName() + " は力尽きた...");
                        System.out.println("GAME OVER");
                        gameOver = true;
                        break;
                    }

                    // 冒険を続けるか確認
                    System.out.print("\n冒険を続けますか？ (y/n): ");
                    String continueChoice = scanner.nextLine();

                    if (!continueChoice.equalsIgnoreCase("y")) {
                        System.out.println("\n冒険を終了します。お疲れ様でした！");
                        gameOver = true;
                    }
                    break;

                case 3:
                    // ステータス確認
                    displayStatus(player);
                    break;

                case 0:
                    System.out.println("\nゲームを終了します。");
                    gameOver = true;
                    break;

                default:
                    System.out.println("無効な選択です。");
                    break;
            }
        }

        scanner.close();
    }

    private static AllyUnit createCharacter(int choice, String name) {
        switch (choice) {
            case 1:
                return new Hero(name);
            case 2:
                return new Mage(name);
            case 3:
                return new MagicSwordsman(name);
            default:
                return null;
        }
    }

    private static void displayStatus(AllyUnit ally) {
        System.out.println("\n=== ステータス ===");
        System.out.println("名前: " + ally.getName());
        System.out.println("レベル: " + ally.getLevel());
        System.out.println("HP: " + ally.getHp() + "/" + ally.getMaxHp());

        if (ally instanceof Mage) {
            Mage mage = (Mage) ally;
            System.out.println("MP: " + mage.getMp() + "/" + mage.getMaxMp());
        } else if (ally instanceof MagicSwordsman) {
            MagicSwordsman ms = (MagicSwordsman) ally;
            System.out.println("MP: " + ms.getMp() + "/" + ms.getMaxMp());
        }

        System.out.println("攻撃力: " + ally.getAttackPower());
        System.out.println("防御力: " + ally.getDefense());
        System.out.println("素早さ: " + ally.getSpeed());
        System.out.println("経験値: " + ally.getExp());
        System.out.println("所持金: " + ally.getMoney() + " ゴールド");
    }
}
