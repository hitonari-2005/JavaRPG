package javaRPG.Interface;

/**
 * Battlable interface
 * Sentou kanouna entity ga jissou suru interface
 * BattleUnit ya sono ko class ga jissou suru
 */
public interface Battlable {

    /**
     * Kougeki method - Taishou wo kougeki suru
     * @param target Kougeki taishou
     */
    void attack(Object target);

    /**
     * Damage wo ukeru method
     * @param damage Ukeru damage ryou
     */
    void takeDamage(int damage);

    /**
     * HP wo kaifuku suru method
     * @param amount Kaifuku ryou
     */
    void heal(int amount);

    /**
     * Seizon kakunin method
     * @return Ikiteiru baai true, taorete iru baai false
     */
    boolean isAlive();

}
