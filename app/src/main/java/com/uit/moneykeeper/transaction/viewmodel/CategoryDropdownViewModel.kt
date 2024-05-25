package com.uit.moneykeeper.transaction.viewmodel

import com.uit.moneykeeper.models.LoaiGiaoDichModel
import com.uit.moneykeeper.global.GlobalObject

class CategoryDropdownViewModel {
    val listLoaiGiaoDich: List<LoaiGiaoDichModel> = GlobalObject.listLoaiGiaoDich.value
}