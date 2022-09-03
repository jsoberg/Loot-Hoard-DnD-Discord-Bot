package com.soberg.loothoard.discord

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "discord")
class DiscordProperties {

    var botToken: String? = null

}