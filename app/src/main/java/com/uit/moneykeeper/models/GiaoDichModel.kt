package com.uit.moneykeeper.models
import java.time.LocalDate

data class GiaoDichModel(
    val ten: String,
    val soTien: Double,
    val ngayGiaoDich: LocalDate,
    val ghiChu: String,
    val loaiGiaoDich: LoaiGiaoDichModel,
    val taiKhoan: ViModel,
    val id: Int
) {
    constructor() : this("",0.0, LocalDate.now(), "", LoaiGiaoDichModel(), ViModel(), 0)
}