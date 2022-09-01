# Loot Hoard

Loot Hoard is a Discord bot that can automatically split loot amongst any number of players in a
D&amp;D game, based off of the coins and values from traditional D&amp;D rules.

Once added to a Discord server, users can trigger the `/split` command with two parameters:
- `players` -> The number of players to split the loot for (e.g. `4`)
- `loot` -> The actual loot you want split, in a comma separated list (e.g. `10p,20g,40e,80s,160c`)
    - See the [Coin](https://github.com/jsoberg/Loot-Split-Discord-Bot/blob/main/domain/src/main/kotlin/com/soberg/lootsplit/domain/Coin.kt) class for the various identifiers that can be used for different coins. As an example, `10 Gold`, `10g`, and `10gp` will all be recognized as 10 Gold coins.

The bot will then respond with a message indicating the calculated loot for each player, and any leftover (if the loot couldn't be evenly split).

`/split 4 10p,20g,40e,80s,160c` will trigger the response:
```
Splitting 10 Platinum, 20 Gold, 40 Electrum, 80 Silver, and 160 Copper amongst 4 players...
Each player receives 37 Gold, and 4 Silver.
```

Currently, loot is rounded to the nearest value in Gold.

**Release 1.0 TBD**
