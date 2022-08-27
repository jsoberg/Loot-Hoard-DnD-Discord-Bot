package com.soberg.lootsplit.domain.parse

import com.google.common.truth.Truth.assertThat
import com.soberg.lootsplit.domain.Coin
import com.soberg.lootsplit.domain.CoinAmount
import org.junit.jupiter.api.Test

internal class CoinAmountParserTest {

    private val parser = CoinAmountParser()

    @Test
    fun `successfully parse with different coin aliases`() {
        assertThat(parseSuccess("100g")).isEqualTo(CoinAmount(100, Coin.Gold))
        assertThat(parseSuccess("200gp")).isEqualTo(CoinAmount(200, Coin.Gold))
        assertThat(parseSuccess("300silver")).isEqualTo(CoinAmount(300, Coin.Silver))
    }

    @Test
    fun `successfully parse with space between coin aliases`() {
        assertThat(parseSuccess("100 c")).isEqualTo(CoinAmount(100, Coin.Copper))
        assertThat(parseSuccess("200 pp")).isEqualTo(CoinAmount(200, Coin.Platinum))
        assertThat(parseSuccess("300 Electrum")).isEqualTo(CoinAmount(300, Coin.Electrum))
    }

    @Test
    fun `successfully parse with spaces before and after value`() {
        assertThat(parseSuccess(" 100 g")).isEqualTo(CoinAmount(100, Coin.Gold))
        assertThat(parseSuccess("200 gp ")).isEqualTo(CoinAmount(200, Coin.Gold))
        assertThat(parseSuccess("   300 Gold   ")).isEqualTo(CoinAmount(300, Coin.Gold))
    }

    @Test
    fun `successfully parse with underscores`() {
        assertThat(parseSuccess("1_000S")).isEqualTo(CoinAmount(1000, Coin.Silver))
        assertThat(parseSuccess("__200_000_Copper")).isEqualTo(CoinAmount(200_000, Coin.Copper))
        assertThat(parseSuccess("9_100000ep")).isEqualTo(CoinAmount(9_100_000, Coin.Electrum))
    }

    @Test
    fun `return error for random value`() {
        val parse = parser.parse("IEHFW W3FE WEFU3UW8 3823")
        assertThat(parse.isFailure).isTrue()
        assertThat(parse.exceptionOrNull()).hasMessageThat()
            .contains("Cannot parse coin name from \"IEHFW W3FE WEFU3UW8 3823\"")
    }

    @Test
    fun `return error for invalid coin name`() {
        val parse = parser.parse("10000gg")
        assertThat(parse.isFailure).isTrue()
        assertThat(parse.exceptionOrNull()).hasMessageThat()
            .contains("Cannot parse coin name from \"10000gg\"")
    }

    @Test
    fun `return error for invalid coin amount`() {
        val parse = parser.parse("1B0000gp")
        assertThat(parse.isFailure).isTrue()
        assertThat(parse.exceptionOrNull()).hasMessageThat()
            .contains("Invalid format for amount value \"1B0000\"")
    }

    @Test
    fun `return error for no specified coin name`() {
        val parse = parser.parse("10000")
        assertThat(parse.isFailure).isTrue()
        assertThat(parse.exceptionOrNull()).hasMessageThat()
            .contains("Cannot parse coin name from \"10000\"")
    }

    private fun parseSuccess(value: String): CoinAmount {
        val parsed = parser.parse(value)
        assertThat(parsed.isSuccess).isTrue()
        return parsed.getOrThrow()
    }
}