package com.soberg.lootsplit.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class LootSummationTest {

    @Test
    fun `add expected amounts when building`() {
        val builder = LootSummation.Builder()
        builder.add(CoinAmount(1L, Coin.Platinum))
        builder.add(CoinAmount(2L, Coin.Gold))
        builder.add(CoinAmount(3L, Coin.Electrum))
        builder.add(CoinAmount(4L, Coin.Silver))
        builder.add(CoinAmount(5L, Coin.Copper))
        builder.add(CoinAmount(6L, Coin.Platinum))

        val summation = builder.build()
        assertThat(summation[Coin.Platinum]).isEqualTo(7L)
        assertThat(summation[Coin.Gold]).isEqualTo(2L)
        assertThat(summation[Coin.Electrum]).isEqualTo(3L)
        assertThat(summation[Coin.Silver]).isEqualTo(4L)
        assertThat(summation[Coin.Copper]).isEqualTo(5L)
    }
}