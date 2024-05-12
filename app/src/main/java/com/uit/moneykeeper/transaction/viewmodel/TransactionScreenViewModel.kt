package com.uit.moneykeeper.transaction.viewmodel

import androidx.lifecycle.ViewModel
import com.uit.moneykeeper.models.GiaoDichModel
import com.uit.moneykeeper.sample.giaoDichList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.util.Calendar

class TransactionViewModel : ViewModel() {
    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()

    private val _selectedMonth = MutableStateFlow(LocalDate.now())
    val selectedMonth: StateFlow<LocalDate> = _selectedMonth.asStateFlow()

    private val _selectedYear = MutableStateFlow(Calendar.getInstance().get(Calendar.YEAR))
    val selectedYear: StateFlow<Int> = _selectedYear.asStateFlow()

    private val _listGiaoDich = MutableStateFlow<List<GiaoDichModel>>(emptyList())
    val listGiaoDich: StateFlow<List<GiaoDichModel>> = _listGiaoDich.asStateFlow()

    init {
        _listGiaoDich.value = giaoDichList
    }

    fun changeTab(index: Int) {
        _selectedTabIndex.value = index
    }

    fun changeMonth(month: LocalDate) {
        _selectedMonth.value = month
    }

    fun nextMonth() {
        changeMonth(selectedMonth.value.plusMonths(1))
    }

    fun previousMonth() {
        changeMonth(selectedMonth.value.minusMonths(1))
    }

    fun changeYear(year: Int) {
        _selectedYear.value = year
    }

    fun nextYear() {
        changeYear(selectedYear.value + 1)
    }

    fun previousYear() {
        changeYear(selectedYear.value - 1)
    }
}

