package com.uit.moneykeeper.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.uit.moneykeeper.transaction.components.IconEnum
import kotlin.math.roundToInt

data class LoaiGiaoDichModel(
    val id: Int,
    val ten: String,
    val loai: PhanLoai,
    val mauSac: Color,
    val icon: IconEnum
){
    constructor() : this(0, "", PhanLoai.Chi, Color.Black, IconEnum.Null)

}

enum class PhanLoai(val isChi: Boolean) {
    Thu(false),
    Chi(true)
}