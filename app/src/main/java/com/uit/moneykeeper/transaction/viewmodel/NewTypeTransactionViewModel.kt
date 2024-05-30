package com.uit.moneykeeper.transaction.viewmodel
import com.uit.moneykeeper.transaction.components.LoaiGiaoDichModel
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.uit.moneykeeper.transaction.components.Color
import com.uit.moneykeeper.transaction.components.IconEnum
import com.uit.moneykeeper.transaction.components.PhanLoai
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NewTypeTransactionViewModel : ViewModel() {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _selectedIcon = MutableStateFlow(IconEnum.Null)
    val selectedIcon: StateFlow<IconEnum> = _selectedIcon.asStateFlow()

    private val _transactionType = MutableStateFlow("")
    val transactionType: StateFlow<String> = _transactionType.asStateFlow()

    private val _color = MutableStateFlow("")
    val color: StateFlow<String> = _color.asStateFlow()

    fun setName(newName: String) {
        _name.value = newName
    }

    fun setSelectedIcon(newIcon: IconEnum) {
        _selectedIcon.value = newIcon
    }

    fun setTransactionType(newType: String) {
        _transactionType.value = newType
    }

    fun setColor(newColor: String) {
        _color.value = newColor
    }

    fun saveNewTransactionType(navController: NavController, context: Context) {
        val db = Firebase.firestore

        db.collection("loaiGiaoDich")
            .orderBy("id", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .addOnSuccessListener { documents ->
                val highestId = if (documents.isEmpty) {
                    0
                } else {
                    documents.documents[0].getLong("id")?.toInt() ?: 0
                }
                val newId = highestId + 1

                val transactionType = if (_transactionType.value.equals("CHI", ignoreCase = true)) {
                    PhanLoai.CHI
                } else {
                    PhanLoai.THU
                }

                val newTransactionType = LoaiGiaoDichModel(
                    id = newId,
                    ten = name.value,
                    loai = transactionType,
                    mauSac = Color(color.value),
                    icon = selectedIcon.value
                )

                db.collection("loaiGiaoDich")
                    .add(newTransactionType)
                    .addOnSuccessListener { documentReference ->
                        println("DocumentSnapshot added with ID: ${documentReference.id}")
                        navController.popBackStack()
                        Toast.makeText(
                            context,
                            "Transaction type added successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnFailureListener { e ->
                        println("Error adding document: $e")
                    }
            }
    }
}
