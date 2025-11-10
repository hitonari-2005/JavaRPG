package rpg.battle;

import rpg.base.AllyUnit;
import rpg.base.BattleUnit;
import rpg.base.EnemyUnit;
import rpg.enemy.Dragon;
import rpg.enemy.Goblin;
import rpg.enemy.Slime;
import rpg.ally.Mage;
import rpg.ally.MagicSwordsman;
import rpg.interfaces.IMagicUser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// バトルシステムクラス
public class BattleSystem {
    private List<AllyUnit> allies;
    private List<EnemyUnit> enemies;
    private Scanner scanner;
    private int battleCount = 0;

    public BattleSystem(List<AllyUnit> allies, Scanner scanner) {
        this.allies = allies;
        this.scanner = scanner;
    }

    // 敵をランダムに生成
    public void generateEnemies() {
        enemies = new ArrayList<>();
        battleCount++;

        // 4戦闘ごとにドラゴンボス戦
        if (battleCount % 4 == 0) {
            int dragonLevel = 1 + (battleCount / 4);
            enemies.add(new Dragon(dragonLevel));
            System.out.println("\n*** ボス戦！ドラゴンが現れた！***");
        } else {
            // ランダムで1〜5体の敵を生成
            Random random = new Random();
            int enemyCount = 1 + random.nextInt(5);
            int enemyLevel = 1 + (battleCount / 2);

            System.out.println("\n敵が現れた！");
            for (int i = 0; i < enemyCount; i++) {
                if (random.nextBoolean()) {
                    enemies.add(new Slime(enemyLevel));
                } else {
                    enemies.add(new Goblin(enemyLevel));
                }
            }
        }

        // 敵の情報を表示
        for (EnemyUnit enemy : enemies) {
            System.out.println("- " + enemy.getName() + " Lv." + enemy.getLevel() + " (HP:" + enemy.getHp() + ")");
        }
    }

    // バトルを実行
    public boolean startBattle() {
        System.out.println("\n=== 戦闘開始！ ===\n");

        while (true) {
            // 戦闘終了判定
            if (!isAllyAlive()) {
                System.out.println("\n=== 全滅した... ===");
                return false;
            }

            if (!isEnemyAlive()) {
                System.out.println("\n=== 勝利！ ===");
                giveRewards();
                return true;
            }

            // 行動順を決定（速さ順）
            List<BattleUnit> actionOrder = getActionOrder();

            // 各ユニットが行動
            for (BattleUnit unit : actionOrder) {
                if (!unit.isAlive()) continue;

                if (!isAllyAlive() || !isEnemyAlive()) {
                    break;
                }

                if (unit instanceof AllyUnit) {
                    allyTurn((AllyUnit) unit);
                } else if (unit instanceof EnemyUnit) {
                    enemyTurn((EnemyUnit) unit);
                }

                System.out.println();
            }

            displayStatus();
        }
    }

    // 味方のターン
    private void allyTurn(AllyUnit ally) {
        System.out.println("--- " + ally.getName() + " のターン ---");
        System.out.println("1. 攻撃");
        System.out.println("2. 回復 (HP+30)");

        if (ally instanceof IMagicUser) {
            System.out.println("3. 魔法攻撃");
        }

        System.out.print("行動を選択: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                // 攻撃対象を選択
                EnemyUnit target = selectEnemyTarget();
                if (target != null) {
                    ally.attack(target);
                }
                break;

            case 2:
                // 回復
                ally.heal(30);
                break;

            case 3:
                if (ally instanceof IMagicUser) {
                    // 魔法攻撃（単体）
                    EnemyUnit magicTarget = selectEnemyTarget();
                    if (magicTarget != null) {
                        ((IMagicUser) ally).useMagic("ファイア", List.of(magicTarget));
                    }
                } else {
                    System.out.println("無効な選択です。");
                }
                break;

            default:
                System.out.println("無効な選択です。");
                break;
        }
    }

    // 敵のターン
    private void enemyTurn(EnemyUnit enemy) {
        System.out.println("--- " + enemy.getName() + " のターン ---");

        // ランダムに味方を選んで攻撃
        List<AllyUnit> aliveAllies = new ArrayList<>();
        for (AllyUnit ally : allies) {
            if (ally.isAlive()) {
                aliveAllies.add(ally);
            }
        }

        if (!aliveAllies.isEmpty()) {
            Random random = new Random();
            AllyUnit target = aliveAllies.get(random.nextInt(aliveAllies.size()));
            enemy.attack(target);
        }
    }

    // 敵を選択
    private EnemyUnit selectEnemyTarget() {
        List<EnemyUnit> aliveEnemies = new ArrayList<>();
        for (EnemyUnit enemy : enemies) {
            if (enemy.isAlive()) {
                aliveEnemies.add(enemy);
            }
        }

        if (aliveEnemies.isEmpty()) {
            return null;
        }

        System.out.println("\n攻撃対象を選択:");
        for (int i = 0; i < aliveEnemies.size(); i++) {
            EnemyUnit enemy = aliveEnemies.get(i);
            System.out.println((i + 1) + ". " + enemy.getName() + " (HP:" + enemy.getHp() + "/" + enemy.getMaxHp() + ")");
        }

        System.out.print("選択: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > aliveEnemies.size()) {
            System.out.println("無効な選択です。最初の敵を選択します。");
            return aliveEnemies.get(0);
        }

        return aliveEnemies.get(choice - 1);
    }

    // 行動順を取得（速さ順）
    private List<BattleUnit> getActionOrder() {
        List<BattleUnit> all = new ArrayList<>();
        for (AllyUnit ally : allies) {
            if (ally.isAlive()) {
                all.add(ally);
            }
        }
        for (EnemyUnit enemy : enemies) {
            if (enemy.isAlive()) {
                all.add(enemy);
            }
        }

        all.sort(Comparator.comparingInt(BattleUnit::getSpeed).reversed());
        return all;
    }

    // 味方が生存しているか
    private boolean isAllyAlive() {
        for (AllyUnit ally : allies) {
            if (ally.isAlive()) {
                return true;
            }
        }
        return false;
    }

    // 敵が生存しているか
    private boolean isEnemyAlive() {
        for (EnemyUnit enemy : enemies) {
            if (enemy.isAlive()) {
                return true;
            }
        }
        return false;
    }

    // 報酬を与える
    private void giveRewards() {
        int totalExp = 0;
        int totalMoney = 0;

        for (EnemyUnit enemy : enemies) {
            totalExp += enemy.getExp();
            totalMoney += enemy.getMoney();
        }

        System.out.println("\n=== 報酬 ===");
        System.out.println("経験値: " + totalExp);
        System.out.println("お金: " + totalMoney + " ゴールド");

        for (AllyUnit ally : allies) {
            if (ally.isAlive()) {
                ally.gainExp(totalExp);
                ally.gainMoney(totalMoney);
            }
        }
    }

    // ステータス表示
    private void displayStatus() {
        System.out.println("\n=== 現在の状況 ===");
        System.out.println("【味方】");
        for (AllyUnit ally : allies) {
            if (ally.isAlive()) {
                String mpInfo = "";
                if (ally instanceof Mage) {
                    Mage mage = (Mage) ally;
                    mpInfo = " MP:" + mage.getMp() + "/" + mage.getMaxMp();
                } else if (ally instanceof MagicSwordsman) {
                    MagicSwordsman ms = (MagicSwordsman) ally;
                    mpInfo = " MP:" + ms.getMp() + "/" + ms.getMaxMp();
                }
                System.out.println("  " + ally.getName() + " Lv." + ally.getLevel() +
                                 " HP:" + ally.getHp() + "/" + ally.getMaxHp() + mpInfo);
            }
        }

        System.out.println("【敵】");
        for (EnemyUnit enemy : enemies) {
            if (enemy.isAlive()) {
                System.out.println("  " + enemy.getName() + " Lv." + enemy.getLevel() +
                                 " HP:" + enemy.getHp() + "/" + enemy.getMaxHp());
            }
        }
        System.out.println();
    }
}
