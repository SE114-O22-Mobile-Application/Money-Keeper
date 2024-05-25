package com.uit.moneykeeper.transaction.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar

class TransactionViewModel : ViewModel() {
    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()

    private val _selectedYear = MutableStateFlow(Calendar.getInstance().get(Calendar.YEAR))
    val selectedYear: StateFlow<Int> = _selectedYear.asStateFlow()

    fun changeTab(index: Int) {
        _selectedTabIndex.value = index
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

