package com.soberg.lootsplit.domain

import com.google.common.truth.Truth.assertThat
import com.soberg.lootsplit.domain.Coin.*
import org.junit.jupiter.api.Test

internal class CoinTest {

    @Test
    fun `parse mixed case full strings`() {
        assertThat(Coin.fromString("PlAtInUm")).isEqualTo(Platinum)
        assertThat(Coin.fromString("GoLd")).isEqualTo(Gold)
        assertThat(Coin.fromString("eLeCtRuM")).isEqualTo(Electrum)
        assertThat(Coin.fromString("sIlVeR")).isEqualTo(Silver)
        assertThat(Coin.fromString("CoPpEr")).isEqualTo(Copper)
    }

    @Test
    fun `parse mixed case abbreviations`() {
        assertThat(Coin.fromString("pP")).isEqualTo(Platinum)
        assertThat(Coin.fromString("p")).isEqualTo(Platinum)
        assertThat(Coin.fromString("Gp")).isEqualTo(Gold)
        assertThat(Coin.fromString("G")).isEqualTo(Gold)
        assertThat(Coin.fromString("eP")).isEqualTo(Electrum)
        assertThat(Coin.fromString("e")).isEqualTo(Electrum)
        assertThat(Coin.fromString("Sp")).isEqualTo(Silver)
        assertThat(Coin.fromString("s")).isEqualTo(Silver)
        assertThat(Coin.fromString("CP")).isEqualTo(Copper)
        assertThat(Coin.fromString("c")).isEqualTo(Copper)
    }

    @Test
    fun `return null for unknown string`() {
        assertThat(Coin.fromString("NotACoin")).isNull()
    }


    @Test
    fun `convert to expected base amounts`() {
        assertThat(Platinum.toBaseCoinAmount(10)).isEqualTo(10_000L)
        assertThat(Gold.toBaseCoinAmount(10)).isEqualTo(1_000L)
        assertThat(Electrum.toBaseCoinAmount(10)).isEqualTo(500L)
        assertThat(Silver.toBaseCoinAmount(10)).isEqualTo(100L)
        assertThat(Copper.toBaseCoinAmount(10)).isEqualTo(10L)
    }

    @Test
    fun `have expected coin value up hierarchy`() {
        assertThat(Copper.nextHighestValueCoin).isEqualTo(Silver)
        assertThat(Silver.nextHighestValueCoin).isEqualTo(Electrum)
        assertThat(Electrum.nextHighestValueCoin).isEqualTo(Gold)
        assertThat(Gold.nextHighestValueCoin).isEqualTo(Platinum)
        assertThat(Platinum.nextHighestValueCoin).isNull()
    }

    @Test
    fun `have expected coin value down hierarchy`() {
        assertThat(Platinum.nextLowestValueCoin).isEqualTo(Gold)
        assertThat(Gold.nextLowestValueCoin).isEqualTo(Electrum)
        assertThat(Electrum.nextLowestValueCoin).isEqualTo(Silver)
        assertThat(Silver.nextLowestValueCoin).isEqualTo(Copper)
        assertThat(Copper.nextLowestValueCoin).isNull()
    }
}