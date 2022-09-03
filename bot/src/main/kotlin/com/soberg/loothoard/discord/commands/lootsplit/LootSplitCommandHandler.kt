package com.soberg.loothoard.discord.commands.lootsplit

import com.soberg.loothoard.domain.lootsplit.LootSplitProcessor
import discord4j.core.`object`.command.ApplicationCommandInteractionOption
import discord4j.core.`object`.command.ApplicationCommandInteractionOptionValue
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import reactor.core.publisher.Mono

class LootSplitCommandHandler(
    private val lootSplitProcessor: LootSplitProcessor,
) {

    fun handle(event: ChatInputInteractionEvent): Mono<Void> {
        if (event.commandName != LootSplitCommandFactory.CommandName) {
            return Mono.empty()
        }

        val numPlayers = event.getOption(LootSplitCommandFactory.NumPlayersOptionName)
            .flatMap(ApplicationCommandInteractionOption::getValue)
            .map(ApplicationCommandInteractionOptionValue::asLong)
            .orElse(-1L)
        val csv = event.getOption(LootSplitCommandFactory.LootOptionName)
            .flatMap(ApplicationCommandInteractionOption::getValue)
            .map(ApplicationCommandInteractionOptionValue::asString)
            .orElse("")

        return event.reply()
            .withEphemeral(true)
            .withContent(
                lootSplitProcessor.process(
                    numPlayers = numPlayers.toInt(),
                    lootPayload = csv,
                )
            )
    }

}