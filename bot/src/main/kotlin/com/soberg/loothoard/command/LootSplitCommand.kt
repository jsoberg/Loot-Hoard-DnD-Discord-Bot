package com.soberg.loothoard.command

import discord4j.core.`object`.command.ApplicationCommandOption
import discord4j.discordjson.json.ApplicationCommandOptionData
import discord4j.discordjson.json.ApplicationCommandRequest

object LootSplitCommand {

    const val CommandName = "split"
    const val NumPlayersOptionName = "players"
    const val LootOptionName = "loot"

    fun create(): ApplicationCommandRequest =
        ApplicationCommandRequest.builder()
            .name(CommandName)
            .description("Splits D&D loot amongst players")
            .addOption(
                ApplicationCommandOptionData.builder()
                    .name(NumPlayersOptionName)
                    .description("Number of players to split loot for")
                    .type(ApplicationCommandOption.Type.INTEGER.value)
                    .required(true)
                    .build()
            )
            .addOption(
                ApplicationCommandOptionData.builder()
                    .name(LootOptionName)
                    .description("Comma separated list of loot to split (e.g. 100g,40s,5c)")
                    .type(ApplicationCommandOption.Type.STRING.value)
                    .required(true)
                    .build()
            )
            .build()
}