package com.soberg.loothoard

import com.soberg.loothoard.discord.DiscordProperties
import discord4j.core.DiscordClientBuilder
import discord4j.core.GatewayDiscordClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean


@SpringBootApplication
@EnableConfigurationProperties(DiscordProperties::class)
class BotApplication {

    @Autowired
    lateinit var discordProperties: DiscordProperties

    @Bean
    fun gatewayDiscordClient(): GatewayDiscordClient {
        val token = discordProperties.botToken
            ?: error("Discord bot token not provided, stopping...")
        return DiscordClientBuilder.create(token)
            .build()
            .login()
            .block()!!
    }
}

fun main() {
    SpringApplicationBuilder(BotApplication::class.java)
        .build()
        .run()
}