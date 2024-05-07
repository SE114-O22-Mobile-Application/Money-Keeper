package com.uit.moneykeeper.models
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.uit.moneykeeper.transaction.components.IconEnum

data class LoaiGiaoDichModel(
    val mauSac: Color,
    val ten: String,
    val loai: PhanLoai,
    val icon: IconEnum,
    val id: Int
) {
    constructor() : this(Color.Unspecified, "", PhanLoai.Chi, IconEnum.Null, 0)
}

enum class PhanLoai(val isChi: Boolean) {
    Thu(false),
    Chi(true)
}