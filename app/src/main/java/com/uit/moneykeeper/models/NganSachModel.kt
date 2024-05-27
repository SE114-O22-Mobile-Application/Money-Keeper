package com.uit.moneykeeper.models
import java.time.LocalDate

data class NganSachModel(
    val id: Int,
    val thoiGian: LocalDate,
    val TongTien: Double
) {
    constructor() : this(0, LocalDate.now(), 0.0)
}