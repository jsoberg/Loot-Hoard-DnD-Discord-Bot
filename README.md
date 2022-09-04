# Loot Hoard

![](https://dcbadge.vercel.app/api/shield/1000943034202066984?bot=true&style=flat)  ![build and test status](https://github.com/jsoberg/Loot-Hoard-DnD-Discord-Bot/actions/workflows/gradle.yml/badge.svg?branch=main)  [![Maintainability](https://api.codeclimate.com/v1/badges/6c0494fce51d8e7d7830/maintainability)](https://codeclimate.com/github/jsoberg/Loot-Hoard-DnD-Discord-Bot/maintainability)  [![Test Coverage](https://api.codeclimate.com/v1/badges/6c0494fce51d8e7d7830/test_coverage)](https://codeclimate.com/github/jsoberg/Loot-Hoard-DnD-Discord-Bot/test_coverage)

Loot Hoard is a Discord bot that can automatically split loot amongst any number of players in a
D&amp;D game, based off of the coins and values from traditional D&amp;D rules. You can add it to your server now from [here](https://discord.com/api/oauth2/authorize?client_id=1000943034202066984&permissions=2048&scope=bot%20applications.commands)!

Once added to a Discord server, users can trigger the `/split` command with two parameters:
- `players` -> The number of players to split the loot for (e.g. `4`)
- `loot` -> The actual loot you want split, in a comma separated list (e.g. `10p,20g,40e,80s,160c`)
    - See the [Coin](https://github.com/jsoberg/Loot-Split-Discord-Bot/blob/main/domain/src/main/kotlin/com/soberg/loothoard/domain/Coin.kt) class for the various identifiers that can be used for different coins. As an example, `10 Gold`, `10g`, and `10gp` will all be recognized as 10 Gold coins.

The bot will then respond with a message indicating the calculated loot for each player, and any leftover (if the loot couldn't be evenly split).

`/split 4 10p,20g,40e,80s,160c` will trigger the response:
```
Splitting 10 Platinum, 20 Gold, 40 Electrum, 80 Silver, and 160 Copper amongst 4 players...
Each player receives 37 Gold, and 4 Silver.
```

Currently, loot is rounded to the nearest value in Gold.

## Contributions

This repository is open to contributions! Feel free to post in the discussions forum to talk about added functionality you'd like to see, or post an issue for any bugs that are found.

## Using the Bot

You can add my hosted version of Loot Hoard to your server [here](https://discord.com/api/oauth2/authorize?client_id=1000943034202066984&permissions=2048&scope=bot%20applications.commands)!

## Hosting the Bot

If you'd instead like to host a version of this bot yourself, you'll need to:
1. Create a new application for the bot in the [Discord Developer Portal](https://discord.com/developers/applications)

1. Download the jar from the [latest release](https://github.com/jsoberg/Loot-Hoard-DnD-Discord-Bot/releases/latest), **or** build from source yourself by running `./gradlew build` and getting the generated jar file in `bot/build/libs/` (Should look like `LootHoardDiscordBot-<version>.jar`)

1. Make sure that you have an `application.properties` file containing the bot's private token (can be found from the Discord application's page -> Settings -> Bot), like so:
```yaml
discord.botToken=<BOT PRIVATE TOKEN HERE>
```
Note that you **don't want to use String quotations ("") around the private token**. I'm not entirely sure why, but the token will be parsed without periods and some other symbols if String quotations are used.

1. Run the bot!
You'll need Java 11 to run the bot. Note that the bot will automatically look for the necessary `application.properties` file from the directory it's run in, or from a `config` subdirectory in the same spot. You can pass in the `-Dspring.config.location=<config directory>` flag **before** the -jar identifier to set the config location, if you're running from somewhere other than the directory that the jar is in (e.g. for running from `systemd` on boot).

My `systemd` service looks like the following:
```
[Unit]
Description=Run Loot Hoard Discord Bot
After=multi-user.target

[Service]
ExecStart=/bin/bash -c '/usr/bin/java -Dspring.config.location=/home/<username>/LootHoard/config/ -jar /home/<username>/LootHoard/*.jar'

[Install]
WantedBy=multi-user.target
```
Using the `*.jar` makes it easy to drop in new versions.
