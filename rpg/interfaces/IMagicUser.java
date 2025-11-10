package rpg.interfaces;

import rpg.base.BattleUnit;
import java.util.List;

public interface IMagicUser {
    void useMagic(String magicName, List<BattleUnit> targets);
}
