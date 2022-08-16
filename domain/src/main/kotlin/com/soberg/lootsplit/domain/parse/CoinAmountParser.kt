package com.soberg.lootsplit.domain.parse

import com.soberg.lootsplit.domain.Coin
import com.soberg.lootsplit.domain.CoinAmount
import java.util.*

internal class CoinAmountParser {

    companion object {
        private val SkipCharacters = listOf(' ', '_')
    }

    fun parse(value: String): Result<CoinAmount> = runCatching {
        val trimmedValue = value.trim()
        val charStack = Stack<Char>()

        var coin: Coin? = null
        for (i in trimmedValue.indices.reversed()) {
            val char = trimmedValue[i]
            when {
                (char in SkipCharacters) -> continue
                (char.isDigit() && coin == null) -> {
                    coin = parseCoinFromStack(charStack)
                    if (coin == null) error("Cannot parse coin name from \"$value\"")
                    charStack.push(char)
                }
                else -> charStack.push(char)
            }
        }

        val amount = parseAmountFromStack(charStack)
        val verifiedCoin = coin ?: error("Unable to parse \"$value\"")
        CoinAmount(amount, verifiedCoin)
    }

    private fun parseCoinFromStack(charStack: Stack<Char>): Coin? {
        val coinString = parseStringFromStack(charStack)
        return Coin.fromString(coinString)
    }

    private fun parseStringFromStack(charStack: Stack<Char>): String {
        val coinBuilder = StringBuilder()
        while (charStack.isNotEmpty()) {
            coinBuilder.append(charStack.pop())
        }
        return coinBuilder.toString()
    }

    private fun parseAmountFromStack(charStack: Stack<Char>): Long {
        val amountString = parseStringFromStack(charStack)
        return try {
            amountString.toLong()
        } catch (e: NumberFormatException) {
            error("Invalid format for amount value \"$amountString\"")
        }
    }
}

