package com.soberg.loothoard.discord.commands

import discord4j.core.`object`.command.ApplicationCommandInteractionOption
import discord4j.core.`object`.command.ApplicationCommandInteractionOptionValue
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent

fun ChatInputInteractionEvent.getLongOrElse(
    optionName: String,
    orElse: Long,
): Long = getOption(optionName)
    .flatMap(ApplicationCommandInteractionOption::getValue)
    .map(ApplicationCommandInteractionOptionValue::asLong)
    .orElse(orElse)

fun ChatInputInteractionEvent.getStringOrElse(
    optionName: String,
    orElse: String,
): String = getOption(optionName)
    .flatMap(ApplicationCommandInteractionOption::getValue)
    .map(ApplicationCommandInteractionOptionValue::asString)
    .orElse(orElse)