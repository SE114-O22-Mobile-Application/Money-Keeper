@file:OptIn(ExperimentalMaterial3Api1::class)

package com.uit.moneykeeper.transaction.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.uit.moneykeeper.transaction.viewmodel.NewTransactionViewModel
import kotlinx.datetime.*
import androidx.compose.material3.ExperimentalMaterial3Api as ExperimentalMaterial3Api1

val currentDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date


@OptIn(ExperimentalMaterial3Api1::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

@Composable
fun NewTransactionScreen(navController: NavController, viewModel: NewTransactionViewModel) {
    val date by viewModel.date.collectAsState()
    val amount by viewModel.amount.collectAsState()
    val category by viewModel.category.collectAsState()
    val wallet by viewModel.wallet.collectAsState()
    val note by viewModel.note.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Thêm giao dịch",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (!navController.popBackStack().not()) {
                            navController.navigate("transaction")
                        }
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back to Transaction screen") } }
            )
        }
    )
    {
        Column(modifier = Modifier.padding(16.dp)) {
            //Date Selector
            TextField(value = amount, onValueChange = { newAmount -> viewModel.setAmount(newAmount) }, label = { Text("Ngày thực hiện giao dịch: ") })
            TextField(value = amount, onValueChange = { newAmount -> viewModel.setAmount(newAmount) }, label = { Text("Số tiền: ") })
            TextField(value = amount, onValueChange = { newAmount -> viewModel.setAmount(newAmount) }, label = { Text("Tên giao dịch: ") })
            TextField(value = amount, onValueChange = { newAmount -> viewModel.setAmount(newAmount) }, label = { Text("Loại giao dịch: ") })
            TextField(value = amount, onValueChange = { newAmount -> viewModel.setAmount(newAmount) }, label = { Text("Ví: ") })
            //DropdownMenu(items = listOf("Thể loại 1", "Thể loại 2"), selectedItem = category) { newCategory -> viewModel.setCategory(newCategory) }
            //DropdownMenu(items = listOf("Ví 1", "Ví 2"), selectedItem = wallet) { newWallet -> viewModel.setWallet(newWallet) }
            TextField(value = note, onValueChange = { newNote -> viewModel.setNote(newNote) }, label = { Text("Ghi chú") })
        }
    }
}

@Composable
fun DateSelector(selectedDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
   // Show a date picker when the button is clicked
}

@Composable
fun <T> DropdownMenu(items: List<T>, selectedItem: T, onItemSelect: (T) -> Unit) {
    // Show a dropdown menu when the button is clicked
}