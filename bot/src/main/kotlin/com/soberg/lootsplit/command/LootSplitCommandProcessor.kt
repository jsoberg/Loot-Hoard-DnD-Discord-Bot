package com.soberg.lootsplit.command

import com.soberg.lootsplit.domain.calculate.LootCalculator
import com.soberg.lootsplit.domain.calculate.RoundingLootCalculator
import com.soberg.lootsplit.domain.parse.LootPayloadParser
import com.soberg.lootsplit.utils.joinToString

class LootSplitCommandProcessor(
    private val parser: LootPayloadParser = LootPayloadParser(),
    private val calculator: LootCalculator = RoundingLootCalculator(),
) {

    fun process(
        numPlayers: Int,
        lootPayload: String,
    ): String = runCatching {
        if (numPlayers <= 0) {
            error("Cannot split loot for $numPlayers players")
        }

        val loot = parser.parse(lootPayload).getOrThrow()
        val calculation = calculator.calculate(
            numPlayers = numPlayers,
            loot = loot,
        )
        toOutputString(calculation)
    }.fold(
        onSuccess = { it },
        onFailure = { "Unable to split loot: ${it.message}" }
    )

    private fun toOutputString(result: LootCalculator.Result): String {
        val outputBuilder = StringBuilder()

        if (result.lootPerPlayer.isEmpty()) {
            outputBuilder.append("No loot could be evenly split amongst the players. ")
        } else {
            outputBuilder.append("Each player receives ")
            val joined = result.lootPerPlayer.inDescendingValueSortedOrder()
                .joinToString(separator = ", ", lastSeparator = ", and ")
            outputBuilder.append(joined)
                .append(". ")
        }

        if (result.leftoverLoot?.isNotEmpty() == true) {
            val joined = result.lootPerPlayer.inDescendingValueSortedOrder()
                .joinToString(separator = ", ", lastSeparator = ", and ")
            outputBuilder.append(joined)
                .append(" is leftover after splitting evenly.")
        }

        return outputBuilder.toString()
    }

}