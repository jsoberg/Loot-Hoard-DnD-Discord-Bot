package com.soberg.lootsplit

import com.soberg.lootsplit.command.LootSplitCommandHandler
import com.soberg.lootsplit.command.LootSplitCommandRegistry
import discord4j.core.DiscordClientBuilder
import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Token required")
        return
    }

    val token = args[0]
    val client = DiscordClientBuilder.create(token).build()
        .login()
        .block()!!
    registerLootSplitCommand(client)
}

private fun registerLootSplitCommand(client: GatewayDiscordClient) {
    val lootSplitCommandProcessor = LootSplitCommandHandler()
    LootSplitCommandRegistry.register(client)
    client.on(ChatInputInteractionEvent::class.java, lootSplitCommandProcessor::handle)
        .then(client.onDisconnect())
        .block()
}