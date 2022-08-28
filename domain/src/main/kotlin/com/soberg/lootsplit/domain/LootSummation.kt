package com.soberg.lootsplit.domain

class LootSummation private constructor(
    private val coinAmountMap: Map<Coin, Long>,
) : Map<Coin, Long> by coinAmountMap {

    /** @return This [LootSummation] split to the base coin of [Coin.Copper]. */
    fun splitToBase(): Long {
        var total = 0L
        for ((coin, amount) in coinAmountMap) {
            total += coin.toBaseCoinAmount(amount)
        }
        return total
    }

    companion object {
        fun build(block: Builder.() -> Unit): LootSummation =
            Builder().apply(block)
                .build()
    }

    class Builder {
        private val coinAmountMap: MutableMap<Coin, Long> = mutableMapOf()

        fun add(coinAmount: CoinAmount): Builder = apply {
            val current = coinAmountMap[coinAmount.coin] ?: 0L
            val newAmount = current + coinAmount.amount
            if (newAmount != 0L) {
                coinAmountMap[coinAmount.coin] = current + coinAmount.amount
            }
        }

        fun add(amount: Long, coin: Coin): Builder = apply {
            add(CoinAmount(amount, coin))
        }

        fun build() = LootSummation(coinAmountMap.toMap())
    }
}