package com.soberg.lootsplit.command

import discord4j.core.GatewayDiscordClient

object LootSplitCommandRegistry {

    /** Registers the Loot Split command. */
    fun register(client: GatewayDiscordClient) {
        val applicationId = client.restClient.applicationId.block()!!
        client.restClient.applicationService
            .createGlobalApplicationCommand(applicationId, LootSplitCommand.create())
            .subscribe()
    }
}
