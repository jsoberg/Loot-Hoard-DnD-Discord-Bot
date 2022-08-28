package com.soberg.lootsplit.domain

/** Describes the available coins that we can split.
 * @param baseCopperValue The amount of [Copper] (our base) that 1 of this Coin is worth.
 * @param aliases A set of aliases that can represent this Coin, ignoring case.
 */
enum class Coin(
    val baseCopperValue: Int,
    val aliases: Set<String>,
) {
    Copper(
        baseCopperValue = 1,
        aliases = setOf("c", "cp", "copper")
    ),

    /** 1sp = 10cp */
    Silver(
        baseCopperValue = 10,
        aliases = setOf("s", "sp", "silver")
    ),

    /** 1ep = 5sp = 50cp */
    Electrum(
        baseCopperValue = 50,
        aliases = setOf("e", "ep", "electrum")
    ),

    /** 1gp = 2ep = 10sp = 100cp */
    Gold(
        baseCopperValue = 100,
        aliases = setOf("g", "gp", "gold")
    ),

    /** 1pp = 10gp = 20ep = 100sp = 1000cp */
    Platinum(
        baseCopperValue = 1000,
        aliases = setOf("p", "pp", "platinum")
    );

    /** @return The amount of [Base]'s value that [amount] represents when converted from this Coin to [Copper], the base [Coin].
     * Example: [Gold.toBaseCoinAmount] with an amount of 10 would return 1000, since 10 [Gold] converted to the base [Copper] is (10 * 100) [Copper].
     */
    fun toBaseCoinAmount(amount: Long): Long = (baseCopperValue * amount)

    /** The next highest value [Coin] available in comparison to this [Coin], or null if this is the highest value [Coin].
     * Example: [Electrum.nextHighestValueCoin] is [Gold], since [Gold] is the next coin up in the value hierarchy from [Electrum].
     */
    val nextHighestValueCoin: Coin?
        get() = when (this) {
            Copper -> Silver
            Silver -> Electrum
            Electrum -> Gold
            Gold -> Platinum
            Platinum -> null
        }

    /** The next lowest value [Coin] available in comparison to this [Coin], or null if this is the lowest value [Coin].
     * Example: [Silver.nextLowestValueCoin] is [Copper], since [Copper] is the next coin down in the value hierarchy from [Silver].
     */
    val nextLowestValueCoin: Coin?
        get() = when (this) {
            Platinum -> Gold
            Gold -> Electrum
            Electrum -> Silver
            Silver -> Copper
            Copper -> null
        }

    companion object {

        fun fromString(value: String): Coin? {
            val lowercase = value.lowercase()
            for (coin in values()) {
                if (coin.aliases.contains(lowercase)) {
                    return coin
                }
            }
            return null
        }
    }
}