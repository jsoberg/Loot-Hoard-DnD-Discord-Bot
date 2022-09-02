package com.soberg.loothoard.command

import com.soberg.loothoard.domain.calculate.LootCalculator
import com.soberg.loothoard.domain.calculate.RoundingLootCalculator
import com.soberg.loothoard.domain.parse.LootPayloadParser
import com.soberg.loothoard.utils.joinToString

class LootSplitCommandProcessor(
    private val parser: LootPayloadParser = LootPayloadParser(),
    private val calculator: LootCalculator = RoundingLootCalculator(),
) {

    fun process(
        numPlayers: Int,
        lootPayload: String,
    ): String = runCatching {
        if (numPlayers <= 0) {
            error("$numPlayers is an invalid number of players")
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
        val original = result.originalPayload.inDescendingValueSortedOrder()
            .joinToString(separator = ", ", lastSeparator = ", and ")
        val outputBuilder =
            StringBuilder("Splitting $original amongst ${result.numPlayers} players...\n")

        val wasSplitEvenly = result.lootPerPlayer.isNotEmpty()
        if (!wasSplitEvenly) {
            outputBuilder.append("No loot could be evenly split amongst the players.")
        } else {
            outputBuilder.append("Each player receives ")
            val joined = result.lootPerPlayer.inDescendingValueSortedOrder()
                .joinToString(separator = ", ", lastSeparator = ", and ")
            outputBuilder.append(joined)
                .append(".")
        }

        if (result.leftoverLoot?.isNotEmpty() == true) {
            val joined = result.leftoverLoot?.inDescendingValueSortedOrder()
                ?.joinToString(separator = ", ", lastSeparator = ", and ")
            outputBuilder.appendLine()
                .append(joined)
                .append(" is leftover")
            if (wasSplitEvenly) {
                outputBuilder.append(" after being split evenly.")
            } else {
                outputBuilder.append(".")
            }
        }

        return outputBuilder.toString()
    }

}