package javaRPG.magic;

import javaRPG.BattleUnit.BattleUnit;
import java.util.List;

/**
 * 魔法の基底クラス
 * すべての魔法の共通機能を定義します
 *
 * このクラスを継承して、FireBall、IceBall、Healなどの
 * 具体的な魔法クラスを作成します
 *
 * @author RPG開発チーム
 * @version 1.0
 */
public abstract class Magic {

    // ========================================
    // フィールド（属性）
    // ========================================

    /** 魔法の名前 */
    protected String magicName;

    /** 魔法の威力（基本値） */
    protected int power;

    /** 魔法の属性（'F'=火、'I'=氷、'H'=回復など） */
    protected final char ATTRIBUTE;

    /** 魔法のタイプ（"攻撃"、"回復"、"補助"など） */
    protected String magicType;

    // ========================================
    // コンストラクタ
    // ========================================

    /**
     * Magicクラスのコンストラクタ
     *
     * @param magicName 魔法の名前
     * @param power 魔法の基本威力
     * @param attribute 魔法の属性（'F'=火、'I'=氷、'H'=回復など）
     * @param magicType 魔法のタイプ（"攻撃"、"回復"など）
     */
    public Magic(String magicName, int power, char attribute, String magicType) {
        this.magicName = magicName;
        this.power = power;
        this.ATTRIBUTE = attribute;
        this.magicType = magicType;
    }

    // ========================================
    // 抽象メソッド（サブクラスで実装必須）
    // ========================================

    /**
     * 魔法を使用する（抽象メソッド）
     * 各魔法クラスで具体的な効果を実装します
     *
     * @param caster 魔法を使用するキャラクター
     * @param targets 魔法の対象（複数可）
     */
    public abstract void cast(BattleUnit caster, List<BattleUnit> targets);

    // ========================================
    // Getter / Setter メソッド
    // ========================================

    /**
     * 魔法の名前を取得
     * @return 魔法の名前
     */
    public String getMagicName() {
        return magicName;
    }

    /**
     * 魔法の名前を設定
     * @param magicName 魔法の名前
     */
    public void setMagicName(String magicName) {
        this.magicName = magicName;
    }

    /**
     * 魔法の基本威力を取得
     * レベルや装備によって実際の威力は変動します
     *
     * @return 基本威力
     */
    public int getPower() {
        return power;
    }

    /**
     * 魔法の威力を設定
     * レベルに応じて威力を変更する際に使用します
     *
     * @param power 新しい威力
     */
    public void setPower(int power) {
        this.power = power;
    }

    /**
     * 魔法の属性を取得
     * @return 属性文字（'F'=火、'I'=氷、'H'=回復など）
     */
    public char getATTRIBUTE() {
        return ATTRIBUTE;
    }

    /**
     * 魔法のタイプを取得
     * @return 魔法のタイプ（"攻撃"、"回復"など）
     */
    public String getMagicType() {
        return magicType;
    }

    /**
     * 魔法のタイプを設定
     * @param magicType 新しい魔法タイプ
     */
    public void setMagicType(String magicType) {
        this.magicType = magicType;
    }

    /**
     * 魔法の情報を文字列として返す
     * @return 魔法の情報
     */
    @Override
    public String toString() {
        return String.format("%s [%s] 威力:%d 属性:%c",
                magicName, magicType, power, ATTRIBUTE);
    }
}
