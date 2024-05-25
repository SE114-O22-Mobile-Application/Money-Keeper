package com.uit.moneykeeper.models
import java.time.LocalDate

data class GiaoDichModel(
    val id: Int,
    val ngayGiaoDich: LocalDate,
    val soTien: Double,
    val ten: String,
    val loaiGiaoDich: LoaiGiaoDichModel,
    val vi: ViModel,
    val ghiChu: String
) {
    constructor() : this(0, LocalDate.now(), 0.0, "", LoaiGiaoDichModel(), ViModel(), "")
}