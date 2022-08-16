package com.soberg.lootsplit.domain

class LootSummation private constructor(
    private val coinAmountMap: Map<Coin, Long>,
) : Map<Coin, Long> by coinAmountMap {
    
    internal class Builder {
        private val coinAmountMap: MutableMap<Coin, Long> = mutableMapOf()

        fun add(coinAmount: CoinAmount): Builder = apply {
            val current = coinAmountMap[coinAmount.coin] ?: 0L
            coinAmountMap[coinAmount.coin] = current + coinAmount.amount
        }

        fun build() = LootSummation(coinAmountMap.toMap())
    }
}