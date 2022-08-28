package com.soberg.lootsplit.domain

data class CoinAmount(
    val amount: Long,
    val coin: Coin,
) {
    override fun toString(): String = "$amount ${coin.humanReadableIdentifier}"
}