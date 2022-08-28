package com.soberg.lootsplit.domain

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

internal class CoinAmountTest {

    @Test
    fun `return expected values for toString`() {
        assertThat(CoinAmount(32, Coin.Platinum).toString()).isEqualTo("32 Platinum")
        assertThat(CoinAmount(100, Coin.Gold).toString()).isEqualTo("100 Gold")
        assertThat(CoinAmount(82, Coin.Electrum).toString()).isEqualTo("82 Electrum")
        assertThat(CoinAmount(1000, Coin.Silver).toString()).isEqualTo("1000 Silver")
        assertThat(CoinAmount(235, Coin.Copper).toString()).isEqualTo("235 Copper")
    }
}