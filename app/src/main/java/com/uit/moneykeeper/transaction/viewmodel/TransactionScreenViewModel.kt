package com.uit.moneykeeper.transaction.viewmodel

import androidx.lifecycle.ViewModel
import com.uit.moneykeeper.global.GlobalFunction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar

class TransactionViewModel : ViewModel() {
    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()
    
    init {
        GlobalFunction.updateListGiaoDich()
    }
    
    fun changeTab(index: Int) {
        _selectedTabIndex.value = index
    }
}

