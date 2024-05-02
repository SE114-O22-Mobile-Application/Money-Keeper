package com.uit.moneykeeper.models
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

data class TaiKhoanModel(
    val mauSac: Color,
    val soDu: Double,
    val ten: String
) {
    var id: String by mutableStateOf("")

    constructor() : this(Color.Unspecified, 0.0, "")
}