package com.soberg.lootsplit.domain.parse

import com.google.common.truth.Truth.assertThat
import com.soberg.lootsplit.domain.Coin
import org.junit.jupiter.api.Test

internal class LootPayloadParserTest {

    private val coinAmountParser = CoinAmountParser()
    private val lootPayloadParser = LootPayloadParser(
        coinAmountParser = coinAmountParser
    )

    @Test
    fun `parse and sum coin amounts successfully`() {
        val summationResult = lootPayloadParser.parse("87 Platinum,12gp,100g,1ep,34ep,4cp")
        assertThat(summationResult.isSuccess).isTrue()
        val summation = summationResult.getOrThrow()
        assertThat(summation[Coin.Platinum]).isEqualTo(87L)
        assertThat(summation[Coin.Gold]).isEqualTo(112L)
        assertThat(summation[Coin.Electrum]).isEqualTo(35L)
        assertThat(summation[Coin.Silver]).isNull()
        assertThat(summation[Coin.Copper]).isEqualTo(4L)
    }

    @Test
    fun `ignore empty entries`() {
        val summationResult = lootPayloadParser.parse("87 Platinum,,,,12sp,12s, 12 silver")
        assertThat(summationResult.isSuccess).isTrue()
        val summation = summationResult.getOrThrow()
        assertThat(summation[Coin.Platinum]).isEqualTo(87L)
        assertThat(summation[Coin.Gold]).isNull()
        assertThat(summation[Coin.Electrum]).isNull()
        assertThat(summation[Coin.Silver]).isEqualTo(36L)
        assertThat(summation[Coin.Copper]).isNull()
    }

    @Test
    fun `return failure result if only one coin amount fails to parse`() {
        val summationResult = lootPayloadParser.parse("8pp,12sp,3gp,60zorps")
        assertThat(summationResult.isFailure).isTrue()
    }
}