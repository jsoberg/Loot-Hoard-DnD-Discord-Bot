package com.soberg.loothoard.discord

import com.soberg.loothoard.discord.commands.lootsplit.LootSplitCommandHandler
import com.soberg.loothoard.domain.lootsplit.LootSplitProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/** Stores [Bean]s for all of our command handlers/processors/etc. */
@Configuration
class BotCommandsConfiguration {

    @Bean
    fun lootSplitProcessor(): LootSplitProcessor = LootSplitProcessor()

    @Bean
    fun lootSplitHandler(processor: LootSplitProcessor): LootSplitCommandHandler =
        LootSplitCommandHandler(processor)
}