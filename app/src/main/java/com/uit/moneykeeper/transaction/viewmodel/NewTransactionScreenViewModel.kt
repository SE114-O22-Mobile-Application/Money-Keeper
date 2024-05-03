package com.uit.moneykeeper.transaction.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class NewTransactionViewModel : ViewModel() {
    private val _date = MutableStateFlow(LocalDate.now())
    val date: StateFlow<LocalDate> = _date.asStateFlow()

    private val _amount = MutableStateFlow("")
    val amount: StateFlow<String> = _amount.asStateFlow()

    private val _category = MutableStateFlow("")
    val category: StateFlow<String> = _category.asStateFlow()

    private val _wallet = MutableStateFlow("")
    val wallet: StateFlow<String> = _wallet.asStateFlow()

    private val _note = MutableStateFlow("")
    val note: StateFlow<String> = _note.asStateFlow()

    fun setDate(newDate: LocalDate) {
        _date.value = newDate
    }

    fun setAmount(newAmount: String) {
        _amount.value = newAmount
    }

    fun setCategory(newCategory: String) {
        _category.value = newCategory
    }

    fun setWallet(newWallet: String) {
        _wallet.value = newWallet
    }

    fun setNote(newNote: String) {
        _note.value = newNote
    }
}