package com.uit.moneykeeper.transaction.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class NewTransactionViewModel : ViewModel() {
    private val _date = MutableStateFlow(LocalDate.now())
    val date: StateFlow<LocalDate> = _date.asStateFlow()

    private val _amount = MutableStateFlow("")
    val amount: StateFlow<String> = _amount.asStateFlow()

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _selectedCatOptionText = MutableStateFlow("")
    val selectedCatOptionText: StateFlow<String> = _selectedCatOptionText.asStateFlow()

    private val _selectedWalletOptionText = MutableStateFlow("")
    val selectedWalletOptionText: StateFlow<String> = _selectedWalletOptionText.asStateFlow()

    private val _note = MutableStateFlow("")
    val note: StateFlow<String> = _note.asStateFlow()

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

    fun saveTransaction(navController: NavController, context: Context) {
        val db = Firebase.firestore

        db.collection("giaoDich")
            .orderBy("id", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .addOnSuccessListener { documents ->
                val highestId = if (documents.isEmpty) {
                    0
                } else {
                    documents.documents[0].getLong("id")?.toInt() ?: 0
                }

                val newId = highestId + 1

                val transactionMap = mapOf(
                    "id" to newId,
                    "ten" to name.value,
                    "soTien" to amount.value.toDouble(),
                    "ngayGiaoDich" to date.value.toString(),
                    "ghiChu" to note.value,
                    "loaiGiaoDich" to selectedCatOptionText.value,
                    "taiKhoan" to selectedWalletOptionText.value
                )

                db.collection("giaoDich")
                    .add(transactionMap)
                    .addOnSuccessListener { documentReference ->
                        println("DocumentSnapshot added with ID: ${documentReference.id}")
                        navController.popBackStack()
                        Toast.makeText(
                            context,
                            "Transaction added successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnFailureListener { e ->
                        println("Error adding document: $e")
                    }
            }
    }
}