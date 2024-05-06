package com.uit.moneykeeper.transaction.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.uit.moneykeeper.models.GiaoDichModel
import com.uit.moneykeeper.models.PhanLoai
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class DailyItemViewModel(
    val date: LocalDate,
    val todayList: List<GiaoDichModel>
) {
    val currentIn: Double
        get() = todayList.filter { it.loaiGiaoDich.loai == PhanLoai.Thu }.sumOf { it.soTien }

    val currentOut: Double
        get() = todayList.filter { it.loaiGiaoDich.loai == PhanLoai.Chi }.sumOf { it.soTien }
}