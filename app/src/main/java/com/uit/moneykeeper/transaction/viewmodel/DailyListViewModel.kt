package com.uit.moneykeeper.transaction.viewmodel

import androidx.lifecycle.ViewModel
import com.uit.moneykeeper.models.GiaoDichModel
import com.uit.moneykeeper.models.PhanLoai
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class DailyListViewModel(private val giaoDichList: List<GiaoDichModel>) : ViewModel() {

    private val _dailyItemList = MutableStateFlow<List<Pair<LocalDate, List<GiaoDichModel>>>>(emptyList())
    val dailyItemList: StateFlow<List<Pair<LocalDate, List<GiaoDichModel>>>> = _dailyItemList.asStateFlow()

    private val _sumIn = MutableStateFlow(0.0)
    val sumIn: StateFlow<Double> = _sumIn.asStateFlow()

    private val _sumOut = MutableStateFlow(0.0)
    val sumOut: StateFlow<Double> = _sumOut.asStateFlow()

    private val _sum = MutableStateFlow(0.0)
    val sum: StateFlow<Double> = _sum.asStateFlow()

    private val _idWallet = MutableStateFlow(0)
    val idWallet: StateFlow<Int> = _idWallet.asStateFlow()

    private val _idCategory = MutableStateFlow(0)
    val idCategory: StateFlow<Int> = _idCategory.asStateFlow()

    init {
        updateDailyItemList()
    }

    fun updateWallet(id: Int) {
        _idWallet.value = id
        updateDailyItemList()
    }

    fun updateCategory(id: Int) {
        _idCategory.value = id
        updateDailyItemList()
    }

    fun updateDailyItemList() {
        val filterCategory = if (idCategory.value == 0) {
            giaoDichList
        } else if (idCategory.value == -1) {
            giaoDichList.filter { !it.loaiGiaoDich.loai.isChi }
        } else if (idCategory.value == -2) {
            giaoDichList.filter { it.loaiGiaoDich.loai.isChi }
        } else {
            giaoDichList.filter { it.loaiGiaoDich.id == idCategory.value }
        }

        val filterWallet = if (idWallet.value != 0) {
            filterCategory.filter { it.vi.id == idWallet.value }
        } else {
            filterCategory
        }

        val sortedGiaoDichList = filterWallet.sortedByDescending { it.ngayGiaoDich }
        val groupedGiaoDichList = sortedGiaoDichList.groupBy { it.ngayGiaoDich }
        val dailyItemList = groupedGiaoDichList.map { (date, transactions) ->
            Pair(date, transactions)
        }

        _dailyItemList.value = dailyItemList

        val sumIn = sortedGiaoDichList.filter { it.loaiGiaoDich.loai == PhanLoai.Thu }.sumOf { it.soTien }
        _sumIn.value = sumIn

        val sumOut = sortedGiaoDichList.filter { it.loaiGiaoDich.loai == PhanLoai.Chi }.sumOf { it.soTien }
        _sumOut.value = sumOut

        _sum.value = sumIn - sumOut
    }
}

