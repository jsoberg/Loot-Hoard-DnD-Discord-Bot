package com.soberg.loothoard.discord.commands.lootsplit

import com.soberg.loothoard.discord.commands.addRequiredOption
import discord4j.core.`object`.command.ApplicationCommandOption
import discord4j.discordjson.json.ApplicationCommandRequest

object LootSplitCommandFactory {

    const val CommandName = "split"
    const val NumPlayersOptionName = "players"
    const val LootOptionName = "loot"

    fun create(): ApplicationCommandRequest =
        ApplicationCommandRequest.builder()
            .name(CommandName)
            .description("Splits D&D loot amongst players")
            .addRequiredOption(
                name = NumPlayersOptionName,
                description = "Number of players to split loot for",
                type = ApplicationCommandOption.Type.INTEGER,
            )
            .addRequiredOption(
                name = LootOptionName,
                description = "Comma separated list of loot to split (e.g. 100g,40s,5c)",
                type = ApplicationCommandOption.Type.STRING,
            )
            .build()
}