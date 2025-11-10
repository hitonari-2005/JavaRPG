package rpg.merchant;

import rpg.base.AllyUnit;
import rpg.item.Sword;
import rpg.item.Staff;
import rpg.interfaces.Sellable;
import rpg.interfaces.IEquipSword;
import rpg.interfaces.IEquipStaff;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// 商人クラス - 剣と杖のみを販売
public class CommonMerchant {
    private List<Sellable> inventory;

    public CommonMerchant() {
        this.inventory = new ArrayList<>();
        initializeInventory();
    }

    private void initializeInventory() {
        // 剣を追加
        inventory.add(new Sword("ブロンズソード", "初心者用の剣", 5, 100));
        inventory.add(new Sword("アイアンソード", "鉄製の剣", 10, 300));
        inventory.add(new Sword("ミスリルソード", "ミスリル製の高級な剣", 20, 800));

        // 杖を追加
        inventory.add(new Staff("木の杖", "初心者用の杖", 8, 150));
        inventory.add(new Staff("魔導杖", "魔力を増幅する杖", 15, 400));
        inventory.add(new Staff("賢者の杖", "賢者が使う強力な杖", 25, 1000));
    }

    public void showShop(AllyUnit ally, Scanner scanner) {
        while (true) {
            System.out.println("\n=== 商人の店 ===");
            System.out.println("所持金: " + ally.getMoney() + " ゴールド");
            System.out.println("\n商品一覧:");

            for (int i = 0; i < inventory.size(); i++) {
                Sellable item = inventory.get(i);
                System.out.println((i + 1) + ". " + item.getName() + " - " + item.getPrice() + "G");
            }

            System.out.println("0. 店を出る");
            System.out.print("\n購入する商品を選択してください: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                System.out.println("またのご来店をお待ちしております！");
                break;
            }

            if (choice < 1 || choice > inventory.size()) {
                System.out.println("無効な選択です。");
                continue;
            }

            Sellable selectedItem = inventory.get(choice - 1);

            if (ally.getMoney() < selectedItem.getPrice()) {
                System.out.println("お金が足りません！");
                continue;
            }

            // 装備可能かチェック
            if (selectedItem instanceof Sword && !(ally instanceof IEquipSword)) {
                System.out.println(ally.getName() + " は剣を装備できません！");
                continue;
            }

            if (selectedItem instanceof Staff && !(ally instanceof IEquipStaff)) {
                System.out.println(ally.getName() + " は杖を装備できません！");
                continue;
            }

            // 購入処理
            ally.spendMoney(selectedItem.getPrice());
            System.out.println(selectedItem.getName() + " を購入しました！");

            // 装備
            if (selectedItem instanceof Sword && ally instanceof IEquipSword) {
                ((IEquipSword) ally).equipSword((Sword) selectedItem);
            } else if (selectedItem instanceof Staff && ally instanceof IEquipStaff) {
                ((IEquipStaff) ally).equipStaff((Staff) selectedItem);
            }
        }
    }
}
