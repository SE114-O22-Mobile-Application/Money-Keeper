package com.uit.moneykeeper.transaction.components

import com.uit.moneykeeper.transaction.components.IconEnum

enum class PhanLoai {
    CHI, THU // Example categories
}

data class Color(val value: String)
data class LoaiGiaoDichModel(
    val id: Int,
    val ten: String,
    val loai: PhanLoai,
    val mauSac: Color,
    val icon: IconEnum
)