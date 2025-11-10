package javaRPG.Interface;

/**
 * IEquipSword interface
 * Ken wo soubi dekiru character ga jissou suru interface
 * Hero ya MagicSwordsman ga jissou suru
 */
public interface IEquipSword {

    /**
     * Ken wo soubi suru method
     * Item package no Sword class ga kansei shitara shiyou suru
     */
    void equipSword();

    /**
     * Ken niyoru kougeki method
     * @param target Kougeki taishou
     */
    void swordAttack(Object target);

}
