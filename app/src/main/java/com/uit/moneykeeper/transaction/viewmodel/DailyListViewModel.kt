package com.uit.moneykeeper.transaction.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uit.moneykeeper.models.GiaoDichModel
import com.uit.moneykeeper.models.PhanLoai
import com.uit.moneykeeper.sample.GlobalObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar

class DailyListViewModel(list: List<GiaoDichModel>) : ViewModel() {

    private val _dailyItemList = MutableStateFlow<List<Pair<LocalDate, List<GiaoDichModel>>>>(emptyList())
    val dailyItemList: StateFlow<List<Pair<LocalDate, List<GiaoDichModel>>>> = _dailyItemList.asStateFlow()

    private val _sumIn = MutableStateFlow(0.0)
    val sumIn: StateFlow<Double> = _sumIn.asStateFlow()

    private val _sumOut = MutableStateFlow(0.0)
    val sumOut: StateFlow<Double> = _sumOut.asStateFlow()

    private val _sum = MutableStateFlow(0.0)
    val sum: StateFlow<Double> = _sum.asStateFlow()

    init {
        updateDailyItemList(list)
    }

    fun updateDailyItemList(giaoDichList: List<GiaoDichModel>) {
        val sortedGiaoDichList = giaoDichList.sortedByDescending { it.ngayGiaoDich }
        val groupedGiaoDichList = sortedGiaoDichList.groupBy { it.ngayGiaoDich }
        val dailyItemList = groupedGiaoDichList.map { (date, transactions) ->
            Pair(date, transactions)
        }

        _dailyItemList.value = dailyItemList

        val sumIn = giaoDichList.filter { it.loaiGiaoDich.loai == PhanLoai.Thu }.sumOf { it.soTien }
        _sumIn.value = sumIn

        val sumOut = giaoDichList.filter { it.loaiGiaoDich.loai == PhanLoai.Chi }.sumOf { it.soTien }
        _sumOut.value = sumOut

        _sum.value = sumIn - sumOut
    }
}

