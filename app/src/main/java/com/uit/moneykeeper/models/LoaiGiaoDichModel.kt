package com.uit.moneykeeper.models
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

data class LoaiGiaoDichModel(
    val mauSac: Color,
    val ten: String,
    val loai: PhanLoai
) {
    var id: String by mutableStateOf("")

    constructor() : this(Color.Unspecified, "", PhanLoai.Chi)
}

enum class PhanLoai(val isChi: Boolean) {
    Thu(false),
    Chi(true)
}