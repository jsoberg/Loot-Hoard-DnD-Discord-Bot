package com.soberg.loothoard.domain.lootsplit

import com.google.common.truth.Truth.assertThat
import com.soberg.loothoard.domain.lootsplit.calculate.RoundingLootCalculator
import com.soberg.loothoard.domain.parse.LootPayloadParser
import org.junit.jupiter.api.Test

internal class LootSplitProcessorTest {

    private val parser = LootPayloadParser()
    private val calculator = RoundingLootCalculator()

    private val processor = LootSplitProcessor(
        parser = parser,
        calculator = calculator,
    )

    @Test
    fun `reject when players less than or equal to 0`() {
        assertThat(processor.process(-1, ""))
            .isEqualTo("Unable to split loot: -1 is an invalid number of players")
        assertThat(processor.process(0, ""))
            .isEqualTo("Unable to split loot: 0 is an invalid number of players")
    }

    @Test
    fun `return expected message when payload parser throws`() {
        assertThat(processor.process(4, "random"))
            .isEqualTo("Unable to split loot: Invalid format for amount value \"random\"")
    }

    @Test
    fun `return expected message when successfully split evenly`() {
        assertThat(processor.process(4, "4g,4s,4c"))
            .isEqualTo(
                "Splitting 4 Gold, 4 Silver, and 4 Copper amongst 4 players...\n" +
                        "Each player receives 1 Gold, 1 Silver, and 1 Copper."
            )
    }

    @Test
    fun `return expected message when loot leftover`() {
        assertThat(processor.process(4, "4g,4s,5c"))
            .isEqualTo(
                "Splitting 4 Gold, 4 Silver, and 5 Copper amongst 4 players...\n" +
                        "Each player receives 1 Gold, 1 Silver, and 1 Copper.\n" +
                        "1 Copper is leftover after being split evenly."
            )
    }

    @Test
    fun `return expected message when no loot can be split evenly`() {
        assertThat(processor.process(4, "1c"))
            .isEqualTo(
                "Splitting 1 Copper amongst 4 players...\n" +
                        "No loot could be evenly split amongst the players.\n" +
                        "1 Copper is leftover."
            )
    }
}