package com.soberg.lootsplit.domain

class LootSummation private constructor(
    private val coinAmountMap: Map<Coin, Long>,
) : Map<Coin, Long> by coinAmountMap {

    fun splitToBase(): CoinAmount {
        var total = 0L
        for ((coin, amount) in coinAmountMap) {
            total += coin.toBaseCoinAmount(amount).amount
        }
        return CoinAmount(
            amount = total,
            coin = Coin.Base,
        )
    }

    companion object {
        fun build(block: Builder.() -> Unit): LootSummation =
            Builder().apply(block)
                .build()
    }

    class Builder internal constructor() {
        private val coinAmountMap: MutableMap<Coin, Long> = mutableMapOf()

        fun add(coinAmount: CoinAmount): Builder = apply {
            val current = coinAmountMap[coinAmount.coin] ?: 0L
            coinAmountMap[coinAmount.coin] = current + coinAmount.amount
        }

        fun build() = LootSummation(coinAmountMap.toMap())
    }
}