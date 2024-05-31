package com.uit.moneykeeper.models
import com.google.firebase.Timestamp
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

data class FirestoreGiaoDichModel(
    var id: Int = 0,
    var ngayGiaoDich: Timestamp = Timestamp.now(),
    var soTien: Double = 0.0,
    var ten: String = "",
    var loaiGiaoDich: LoaiGiaoDichModel = LoaiGiaoDichModel(),
    var vi: ViModel = ViModel(),
    var ghiChu: String = ""
)