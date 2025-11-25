package javaRPG.battle;

import javaRPG.AllyUnit.AllyUnit;
import javaRPG.AllyUnit.Hero;
import javaRPG.AllyUnit.Mage;
import javaRPG.AllyUnit.MagicSwordsman;
import javaRPG.BattleUnit.BattleUnit;
import javaRPG.EnemyUnit.EnemyUnit;
import javaRPG.EnemyUnit.Slime;
import javaRPG.EnemyUnit.Goblin;
import javaRPG.EnemyUnit.Dragon;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Comparator;
import java.util.Scanner;

/**
 * バトルシステムクラス
 * RPGの戦闘ロジックを管理する
 *
 * 機能:
 * - 敵のランダム出現（1〜5体）
 * - 素早さ順の攻撃順決定
 * - 戦闘の進行と終了判定
 * - 戦闘後の報酬配分
 * - ボス戦（ドラゴン）の管理
 */
public class BattleSystem {

    private List<AllyUnit> allies;        // 味方パーティ
    private List<EnemyUnit> enemies;      // 敵グループ
    private Random random;
    private int battleCount;              // 戦闘回数カウント
    private boolean isBossBattle;         // ボス戦フラグ

    public BattleSystem() {
        this.allies = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.random = new Random();
        this.battleCount = 0;
        this.isBossBattle = false;
    }

    /**
     * 味方パーティに追加
     */
    public void addAlly(AllyUnit ally) {
        allies.add(ally);
    }

    /**
     * ランダムエンカウント
     * 1〜5体の敵がランダムに出現
     * 約4回の戦闘後にドラゴンボス戦
     */
    public void randomEncounter() {
        enemies.clear();
        battleCount++;

        // 4回の戦闘ごとにボス戦
        if (battleCount % 4 == 0) {
            isBossBattle = true;
            System.out.println("\n★★★★★★★★★★★★★★★★★★★★★");
            System.out.println("★   強大な気配を感じる...！   ★");
            System.out.println("★★★★★★★★★★★★★★★★★★★★★\n");

            // ボス戦：ドラゴンが出現
            int bossLevel = 3 + (battleCount / 4);
            Dragon dragon = new Dragon(bossLevel);
            enemies.add(dragon);

            System.out.println("★ ボス「" + dragon.getName() + "」が現れた！ ★");
        } else {
            isBossBattle = false;
            System.out.println("\n==========================================");
            System.out.println("       モンスターが現れた！");
            System.out.println("==========================================\n");

            // 通常戦闘：敵が出現
            // 一回目の戦闘は1〜3体、それ以降は1〜5体
            int enemyCount;
            if (battleCount == 1) {
                enemyCount = random.nextInt(3) + 1;  // 1〜3体
            } else {
                enemyCount = random.nextInt(5) + 1;  // 1〜5体
            }
            int enemyLevel = 1 + (battleCount / 2);

            for (int i = 0; i < enemyCount; i++) {
                EnemyUnit enemy;
                // スライムとゴブリンをランダムに出現
                if (random.nextBoolean()) {
                    enemy = new Slime(enemyLevel);
                } else {
                    enemy = new Goblin(enemyLevel);
                }
                enemies.add(enemy);
                System.out.println("- " + enemy.getName() + " (Lv." + enemy.getLevel() + ") が現れた！");
            }
        }

        System.out.println();
    }

    /**
     * 戦闘開始
     */
    public boolean startBattle(Scanner scanner) {
        if (allies.isEmpty()) {
            System.out.println("パーティに味方がいません！");
            return false;
        }

        randomEncounter();

        // 戦闘ループ
        while (true) {
            // 全滅チェック
            if (!hasAliveAllies()) {
                System.out.println("\n==========================================");
                System.out.println("       全滅してしまった...");
                System.out.println("==========================================");
                return false;
            }

            if (!hasAliveEnemies()) {
                // 戦闘勝利
                battleVictory();
                return true;
            }

            // ターン開始
            executeTurn(scanner);
        }
    }

    /**
     * 1ターンの実行
     * 素早さ順に全員が行動
     */
    private void executeTurn(Scanner scanner) {
        // 全バトルユニットを素早さ順にソート
        List<BattleUnit> allUnits = new ArrayList<>();
        allUnits.addAll(allies);
        allUnits.addAll(enemies);

        // 素早さ（speed）の降順でソート
        allUnits.sort(Comparator.comparingInt(BattleUnit::getSpeed).reversed());

        System.out.println("\n========== ターン開始 ==========\n");

        // 各ユニットの行動
        for (BattleUnit unit : allUnits) {
            if (!unit.isAlive()) {
                continue;  // 倒れているユニットはスキップ
            }

            if (unit instanceof AllyUnit) {
                // 味方の行動（プレイヤーが選択）
                allyAction((AllyUnit) unit, scanner);
            } else if (unit instanceof EnemyUnit) {
                // 敵の行動（自動）
                enemyAction((EnemyUnit) unit);
            }

            // 戦闘終了チェック
            if (!hasAliveAllies() || !hasAliveEnemies()) {
                break;
            }
        }
    }

    /**
     * 味方の行動選択
     */
    private void allyAction(AllyUnit ally, Scanner scanner) {
        System.out.println("\n--- " + ally.getName() + "のターン ---");
        System.out.println("HP: " + ally.getHp() + "/" + ally.getMaxHp());
        System.out.println("MP: " + ally.getMp() + "/" + ally.getMaxMp());

        // キャラクタークラスに応じた行動メニューを表示
        if (ally instanceof Hero) {
            heroAction((Hero) ally, scanner);
        } else if (ally instanceof MagicSwordsman) {
            magicSwordsmanAction((MagicSwordsman) ally, scanner);
        } else if (ally instanceof Mage) {
            mageAction((Mage) ally, scanner);
        } else {
            // 通常の味方ユニット
            basicAllyAction(ally, scanner);
        }
    }

    /**
     * 勇者（Hero）の行動選択
     */
    private void heroAction(Hero hero, Scanner scanner) {
        boolean actionSuccessful = false;

        while (!actionSuccessful) {
            System.out.println("\n行動を選択:");
            System.out.println("1. 通常攻撃");
            System.out.println("2. 剣攻撃（装備している剣で強力な攻撃）");
            System.out.println("3. 勇者の剣（必殺技・大ダメージ）");
            System.out.println("4. 防御");
            System.out.print("選択: ");

            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1:
                    // 通常攻撃
                    EnemyUnit target1 = selectEnemyTarget(scanner);
                    if (target1 != null) {
                        hero.attack(target1);
                        actionSuccessful = true;
                    }
                    break;

                case 2:
                    // 剣攻撃
                    EnemyUnit target2 = selectEnemyTarget(scanner);
                    if (target2 != null) {
                        hero.swordAttack(target2);
                        actionSuccessful = true;
                    }
                    break;

                case 3:
                    // 必殺技
                    EnemyUnit target3 = selectEnemyTarget(scanner);
                    if (target3 != null) {
                        // MP不足の場合はfalseが返るので、ループが継続される
                        if (hero.braveSlash(target3)) {
                            actionSuccessful = true;
                        }
                    }
                    break;

                case 4:
                    // 防御
                    hero.defend();
                    actionSuccessful = true;
                    break;

                default:
                    System.out.println("無効な選択です。");
                    hero.attack(selectEnemyTarget(scanner));
                    actionSuccessful = true;
                    break;
            }
        }
    }

    /**
     * 魔法使い（Mage）の行動選択
     */
    private void mageAction(Mage mage, Scanner scanner) {
        boolean actionSuccessful = false;

        while (!actionSuccessful) {
            System.out.println("\n行動を選択:");
            System.out.println("1. 通常攻撃");
            System.out.println("2. 杖攻撃（装備している杖で魔法攻撃）");
            System.out.println("3. 魔法を使う");
            System.out.println("4. メテオ（必殺技・全体攻撃）");
            System.out.println("5. 防御");
            System.out.print("選択: ");

            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1:
                    // 通常攻撃
                    EnemyUnit target1 = selectEnemyTarget(scanner);
                    if (target1 != null) {
                        mage.attack(target1);
                        actionSuccessful = true;
                    }
                    break;

                case 2:
                    // 杖攻撃
                    EnemyUnit target2 = selectEnemyTarget(scanner);
                    if (target2 != null) {
                        mage.staffAttack(target2);
                        actionSuccessful = true;
                    }
                    break;

                case 3:
                    // 魔法使用
                    // useMagicMenuがMP不足で失敗した場合、falseが返る
                    if (useMagicMenu(mage, scanner)) {
                        actionSuccessful = true;
                    }
                    break;

                case 4:
                    // 必殺技メテオ（全体攻撃）
                    List<BattleUnit> allEnemies = new ArrayList<>();
                    allEnemies.addAll(getAliveEnemies());
                    // MP不足の場合はfalseが返るので、ループが継続される
                    if (mage.meteorStrike(allEnemies)) {
                        actionSuccessful = true;
                    }
                    break;

                case 5:
                    // 防御
                    mage.defend();
                    actionSuccessful = true;
                    break;

                default:
                    System.out.println("無効な選択です。");
                    mage.attack(selectEnemyTarget(scanner));
                    actionSuccessful = true;
                    break;
            }
        }
    }

    /**
     * 魔法剣士（MagicSwordsman）の行動選択
     */
    private void magicSwordsmanAction(MagicSwordsman magicSwordsman, Scanner scanner) {
        boolean actionSuccessful = false;

        while (!actionSuccessful) {
            System.out.println("\n行動を選択:");
            System.out.println("1. 通常攻撃");
            System.out.println("2. 剣攻撃");
            System.out.println("3. 杖攻撃");
            System.out.println("4. 魔法を使う");
            System.out.println("5. 魔法剣（必殺技・剣と杖の力を合わせた攻撃）");
            System.out.println("6. 二刀流攻撃（剣と杖の連続攻撃）");
            System.out.println("7. 防御");
            System.out.print("選択: ");

            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1:
                    // 通常攻撃
                    EnemyUnit target1 = selectEnemyTarget(scanner);
                    if (target1 != null) {
                        magicSwordsman.attack(target1);
                        actionSuccessful = true;
                    }
                    break;

                case 2:
                    // 剣攻撃
                    EnemyUnit target2 = selectEnemyTarget(scanner);
                    if (target2 != null) {
                        magicSwordsman.swordAttack(target2);
                        actionSuccessful = true;
                    }
                    break;

                case 3:
                    // 杖攻撃
                    EnemyUnit target3 = selectEnemyTarget(scanner);
                    if (target3 != null) {
                        magicSwordsman.staffAttack(target3);
                        actionSuccessful = true;
                    }
                    break;

                case 4:
                    // 魔法使用
                    // useMagicMenuがMP不足で失敗した場合、falseが返る
                    if (useMagicMenu(magicSwordsman, scanner)) {
                        actionSuccessful = true;
                    }
                    break;

                case 5:
                    // 魔法剣（必殺技）
                    EnemyUnit target5 = selectEnemyTarget(scanner);
                    if (target5 != null) {
                        // MP不足の場合はfalseが返るので、ループが継続される
                        if (magicSwordsman.magicBlade(target5)) {
                            actionSuccessful = true;
                        }
                    }
                    break;

                case 6:
                    // 二刀流攻撃
                    EnemyUnit target6 = selectEnemyTarget(scanner);
                    if (target6 != null) {
                        // MP不足の場合はfalseが返るので、ループが継続される
                        if (magicSwordsman.dualWieldAttack(target6)) {
                            actionSuccessful = true;
                        }
                    }
                    break;

                case 7:
                    // 防御
                    magicSwordsman.defend();
                    actionSuccessful = true;
                    break;

                default:
                    System.out.println("無効な選択です。");
                    magicSwordsman.attack(selectEnemyTarget(scanner));
                    actionSuccessful = true;
                    break;
            }
        }
    }

    /**
     * 基本的な味方ユニットの行動
     */
    private void basicAllyAction(AllyUnit ally, Scanner scanner) {
        System.out.println("\n行動を選択:");
        System.out.println("1. 攻撃");
        System.out.println("2. 防御");
        System.out.print("選択: ");

        int action = scanner.nextInt();
        scanner.nextLine();

        if (action == 1) {
            EnemyUnit target = selectEnemyTarget(scanner);
            if (target != null) {
                ally.attack(target);
            }
        } else if (action == 2) {
            ally.defend();
        } else {
            System.out.println("無効な選択です。");
        }
    }

    /**
     * 魔法使用メニュー（MageとMagicSwordsmanで共通）
     */
    private boolean useMagicMenu(AllyUnit caster, Scanner scanner) {
        System.out.println("\n使用する魔法を選択:");
        System.out.println("1. ファイア（単体攻撃）");
        System.out.println("2. ブリザド（単体攻撃・氷系）");
        System.out.println("3. サンダー（単体攻撃・雷系）");
        System.out.println("4. ケアル（単体回復）");
        System.out.println("5. サンダガ（全体攻撃）");
        System.out.println("0. キャンセル");
        System.out.print("選択: ");

        int magicChoice = scanner.nextInt();
        scanner.nextLine();

        String magicName = "";
        List<BattleUnit> targets = new ArrayList<>();

        switch (magicChoice) {
            case 1:
                magicName = "ファイア";
                EnemyUnit target1 = selectEnemyTarget(scanner);
                if (target1 != null) targets.add(target1);
                break;

            case 2:
                magicName = "ブリザド";
                EnemyUnit target2 = selectEnemyTarget(scanner);
                if (target2 != null) targets.add(target2);
                break;

            case 3:
                magicName = "サンダー";
                EnemyUnit target3 = selectEnemyTarget(scanner);
                if (target3 != null) targets.add(target3);
                break;

            case 4:
                magicName = "ケアル";
                // 味方を対象に選択
                AllyUnit allyTarget = selectAllyTarget(scanner);
                if (allyTarget != null) targets.add(allyTarget);
                break;

            case 5:
                magicName = "サンダガ";
                // 全体攻撃
                targets.addAll(getAliveEnemies());
                break;

            case 0:
                System.out.println("魔法の使用をキャンセルしました。");
                return false;  // キャンセルは失敗として扱い、再選択可能にする

            default:
                System.out.println("無効な選択です。");
                return false;  // 無効な選択も失敗として扱い、再選択可能にする
        }

        // 対象が選択されていない場合は失敗
        if (targets.isEmpty()) {
            System.out.println("対象が選択されていません。");
            return false;
        }

        // 魔法を使用し、その結果を返す
        if (caster instanceof Mage) {
            return ((Mage) caster).useMagic(magicName, targets);
        } else if (caster instanceof MagicSwordsman) {
            return ((MagicSwordsman) caster).useMagic(magicName, targets);
        }

        return false;  // どのクラスにも該当しない場合は失敗
    }

    /**
     * 味方ユニットを選択
     */
    private AllyUnit selectAllyTarget(Scanner scanner) {
        List<AllyUnit> aliveAllies = getAliveAllies();

        if (aliveAllies.isEmpty()) {
            return null;
        }

        if (aliveAllies.size() == 1) {
            return aliveAllies.get(0);
        }

        System.out.println("\n対象を選択:");
        for (int i = 0; i < aliveAllies.size(); i++) {
            AllyUnit ally = aliveAllies.get(i);
            System.out.println((i + 1) + ". " + ally.getName() +
                             " (HP: " + ally.getHp() + "/" + ally.getMaxHp() +
                             ", MP: " + ally.getMp() + "/" + ally.getMaxMp() + ")");
        }

        System.out.print("対象を選択: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice >= 1 && choice <= aliveAllies.size()) {
            return aliveAllies.get(choice - 1);
        }

        return aliveAllies.get(0);  // デフォルトは最初の味方
    }

    /**
     * 敵の行動（自動）
     */
    private void enemyAction(EnemyUnit enemy) {
        // ドラゴンの特殊行動
        if (enemy instanceof Dragon) {
            Dragon dragon = (Dragon) enemy;
            // 30%の確率でファイアーブレス（全体攻撃）
            if (random.nextInt(100) < 30) {
                List<BattleUnit> allyTargets = new ArrayList<>();
                allyTargets.addAll(allies);
                dragon.fireBreath(allyTargets);
                return;
            }
        }

        // 通常攻撃：ランダムな味方を攻撃
        List<AllyUnit> aliveAllies = getAliveAllies();
        if (!aliveAllies.isEmpty()) {
            AllyUnit target = aliveAllies.get(random.nextInt(aliveAllies.size()));
            enemy.attack(target);
        }
    }

    /**
     * 攻撃対象の敵を選択
     */
    private EnemyUnit selectEnemyTarget(Scanner scanner) {
        List<EnemyUnit> aliveEnemies = getAliveEnemies();

        if (aliveEnemies.isEmpty()) {
            return null;
        }

        if (aliveEnemies.size() == 1) {
            return aliveEnemies.get(0);
        }

        System.out.println("\n攻撃対象を選択:");
        for (int i = 0; i < aliveEnemies.size(); i++) {
            EnemyUnit enemy = aliveEnemies.get(i);
            System.out.println((i + 1) + ". " + enemy.getName() +
                             " (HP: " + enemy.getHp() + "/" + enemy.getMaxHp() + ")");
        }

        System.out.print("対象を選択: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice >= 1 && choice <= aliveEnemies.size()) {
            return aliveEnemies.get(choice - 1);
        }

        return aliveEnemies.get(0);  // デフォルトは最初の敵
    }

    /**
     * 戦闘勝利処理
     */
    private void battleVictory() {
        System.out.println("\n==========================================");
        if (isBossBattle) {
            System.out.println("     ★ ボス撃破！ ★");
        } else {
            System.out.println("       戦闘に勝利した！");
        }
        System.out.println("==========================================\n");

        // 経験値とお金の計算
        int totalExp = 0;
        int totalMoney = 0;

        for (EnemyUnit enemy : enemies) {
            totalExp += enemy.getExp();
            totalMoney += enemy.getMoney();
        }

        // ボス戦ならボーナス
        if (isBossBattle) {
            totalExp = (int)(totalExp * 1.5);
            totalMoney = (int)(totalMoney * 2.0);
            System.out.println("★ ボス撃破ボーナス！ ★");
        }

        System.out.println("獲得経験値: " + totalExp);
        System.out.println("獲得ゴールド: " + totalMoney);
        System.out.println();

        // 味方全員に経験値を配分
        for (AllyUnit ally : allies) {
            if (ally.isAlive()) {
                ally.winGetExp(totalExp);
            }
        }

        // お金は最初の味方（主人公）に追加
        if (!allies.isEmpty()) {
            allies.get(0).winGetMoney(totalMoney);
        }
    }

    /**
     * 生存している味方がいるかチェック
     */
    private boolean hasAliveAllies() {
        for (AllyUnit ally : allies) {
            if (ally.isAlive()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 生存している敵がいるかチェック
     */
    private boolean hasAliveEnemies() {
        for (EnemyUnit enemy : enemies) {
            if (enemy.isAlive()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 生存している味方のリストを取得
     */
    private List<AllyUnit> getAliveAllies() {
        List<AllyUnit> aliveList = new ArrayList<>();
        for (AllyUnit ally : allies) {
            if (ally.isAlive()) {
                aliveList.add(ally);
            }
        }
        return aliveList;
    }

    /**
     * 生存している敵のリストを取得
     */
    private List<EnemyUnit> getAliveEnemies() {
        List<EnemyUnit> aliveList = new ArrayList<>();
        for (EnemyUnit enemy : enemies) {
            if (enemy.isAlive()) {
                aliveList.add(enemy);
            }
        }
        return aliveList;
    }

    /**
     * 戦闘回数を取得
     */
    public int getBattleCount() {
        return battleCount;
    }

}
