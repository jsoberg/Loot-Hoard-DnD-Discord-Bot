package com.soberg.lootsplit.domain

enum class Coin(
    val baseFactor: Float,
    val aliases: Set<String>,
) {
    Copper(
        baseFactor = 1f,
        aliases = setOf("c", "cp", "copper")
    ),

    /** 1sp = 10cp */
    Silver(
        baseFactor = 10f,
        aliases = setOf("s", "sp", "silver")
    ),

    /** 1ep = 5sp = 50cp */
    Electrum(
        baseFactor = 50f,
        aliases = setOf("e", "ep", "electrum")
    ),

    /** 1gp = 2ep = 10sp = 100cp */
    Gold(
        baseFactor = 100f,
        aliases = setOf("g", "gp", "gold")
    ),

    /** 1pp = 10gp = 20ep = 100sp = 1000cp */
    Platinum(
        baseFactor = 1000f,
        aliases = setOf("p", "pp", "platinum")
    );

    companion object {
        val Base = Copper
    }
}