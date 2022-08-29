package com.soberg.lootsplit.domain.calculate

import com.soberg.lootsplit.domain.LootSummation

interface LootCalculator {

    /** @return A [LootCalculator.Result] that splits the provided loot parameters amongst players.
     * @param numPlayers Number of players that this result was split for.
     * @param loot The [LootSummation] to split amongst players.
     */
    fun calculate(
        numPlayers: Int,
        loot: LootSummation,
    ): Result

    /**
     * @param numPlayers Number of players that this result was split for.
     * @param originalPayload The original [LootSummation] that was used to calculate a split.
     * @param lootPerPlayer A [LootSummation] that each individual player will receive from this split.
     * @param leftoverLoot A [LootSummation] that is leftover after splitting loot, if the loot could not be evenly split amongst players.
     */
    data class Result(
        val numPlayers: Int,
        val originalPayload: LootSummation,
        val lootPerPlayer: LootSummation,
        val leftoverLoot: LootSummation?,
    )
}