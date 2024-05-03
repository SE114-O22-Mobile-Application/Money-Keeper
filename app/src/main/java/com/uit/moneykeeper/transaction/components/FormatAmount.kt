package com.uit.moneykeeper.transaction.components

import java.util.Locale

object DoubleToStringConverter {

    @JvmStatic
    fun convert(value: Double): String {
        val amountString = value.toString().replace(".", ",")
        val parts = amountString.split(",")

        val integerPart = parts[0].reversed().chunked(3).joinToString(".").reversed()
        val decimalPart = parts.getOrNull(1) ?: ""
        val trimmedDecimalPart = decimalPart.trimEnd('0')
        val roundedDecimalPart = if (trimmedDecimalPart.isNotEmpty()) {
            String.format(Locale("vi"), "%.3f", trimmedDecimalPart.toDouble()).replace(".", "").trimEnd('0')
        } else {
            ""
        }

        return "$integerPart,${roundedDecimalPart.trimEnd(',')}đ"
    }
}