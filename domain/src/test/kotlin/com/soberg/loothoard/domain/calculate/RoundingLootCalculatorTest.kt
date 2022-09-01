package com.soberg.loothoard.domain.calculate

import com.google.common.truth.Truth.assertThat
import com.soberg.loothoard.domain.Coin
import com.soberg.loothoard.domain.LootSummation
import org.junit.jupiter.api.Test

internal class RoundingLootCalculatorTest {

    @Test
    fun `round with expected coin steps, starting with roundTo and iterating down, with expected leftover amount`() {
        val calculator = RoundingLootCalculator(roundTo = Coin.Gold)
        // (5g + 9e + 49s + 3c) = (14g + 4s + 3c)
        val loot = LootSummation.build {
            add(5, Coin.Gold)
            add(9, Coin.Electrum)
            add(49, Coin.Silver)
            add(3, Coin.Copper)
        }
        val result = calculator.calculate(numPlayers = 4, loot)
        // When we split, starting at Gold:
        // 1. ((1443c / 100c/g) = 14g 43c) / 4 = 3g (243c leftover)
        // 2. ((243c / 50c/e) = 4e 43c) / 4 = 1e (43c leftover)
        // 3. ((43c / 10c/s) = 4s 3c) / 4 = 1s (3c leftover)
        // 4. ((3c / 1c/c) = 3c) / 4 = 0c (3c leftover)
        assertThat(result.lootPerPlayer[Coin.Platinum]).isNull()
        assertThat(result.lootPerPlayer[Coin.Gold]).isEqualTo(3L)
        assertThat(result.lootPerPlayer[Coin.Electrum]).isEqualTo(1L)
        assertThat(result.lootPerPlayer[Coin.Silver]).isEqualTo(1L)
        assertThat(result.lootPerPlayer[Coin.Copper]).isNull()

        assertThat(result.leftoverLoot).isNotNull()
        assertThat(result.leftoverLoot!!.keys).containsExactly(Coin.Copper)
        assertThat(result.leftoverLoot!![Coin.Copper]).isEqualTo(3L)
    }

    @Test
    fun `round as expected when roundTo is base coin`() {
        val calculator = RoundingLootCalculator(roundTo = Coin.Copper)
        val loot = LootSummation.build {
            add(5, Coin.Gold)
            add(9, Coin.Electrum)
            add(49, Coin.Silver)
            add(3, Coin.Copper)
        }
        val result = calculator.calculate(numPlayers = 4, loot)
        assertThat(result.lootPerPlayer.keys).containsExactly(Coin.Copper)
        assertThat(result.lootPerPlayer[Coin.Copper]).isEqualTo(360L)

        assertThat(result.leftoverLoot).isNotNull()
        assertThat(result.leftoverLoot!!.keys).containsExactly(Coin.Copper)
        assertThat(result.leftoverLoot!![Coin.Copper]).isEqualTo(3L)
    }

    @Test
    fun `round to expected coin even when higher value coins can be split evenly`() {
        val calculator = RoundingLootCalculator(roundTo = Coin.Gold)
        val loot = LootSummation.build {
            add(10, Coin.Platinum)
            add(10, Coin.Gold)
            add(20, Coin.Electrum)
            add(100, Coin.Silver)
            add(1000, Coin.Copper)
        }
        val result = calculator.calculate(numPlayers = 10, loot)
        // (1p + 1g + 2e + 10s + 100c) per player expected, which rounds to 14g
        assertThat(result.lootPerPlayer.keys).containsExactly(Coin.Gold)
        assertThat(result.lootPerPlayer[Coin.Gold]).isEqualTo(14L)
    }

    @Test
    fun `calculate when loot can't be split`() {
        val calculator = RoundingLootCalculator(roundTo = Coin.Gold)
        val loot = LootSummation.build {
            add(11, Coin.Copper)
        }
        val result = calculator.calculate(numPlayers = 12, loot)
        assertThat(result.lootPerPlayer).isEmpty()

        assertThat(result.leftoverLoot).isNotNull()
        assertThat(result.leftoverLoot!!.keys).containsExactly(Coin.Copper)
        assertThat(result.leftoverLoot!![Coin.Copper]).isEqualTo(11L)
    }
}