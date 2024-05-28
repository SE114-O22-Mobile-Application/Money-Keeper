package com.uit.moneykeeper.models

import java.time.LocalDate

data class CTNganSachModel(
    val id: Int,
    val NganSach: NganSachModel,
    val TenNS: String,
    val LoaiNS: LoaiGiaoDichModel,
    val SoTien: Double
) {
    constructor() : this(0, NganSachModel(),"TenNS" ,LoaiGiaoDichModel(), 0.0);
}