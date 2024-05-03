package com.uit.moneykeeper.transaction.viewmodel

import androidx.lifecycle.ViewModel
import com.uit.moneykeeper.models.GiaoDichModel
import com.uit.moneykeeper.models.PhanLoai
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.util.Calendar

class DailyListViewModel(private val giaoDichList: List<GiaoDichModel>, inputMonth: LocalDate) : ViewModel() {
    private val _currentDate = MutableStateFlow(inputMonth)
    val currentDate: StateFlow<LocalDate> = _currentDate.asStateFlow()

    private val _currentIn = MutableStateFlow(0.0)
    val currentIn: StateFlow<Double> = _currentIn.asStateFlow()

    private val _currentOut = MutableStateFlow(0.0)
    val currentOut: StateFlow<Double> = _currentOut.asStateFlow()

    private val _currentSum = MutableStateFlow(0.0)
    val currentSum: StateFlow<Double> = _currentSum.asStateFlow()

    private val _inputMonth = MutableStateFlow(inputMonth)
    val inputMonth: StateFlow<LocalDate> = _inputMonth.asStateFlow()

    var outputList: List<GiaoDichModel> = giaoDichList

    init {
        outputList = outputList
            .filter { it.ngayGiaoDich == inputMonth }
            .sortedBy { it.ngayGiaoDich }
    }

    fun updateCurrentDate(newDate: LocalDate) {
        if (newDate != currentDate.value) {
            _currentDate.value = newDate
        }
    }
}
