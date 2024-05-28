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

class TransactionDetailViewModel : ViewModel() {
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

//    fun saveTransaction(navController: NavController, context: Context) {
//        val db = Firebase.firestore
//
//        db.collection("giaoDich")
//            .orderBy("id", Query.Direction.DESCENDING)
//            .limit(1)
//            .get()
//            .addOnSuccessListener { documents ->
//                val highestId = if (documents.isEmpty) {
//                    0
//                } else {
//                    documents.documents[0].getLong("id")?.toInt() ?: 0
//                }
//                val newId = highestId + 1
//
//                val selectedCategory =
//                    _category.value.firstOrNull { it.ten.equals(_selectedCatOptionText.value, ignoreCase = true)}
//                val selectedWallet =
//                    _wallet.value.firstOrNull { it.ten.equals(_selectedWalletOptionText.value, ignoreCase = true)}
//
//
//                val transactionMap = mapOf(
//                    "id" to newId,
//                    "ten" to name.value,
//                    "soTien" to amount.value.toDouble(),
//                    "ngayGiaoDich" to GlobalFunction.convertLocalDateToTimestamp(date.value),
//                    "ghiChu" to note.value,
//                    "loaiGiaoDich" to selectedCategory?.let {
//                        mapOf(
//                            "id" to it.id,
//                            "ten" to it.ten,
//                            "loai" to it.loai.name,
//                            "mauSac" to it.mauSac.toString(),
//                            "icon" to it.icon.name
//                        )
//                    },
//                    "vi" to selectedWallet?.let {
//                        mapOf(
//                            "id" to it.id,
//                            "ten" to it.ten,
//                            "soDu" to it.soDu
//                        )
//                    }
//                )
//
//                db.collection("giaoDich")
//                    .add(transactionMap)
//                    .addOnSuccessListener { documentReference ->
//                        println("DocumentSnapshot added with ID: ${documentReference.id}")
//                        navController.popBackStack()
//                        Toast.makeText(
//                            context,
//                            "Transaction added successfully!",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                    .addOnFailureListener { e ->
//                        println("Error adding document: $e")
//                    }
//            }
//    }
}