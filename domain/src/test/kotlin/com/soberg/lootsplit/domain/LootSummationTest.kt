package com.soberg.lootsplit.domain

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

internal class LootSummationTest {

    @Test
    fun `add expected amounts when building`() {
        val summation = LootSummation.build {
            add(CoinAmount(1L, Coin.Platinum))
            add(CoinAmount(2L, Coin.Gold))
            add(CoinAmount(3L, Coin.Electrum))
            add(CoinAmount(4L, Coin.Silver))
            add(CoinAmount(5L, Coin.Copper))
            add(CoinAmount(6L, Coin.Platinum))
        }

        assertThat(summation[Coin.Platinum]).isEqualTo(7L)
        assertThat(summation[Coin.Gold]).isEqualTo(2L)
        assertThat(summation[Coin.Electrum]).isEqualTo(3L)
        assertThat(summation[Coin.Silver]).isEqualTo(4L)
        assertThat(summation[Coin.Copper]).isEqualTo(5L)
    }

    @Test
    fun `split to expected base amount`() {
        val summation = LootSummation.build {
            add(CoinAmount(10L, Coin.Platinum))
            add(CoinAmount(20L, Coin.Gold))
            add(CoinAmount(30L, Coin.Electrum))
            add(CoinAmount(40L, Coin.Silver))
            add(CoinAmount(50L, Coin.Copper))
        }

        // (10 * 1000) + (20 * 100) + (30 * 50) + (40 * 10) + (50 * 1)
        assertThat(summation.splitToBase()).isEqualTo(13_950L)
    }
}