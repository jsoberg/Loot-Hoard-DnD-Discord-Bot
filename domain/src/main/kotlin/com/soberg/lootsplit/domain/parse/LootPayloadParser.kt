package com.soberg.lootsplit.domain.parse

import com.soberg.lootsplit.domain.LootSummation

internal class LootPayloadParser constructor(
    private val coinAmountParser: CoinAmountParser,
) {

    fun parse(payload: String): Result<LootSummation> = runCatching {
        val summation = LootSummation.Builder()
        payload.split(",").forEach { amountString ->
            if (amountString.isNotEmpty()) {
                val amount = coinAmountParser.parse(amountString)
                    .getOrThrow()
                summation.add(amount)
            }
        }
        summation.build()
    }
}