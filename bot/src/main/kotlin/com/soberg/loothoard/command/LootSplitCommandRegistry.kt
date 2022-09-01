package com.soberg.loothoard.command

import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class LootSplitCommandRegistry(
    private val client: GatewayDiscordClient,
    private val commandHandler: LootSplitCommandHandler,
) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        registerLootSplitCommand()
        registerCommandHandler()
    }

    private fun registerLootSplitCommand() {
        val applicationId = client.restClient.applicationId.block()!!
        client.restClient.applicationService
            .createGlobalApplicationCommand(applicationId, LootSplitCommand.create())
            .subscribe()
    }

    private fun registerCommandHandler() {
        client.on(ChatInputInteractionEvent::class.java, commandHandler::handle)
            .then(client.onDisconnect())
            .block()
    }
}
