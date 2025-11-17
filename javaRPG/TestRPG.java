package javaRPG;

import javaRPG.AllyUnit.Hero;
import javaRPG.AllyUnit.Mage;
import javaRPG.AllyUnit.MagicSwordsman;
import javaRPG.EnemyUnit.Slime;
import javaRPG.EnemyUnit.Goblin;
import javaRPG.EnemyUnit.Dragon;
import javaRPG.item.Sword;
import javaRPG.item.Staff;
import javaRPG.magic.FireBall;
import javaRPG.magic.Heal;
import java.util.Arrays;

public class TestRPG {
    public static void main(String[] args) {
        System.out.println("===== RPGシステムテスト開始 =====\n");
        
        try {
            // 1. キャラクター作成テスト
            System.out.println("[1] キャラクター作成テスト");
            Hero hero = new Hero();
            Mage mage = new Mage();
            MagicSwordsman magicSwordsman = new MagicSwordsman();
            System.out.println("✓ Hero作成成功: " + hero.getName());
            System.out.println("✓ Mage作成成功: " + mage.getName());
            System.out.println("✓ MagicSwordsman作成成功: " + magicSwordsman.getName());
            
            // 2. 敵作成テスト
            System.out.println("\n[2] 敵作成テスト");
            Slime slime = new Slime(1);
            Goblin goblin = new Goblin(1);
            Dragon dragon = new Dragon(3);
            System.out.println("✓ Slime作成成功: " + slime.getName());
            System.out.println("✓ Goblin作成成功: " + goblin.getName());
            System.out.println("✓ Dragon作成成功: " + dragon.getName());
            
            // 3. アイテム作成と装備テスト
            System.out.println("\n[3] アイテム装備テスト");
            Sword sword = new Sword("テスト剣", "テスト用", 10, 100);
            Staff staff = new Staff("テスト杖", "テスト用", 15, 150);
            
            hero.equipSword(sword);
            mage.equipStaff(staff);
            magicSwordsman.equipSword(sword);
            magicSwordsman.equipStaff(staff);
            System.out.println("✓ 装備テスト成功");
            
            // 4. 攻撃テスト
            System.out.println("\n[4] 攻撃テスト");
            hero.attack(slime);
            mage.staffAttack(goblin);
            System.out.println("✓ 攻撃テスト成功");
            
            // 5. 魔法テスト
            System.out.println("\n[5] 魔法テスト");
            mage.useMagic("ファイア", Arrays.asList(slime));
            System.out.println("✓ 魔法テスト成功");
            
            // 6. 回復テスト
            System.out.println("\n[6] 回復テスト");
            hero.heal(20);
            System.out.println("✓ 回復テスト成功");
            
            // 7. 経験値・お金テスト
            System.out.println("\n[7] 経験値・お金テスト");
            hero.winGetExp(50);
            hero.winGetMoney(100);
            System.out.println("✓ 経験値・お金テスト成功");
            
            System.out.println("\n===== すべてのテストが成功しました！ =====");
            
        } catch (Exception e) {
            System.err.println("\n===== エラーが発生しました！ =====");
            e.printStackTrace();
        }
    }
}
