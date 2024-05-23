package com.uit.moneykeeper.transaction.views

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.uit.moneykeeper.components.MonthPicker
import com.uit.moneykeeper.components.WeekPicker
import com.uit.moneykeeper.transaction.viewmodel.NewTransactionViewModel
import kotlinx.datetime.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

@Composable
fun NewTransactionScreen(navController: NavController, viewModel: NewTransactionViewModel) {
    val date by viewModel.date.collectAsState()
    val amount by viewModel.amount.collectAsState()
    val name by viewModel.name.collectAsState()
    val categoryOptions = listOf("Thể loại 1", "Thể loại 2")
    var selectedCatOptionText by remember { mutableStateOf(categoryOptions[0]) }
    val walletOptions = listOf("Ví 1", "Ví 2")
    var selectedWalletOptionText by remember { mutableStateOf(walletOptions[0]) }

    val note by viewModel.note.collectAsState()

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val dateString = date.format(formatter)
    val focusRequester = remember { FocusRequester() }
    val calendarState = rememberUseCaseState()

    var expandedCat by remember { mutableStateOf(false) }
    var expandedWallet by remember { mutableStateOf(false) }


    CalendarDialog(
        state = calendarState,
        selection = CalendarSelection.Date { selectedDate ->
            Log.d("Selected date", "$selectedDate")
            viewModel.setDate(selectedDate)
            calendarState.hide()
            focusRequester.requestFocus()
        }
    )

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
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back to Transaction screen") } }
            )
        },
        modifier = Modifier.fillMaxSize()
    )

    {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(top = 60.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
        )
        {
            TextField(
                value = dateString,
                onValueChange = { /* Do nothing as we don't want to allow manual date input */ },
                label = { Text("Ngày thực hiện giao dịch: ") },
                readOnly = true,
                modifier = Modifier
                    .background(Color.White)
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState: FocusState ->
                        if (focusState.isFocused) {
                            calendarState.show()
                        }
                    }
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    disabledIndicatorColor = Color.White
                )
            )
            TextField(
                value = amount,
                onValueChange = { newAmount -> viewModel.setAmount(newAmount) },
                label = { Text("Số tiền: ") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    disabledIndicatorColor = Color.White
                )
            )
            TextField(
                value = name,
                onValueChange = { newName -> viewModel.setName(newName) },
                label = { Text("Tên giao dịch: ") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    disabledIndicatorColor = Color.White
                )
            )
            ExposedDropdownMenuBox(
                expanded = expandedCat,
                onExpandedChange = { expandedCat = !expandedCat }
            ){
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                            .background(Color.White),
                    readOnly = true,
                    value = selectedCatOptionText,
                    onValueChange = {},
                    label = { Text("Loại giao dịch") },
                    trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCat)},
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White,
                        disabledIndicatorColor = Color.White
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expandedCat,
                        onDismissRequest = { expandedCat = false }
                    ){
                        categoryOptions.forEach {selectedcatopt ->
                            DropdownMenuItem(
                                text={Text(selectedcatopt) },
                                onClick = {
                                    selectedCatOptionText = selectedcatopt
                                    expandedCat = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
            }
            ExposedDropdownMenuBox(
                expanded = expandedWallet,
                onExpandedChange = { expandedWallet = !expandedWallet }
            ){
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                        .background(Color.White),
                    readOnly = true,
                    value = selectedWalletOptionText,
                    onValueChange = {},
                    label = { Text("Ví") },
                    trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedWallet)},
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White,
                        disabledIndicatorColor = Color.White
                    )
                )
                ExposedDropdownMenu(
                    expanded = expandedWallet,
                    onDismissRequest = { expandedWallet = false }
                ){
                    walletOptions.forEach {selectedwalletopt -> // Use walletOptions here
                        DropdownMenuItem(
                            text={Text(selectedwalletopt) },
                            onClick = {
                                selectedWalletOptionText = selectedwalletopt
                                expandedWallet = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }
            TextField(
                value = note,
                onValueChange = { newNote -> viewModel.setNote(newNote) },
                label = { Text("Ghi chú") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    disabledIndicatorColor = Color.White
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    if (!navController.popBackStack().not()) {
                        navController.navigate("transaction")
                    }
                }) {
                    Text("Cancel")
                }

                Button(
                    onClick = { /* Handle save action */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Green,
                        contentColor = Color.White,
                        disabledContentColor = Color.White,
                        disabledContainerColor = Color.Gray
                    )
                ) {
                    Text("Save")
                }

                Button(
                    onClick = { navController.navigate("EditTransactionScreen") }
                ) {
                    Text("Edit")
                }
            }
        }


        Column(modifier = Modifier.padding(16.dp)) {
            //Date Selector
            TextField(
                value = amount,
                onValueChange = { newAmount -> viewModel.setAmount(newAmount) },
                label = { Text("Ngày thực hiện giao dịch: ") })
            TextField(
                value = amount,
                onValueChange = { newAmount -> viewModel.setAmount(newAmount) },
                label = { Text("Số tiền: ") })
            TextField(
                value = amount,
                onValueChange = { newAmount -> viewModel.setAmount(newAmount) },
                label = { Text("Tên giao dịch: ") })
            TextField(
                value = amount,
                onValueChange = { newAmount -> viewModel.setAmount(newAmount) },
                label = { Text("Loại giao dịch: ") })
            TextField(
                value = amount,
                onValueChange = { newAmount -> viewModel.setAmount(newAmount) },
                label = { Text("Ví: ") })

            //DropdownMenu(items = listOf("Thể loại 1", "Thể loại 2"), selectedItem = category) { newCategory -> viewModel.setCategory(newCategory) }
                //DropdownMenu(items = listOf("Ví 1", "Ví 2"), selectedItem = wallet) { newWallet -> viewModel.setWallet(newWallet) }
                TextField(
                    value = note,
                    onValueChange = { newNote -> viewModel.setNote(newNote) },
                    label = { Text("Ghi chú") })
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
