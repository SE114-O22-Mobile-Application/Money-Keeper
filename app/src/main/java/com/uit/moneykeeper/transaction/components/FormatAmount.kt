package com.uit.moneykeeper.transaction.components

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Currency
import kotlin.math.roundToInt

object DoubleToStringConverter {
    @JvmStatic
    fun convert(value: Double): String {
        val format = DecimalFormat()
        format.maximumFractionDigits = 0
        format.decimalFormatSymbols = DecimalFormatSymbols.getInstance().apply {
            groupingSeparator = '.'
        }

        return format.format(value.roundToInt()).plus("đ")
    }
}