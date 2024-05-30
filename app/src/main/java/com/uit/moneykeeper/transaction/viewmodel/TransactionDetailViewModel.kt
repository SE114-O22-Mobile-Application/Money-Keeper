package com.uit.moneykeeper.transaction.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.uit.moneykeeper.global.GlobalFunction
import com.uit.moneykeeper.models.FirestoreGiaoDichModel
import com.uit.moneykeeper.models.GiaoDichModel
import com.uit.moneykeeper.models.LoaiGiaoDichModel
import com.uit.moneykeeper.models.ViModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class TransactionDetailViewModel(Id: Int) : ViewModel() {
    private val _giaoDich = MutableStateFlow<GiaoDichModel?>(null)
    val giaoDich: StateFlow<GiaoDichModel?> = _giaoDich.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

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
    viewModelScope.launch {
        getLoaiGiaoDich { categories ->
            _category.value = categories
            updateCategoryOptions()
        }
        getVi { wallets ->
            _wallet.value = wallets
            updateWalletOptions()
        }
        getGiaoDich(Id)
    }
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

    private fun getGiaoDich(Id: Int) {
        _isLoading.value = true // Set loading state to true at the start of data fetch
        val db = FirebaseFirestore.getInstance()
        db.collection("giaoDich").whereEqualTo("id", Id)
            .get()
            .addOnSuccessListener { document ->
                if (!document.isEmpty) {
                    val firestoreGiaoDichData = document.documents[0].toObject(FirestoreGiaoDichModel::class.java)
                    if (firestoreGiaoDichData != null) {
                        val ngayGiaoDich = GlobalFunction.convertTimestampToLocalDate(firestoreGiaoDichData.ngayGiaoDich)
                        val giaoDichData = GiaoDichModel(
                            firestoreGiaoDichData.id,
                            ngayGiaoDich,
                            firestoreGiaoDichData.soTien,
                            firestoreGiaoDichData.ten,
                            firestoreGiaoDichData.loaiGiaoDich,
                            firestoreGiaoDichData.vi,
                            firestoreGiaoDichData.ghiChu
                        )
                        _giaoDich.value = giaoDichData
                    } else {
                        Log.e("TransactionDetailViewModel", "GiaoDichData is null!")
                    }
                } else {
                    Log.e("TransactionDetailViewModel", "Document with id: $Id does not exist !")
                }
                _isLoading.value = false // Set loading state to false after data fetch is successful
            }
            .addOnFailureListener { exception ->
                Log.e("TransactionDetailViewModel", "Error getting document: ", exception)
                _isLoading.value = false // Set loading state to false even if data fetch fails
            }
    }

    fun deleteGiaoDich(id: Int?, context: Context) {
        val db = FirebaseFirestore.getInstance()
        if (id == null) {
            Log.w("TransactionDetailViewModel", "Cannot delete document: id is null")
            return
        }
        Log.d("TransactionDetailViewModel", "Attempting to delete document with id: $id")
        db.collection("giaoDich").whereEqualTo("id", id)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Log.w("TransactionDetailViewModel", "No document found with id: $id")
                } else {
                    documents.documents[0].reference
                        .delete()
                        .addOnSuccessListener {
                            Toast.makeText(context, "Transaction successfully deleted!", Toast.LENGTH_SHORT).show()
                            Log.d("TransactionDetailViewModel", "Transaction successfully deleted!")
                        }
                        .addOnFailureListener { e ->
                            Log.w("TransactionDetailViewModel", "Error deleting document", e)
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.w("TransactionDetailViewModel", "Error finding document", e)
            }
    }
}