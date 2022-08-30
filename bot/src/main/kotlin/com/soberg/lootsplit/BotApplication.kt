package com.soberg.lootsplit

import com.soberg.lootsplit.command.LootSplitCommandHandler
import discord4j.core.DiscordClientBuilder
import discord4j.core.GatewayDiscordClient
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Bean


@SpringBootApplication
open class BotApplication {

    @Bean
    open fun gatewayDiscordClient(args: ApplicationArguments): GatewayDiscordClient {
        // TODO: Identify how we want to store/retrieve the private token.
        val token = args.sourceArgs[0]
        return DiscordClientBuilder.create(token)
            .build()
            .login()
            .block()!!
    }

    @Bean
    open fun commandHandler(): LootSplitCommandHandler = LootSplitCommandHandler()
}

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Token required")
        return
    }

    SpringApplicationBuilder(BotApplication::class.java)
        .build()
        .run(*args)
}