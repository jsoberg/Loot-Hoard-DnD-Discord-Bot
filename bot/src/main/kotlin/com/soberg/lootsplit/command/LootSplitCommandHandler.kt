package com.soberg.lootsplit.command

import discord4j.core.`object`.command.ApplicationCommandInteractionOption
import discord4j.core.`object`.command.ApplicationCommandInteractionOptionValue
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import reactor.core.publisher.Mono

object LootSplitCommandHandler {

    fun handle(event: ChatInputInteractionEvent): Mono<Void> {
        if (event.commandName != LootSplitCommand.CommandName) {
            return Mono.empty()
        }

        val numPlayers = event.getOption(LootSplitCommand.NumPlayersOptionName)
            .flatMap(ApplicationCommandInteractionOption::getValue)
            .map(ApplicationCommandInteractionOptionValue::asLong)
            .orElse(-1L)
        val csv = event.getOption(LootSplitCommand.LootOptionName)
            .flatMap(ApplicationCommandInteractionOption::getValue)
            .map(ApplicationCommandInteractionOptionValue::asString)
            .orElse("")
        return event.reply()
            .withEphemeral(true)
            .withContent("Command received, $numPlayers and {$csv}")
    }

}