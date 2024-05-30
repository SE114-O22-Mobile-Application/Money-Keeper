package com.uit.moneykeeper.transaction.viewmodel

import androidx.lifecycle.ViewModel
import com.uit.moneykeeper.global.GlobalObject
import com.uit.moneykeeper.models.GiaoDichModel
import com.uit.moneykeeper.models.LoaiGiaoDichModel
import com.uit.moneykeeper.models.PhanLoai
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar

class YearlyTabViewModel : ViewModel()  {
    private val _selectedYear = MutableStateFlow(Calendar.getInstance().get(Calendar.YEAR))
    val selectedYear: StateFlow<Int> = _selectedYear.asStateFlow()

    private val _thisYearList = MutableStateFlow<List<GiaoDichModel>>(emptyList())
    private val thisYearList: StateFlow<List<GiaoDichModel>> = _thisYearList.asStateFlow()

    private val _monthlyItemList = MutableStateFlow<List<Pair<Int, List<SumEachTypeItem>>>>(emptyList())
    val monthlyItemList: StateFlow<List<Pair<Int, List<SumEachTypeItem>>>> = _monthlyItemList.asStateFlow()

    private val _sumIn = MutableStateFlow(0.0)
    val sumIn: StateFlow<Double> = _sumIn.asStateFlow()

    private val _sumOut = MutableStateFlow(0.0)
    val sumOut: StateFlow<Double> = _sumOut.asStateFlow()

    private val _sum = MutableStateFlow(0.0)
    val sum: StateFlow<Double> = _sum.asStateFlow()

    init {
        updateList()
    }

    private fun updateList() {
        _thisYearList.value = GlobalObject.listGiaoDich.value.filter { it.ngayGiaoDich.year == selectedYear.value }
        updateMonthlyItemList()
    }

    private fun changeYear(year: Int) {
        _selectedYear.value = year
        updateList()
    }

    fun nextYear() {
        changeYear(selectedYear.value + 1)
    }

    fun previousYear() {
        changeYear(selectedYear.value - 1)
    }
    private fun updateMonthlyItemList() {
        val sortedGiaoDichList = thisYearList.value.sortedByDescending { it.ngayGiaoDich }

        val groupedByMonth = sortedGiaoDichList.groupBy { it.ngayGiaoDich.month.value }

        val monthlyItemList = groupedByMonth.map { (month, transactions) ->
            val groupedByLoaiGiaoDich = transactions.groupBy { it.loaiGiaoDich }
            val sumEachTypeItemList = groupedByLoaiGiaoDich.map { (loaiGiaoDich, transactions) ->
                val sum = transactions.sumOf { it.soTien }
                SumEachTypeItem(loaiGiaoDich, sum)
            }
            Pair(month, sumEachTypeItemList)
        }

        _monthlyItemList.value = monthlyItemList

        _sumIn.value = monthlyItemList.flatMap { it.second }.filter { it.loaiGiaoDich.loai == PhanLoai.Thu }.sumOf { it.sum }
        _sumOut.value = monthlyItemList.flatMap { it.second }.filter { it.loaiGiaoDich.loai == PhanLoai.Chi }.sumOf { it.sum }
        _sum.value = sumIn.value - _sumOut.value
    }
}

data class SumEachTypeItem(val loaiGiaoDich: LoaiGiaoDichModel, val sum: Double)