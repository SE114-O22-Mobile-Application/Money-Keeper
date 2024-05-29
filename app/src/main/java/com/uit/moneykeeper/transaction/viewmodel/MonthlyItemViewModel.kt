package com.uit.moneykeeper.transaction.viewmodel

import com.uit.moneykeeper.models.PhanLoai
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

class MonthlyItemViewModel (private val month: Int, val itemList: List<SumEachTypeItem>) {
    val monthName: String
        get() = if (month in 1..12) {
            Month.of(month).getDisplayName(TextStyle.FULL, Locale.forLanguageTag("vi")).replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.forLanguageTag("vi")) else it.toString() }
        } else {
            "Invalid month number"
        }

    val currentIn: Double
        get() = itemList.filter { it.loaiGiaoDich.loai == PhanLoai.Thu }.sumOf { it.sum }

    val currentOut: Double
        get() = itemList.filter { it.loaiGiaoDich.loai == PhanLoai.Chi }.sumOf { it.sum }
}