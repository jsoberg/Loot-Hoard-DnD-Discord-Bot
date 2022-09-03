package com.soberg.loothoard.discord

import com.soberg.loothoard.discord.commands.lootsplit.LootSplitCommandFactory
import com.soberg.loothoard.discord.commands.lootsplit.LootSplitCommandHandler
import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class BotCommandRegistry(
    private val client: GatewayDiscordClient,
    private val commandHandler: LootSplitCommandHandler,
) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        registerLootSplitCommand()
        registerLootSplitCommandHandler()
    }

    private fun registerLootSplitCommand() {
        val applicationId = client.restClient.applicationId.block()!!
        client.restClient.applicationService
            .createGlobalApplicationCommand(applicationId, LootSplitCommandFactory.create())
            .subscribe()
        registerLootSplitCommandHandler()
    }

    private fun registerLootSplitCommandHandler() {
        client.on(ChatInputInteractionEvent::class.java, commandHandler::handle)
            .then(client.onDisconnect())
            .block()
    }
}
