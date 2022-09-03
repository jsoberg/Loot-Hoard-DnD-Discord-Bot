package com.soberg.loothoard.discord.commands

import discord4j.core.`object`.command.ApplicationCommandOption
import discord4j.discordjson.json.ApplicationCommandOptionData
import discord4j.discordjson.json.ImmutableApplicationCommandRequest

fun ImmutableApplicationCommandRequest.Builder.addRequiredOption(
    name: String,
    description: String,
    type: ApplicationCommandOption.Type,
): ImmutableApplicationCommandRequest.Builder = addOption(
    ApplicationCommandOptionData.builder()
        .name(name)
        .description(description)
        .type(type.value)
        .required(true)
        .build()
)