package com.uit.moneykeeper.sample

import com.uit.moneykeeper.models.GiaoDichModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object GlobalObject {
    private val _listGiaoDich = MutableStateFlow<List<GiaoDichModel>>(emptyList())
    val listGiaoDich: StateFlow<List<GiaoDichModel>> = _listGiaoDich.asStateFlow()

    init {
        updateList(giaoDichList)
    }

    fun updateList(list: List<GiaoDichModel>) {
        _listGiaoDich.value = list
    }
}