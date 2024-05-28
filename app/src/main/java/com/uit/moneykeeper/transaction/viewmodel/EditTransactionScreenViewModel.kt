package com.uit.moneykeeper.transaction.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.uit.moneykeeper.models.LoaiGiaoDichModel
import com.uit.moneykeeper.models.ViModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class EditTransactionViewModel (Id: Int): ViewModel() {
    private val _id = MutableStateFlow(Id)
    val id: StateFlow<Int> = _id.asStateFlow()

    private val _date = MutableStateFlow(LocalDate.now())
    val date: StateFlow<LocalDate> = _date.asStateFlow()

    private val _amount = MutableStateFlow("")
    val amount: StateFlow<String> = _amount.asStateFlow()

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _category = MutableStateFlow<List<LoaiGiaoDichModel>>(emptyList())
    val category: StateFlow<List<LoaiGiaoDichModel>> = _category.asStateFlow()

    private val _categoryOptions = MutableStateFlow<List<String>>(emptyList())
    val categoryOptions: StateFlow<List<String>> = _categoryOptions.asStateFlow()

    private val _selectedCatOptionText = MutableStateFlow("")
    val selectedCatOptionText: StateFlow<String> = _selectedCatOptionText.asStateFlow()

    private val _wallet = MutableStateFlow<List<ViModel>>(emptyList())
    val wallet: StateFlow<List<ViModel>> = _wallet.asStateFlow()

    private val _walletOptions = MutableStateFlow<List<String>>(emptyList())
    val walletOptions: StateFlow<List<String>> = _walletOptions.asStateFlow()

    private val _selectedWalletOptionText = MutableStateFlow("")
    val selectedWalletOptionText: StateFlow<String> = _selectedWalletOptionText.asStateFlow()

    private val _note = MutableStateFlow("")
    val note: StateFlow<String> = _note.asStateFlow()

    private fun updateCategoryOptions() {
        _categoryOptions.value = _category.value.map { it.ten }
    }

    private fun updateWalletOptions() {
        _walletOptions.value = _wallet.value.map { it.ten }
    }

    init {
        getLoaiGiaoDich { categories ->
            _category.value = categories
            updateCategoryOptions()
        }
        getVi { wallets ->
            _wallet.value = wallets
            updateWalletOptions()
        }
    }

    fun setId(newId: Int) {
        _id.value = newId
    }

    fun setDate(newDate: LocalDate) {
        _date.value = newDate
    }

    fun setAmount(newAmount: String) {
        _amount.value = newAmount
    }

    fun setName(newName: String) {
        _name.value = newName
    }

    fun setSelectedCatOptionText(newSelectedCatOptionText: String) {
        _selectedCatOptionText.value = newSelectedCatOptionText
    }

    fun setSelectedWalletOptionText(newSelectedWalletOptionText: String) {
        _selectedWalletOptionText.value = newSelectedWalletOptionText
    }

    fun setNote(newNote: String) {
        _note.value = newNote
    }

    private fun getLoaiGiaoDich(onComplete: (List<LoaiGiaoDichModel>) -> Unit) {
        val db = Firebase.firestore
        db.collection("loaiGiaoDich").get()
            .addOnSuccessListener { snapshot ->
                val list = snapshot.documents.mapNotNull { document ->
                    document.toObject(LoaiGiaoDichModel::class.java)
                }
                onComplete(list)
            }
    }

    private fun getVi(onComplete: (List<ViModel>) -> Unit) {
        val db = Firebase.firestore
        db.collection("vi").get()
            .addOnSuccessListener { snapshot ->
                val list = snapshot.documents.mapNotNull { document ->
                    document.toObject(ViModel::class.java)
                }
                onComplete(list)
            }
    }
}