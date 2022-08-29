package com.soberg.lootsplit.domain.calculate

import com.soberg.lootsplit.domain.Coin
import com.soberg.lootsplit.domain.LootSummation

/** [LootCalculator] implementation that will round to the specified [Coin] in [roundTo] after splitting. */
class RoundingLootCalculator(
    private val roundTo: Coin = Coin.Gold,
) : LootCalculator {

    override fun calculate(
        numPlayers: Int,
        loot: LootSummation,
    ): LootCalculator.Result {
        val lootBuilder = LootSummation.Builder()

        // First calculate our roundTo coin.
        val baseCoinAmount = loot.splitToBase()
        val roundCoinAmount = (baseCoinAmount / roundTo.baseCopperValue)
        lootBuilder.add(roundCoinAmount / numPlayers, roundTo)
        var leftoverCopper = (baseCoinAmount % roundTo.baseCopperValue) +
                roundTo.toBaseCoinAmount(roundCoinAmount % numPlayers)

        // Now, iterate down from the rounding coin until we have no leftover.
        var currentCoin: Coin? = roundTo.nextLowestValueCoin
        while (currentCoin != null && leftoverCopper != 0L) {
            val currentCoinAmount = (leftoverCopper / currentCoin.baseCopperValue)
            lootBuilder.add(currentCoinAmount / numPlayers, currentCoin)

            leftoverCopper = (leftoverCopper % currentCoin.baseCopperValue) +
                    currentCoin.toBaseCoinAmount(currentCoinAmount % numPlayers)
            currentCoin = currentCoin.nextLowestValueCoin
        }
        return LootCalculator.Result(
            numPlayers = numPlayers,
            originalPayload = loot,
            lootPerPlayer = lootBuilder.build(),
            leftoverLoot = LootSummation.build { add(leftoverCopper, Coin.Copper) },
        )
    }
}