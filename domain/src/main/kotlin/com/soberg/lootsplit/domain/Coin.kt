package com.soberg.lootsplit.domain

enum class Coin(
    private val baseFactor: Int,
    val aliases: Set<String>,
) {
    Copper(
        baseFactor = 1,
        aliases = setOf("c", "cp", "copper")
    ),

    /** 1sp = 10cp */
    Silver(
        baseFactor = 10,
        aliases = setOf("s", "sp", "silver")
    ),

    /** 1ep = 5sp = 50cp */
    Electrum(
        baseFactor = 50,
        aliases = setOf("e", "ep", "electrum")
    ),

    /** 1gp = 2ep = 10sp = 100cp */
    Gold(
        baseFactor = 100,
        aliases = setOf("g", "gp", "gold")
    ),

    /** 1pp = 10gp = 20ep = 100sp = 1000cp */
    Platinum(
        baseFactor = 1000,
        aliases = setOf("p", "pp", "platinum")
    );

    /** @return The amount of [Base]'s value that [amount] represents when converted from this [Coin] to [Base].
     * Example: Coin.Gold.toBaseCountAmount(10) = 1000 Copper (10 Gold coins converted to the base coin Copper, resulting in an amount of 1000 Copper coins.)
     */
    fun toBaseCoinAmount(amount: Long): CoinAmount = CoinAmount(
        amount = (baseFactor * amount),
        coin = Base,
    )

    companion object {
        val Base = Copper

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