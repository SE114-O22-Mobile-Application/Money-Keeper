package com.uit.moneykeeper.transaction.viewmodel

import androidx.lifecycle.ViewModel
import com.uit.moneykeeper.models.GiaoDichModel
import com.uit.moneykeeper.global.GlobalObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

//class MonthlyTabViewModel(month: Int? = null, year: Int? = null) : ViewModel() {
//    private val _selectedMonth = MutableStateFlow(month?.let { year?.let { it1 -> LocalDate.of(it1, it, 1) } } ?: LocalDate.now())

class MonthlyTabViewModel : ViewModel() {
    private val _selectedMonth = MutableStateFlow(LocalDate.now())
    val selectedMonth: StateFlow<LocalDate> = _selectedMonth.asStateFlow()

    private val _thisMonthList = MutableStateFlow<List<GiaoDichModel>>(emptyList())
    val thisMonthList: StateFlow<List<GiaoDichModel>> = _thisMonthList.asStateFlow()

    init {
        updateList(GlobalObject.listGiaoDich.value.filter { it.ngayGiaoDich.month == selectedMonth.value.month && it.ngayGiaoDich.year == selectedMonth.value.year })
    }

    fun updateList(list: List<GiaoDichModel>) {
        _thisMonthList.value = list
    }

    fun changeMonth(month: LocalDate) {
        _selectedMonth.value = month
        updateList(GlobalObject.listGiaoDich.value.filter { it.ngayGiaoDich.month == month.month && it.ngayGiaoDich.year == month.year })
    }

    fun nextMonth() {
        changeMonth(selectedMonth.value.plusMonths(1))
    }

    fun previousMonth() {
        changeMonth(selectedMonth.value.minusMonths(1))
    }
}