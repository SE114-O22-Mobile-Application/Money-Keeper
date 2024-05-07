package com.uit.moneykeeper.transaction.viewmodel

import com.uit.moneykeeper.models.LoaiGiaoDichModel
import com.uit.moneykeeper.sample.GlobalObject

class CategoryDropdownViewModel {
    val listLoaiGiaoDich: List<LoaiGiaoDichModel> = GlobalObject.listLoaiGiaoDich.value
}