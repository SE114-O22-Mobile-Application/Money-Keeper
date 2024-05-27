package com.uit.moneykeeper.budget.viewmodel

import java.time.LocalDate
import androidx.compose.runtime.mutableStateOf
import com.uit.moneykeeper.models.ViModel

class NewBudgetViewModel {
    private val _thoiGian = mutableStateOf(LocalDate.now())

    fun setTime(time: LocalDate) {
        _thoiGian.value = time
    }

    fun getTime(): LocalDate {
        return _thoiGian.value
    }
}