package com.soberg.loothoard.discord.commands.lootsplit

import com.soberg.loothoard.discord.commands.getLongOrElse
import com.soberg.loothoard.discord.commands.getStringOrElse
import com.soberg.loothoard.domain.lootsplit.LootSplitProcessor
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import reactor.core.publisher.Mono

class LootSplitCommandHandler(
    private val lootSplitProcessor: LootSplitProcessor,
) {

    fun handle(event: ChatInputInteractionEvent): Mono<Void> {
        if (event.commandName != LootSplitCommandFactory.CommandName) {
            return Mono.empty()
        }

        val numPlayers = event.getLongOrElse(LootSplitCommandFactory.NumPlayersOptionName, -1L)
        val csv = event.getStringOrElse(LootSplitCommandFactory.LootOptionName, "")
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