package com.soberg.loothoard

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "discord")
open class DiscordProperties {

    var botToken: String? = null

}