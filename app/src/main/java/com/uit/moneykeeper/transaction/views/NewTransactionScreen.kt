package com.uit.moneykeeper.transaction.views


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
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
    val decoyFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val calendarState = rememberUseCaseState()
    var isCalendarDialogShowing by remember { mutableStateOf(false) }

    var expandedCat by remember { mutableStateOf(false) }
    var expandedWallet by remember { mutableStateOf(false) }
    val context = LocalContext.current

    CalendarDialog(
        state = calendarState,
        selection = CalendarSelection.Date { selectedDate ->
            Log.d("Selected date", "$selectedDate")
            viewModel.setDate(selectedDate)
            calendarState.hide()
            isCalendarDialogShowing = false
            focusManager.clearFocus()
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        ),
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
        CompositionLocalProvider(LocalFocusManager provides focusManager) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
                    .padding(top = 70.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
            )
            {
                OutlinedTextField(
                    value = dateString,
                    onValueChange = { /* Do nothing as we don't want to allow manual date input */ },
                    label = { Text("Ngày thực hiện giao dịch: ") },
                    readOnly = true,
                    modifier = Modifier
                        .background(Color.Transparent)
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState: FocusState ->
                            if (focusState.isFocused) {
                                calendarState.show()
                                isCalendarDialogShowing = true
                                decoyFocusRequester.requestFocus()
                            }
                        }
                        .fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.LightGray,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    )
                )
                OutlinedTextField(
                    value = amount,
                    onValueChange = { newAmount -> viewModel.setAmount(newAmount) },
                    label = { Text("Số tiền: ") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.LightGray,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    )
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { newName -> viewModel.setName(newName) },
                    label = { Text("Tên giao dịch: ") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.LightGray,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    )
                )
                ExposedDropdownMenuBox(
                    expanded = expandedCat,
                    onExpandedChange = { expandedCat = !expandedCat }
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                            .background(Color.White),
                        readOnly = true,
                        value = selectedCatOptionText,
                        onValueChange = {},
                        label = { Text("Loại giao dịch") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCat) },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.LightGray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expandedCat,
                        onDismissRequest = { expandedCat = false }
                    ) {
                        categoryOptions.forEach { selectedcatopt ->
                            DropdownMenuItem(
                                text = { Text(selectedcatopt) },
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
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                            .background(Color.White),
                        readOnly = true,
                        value = selectedWalletOptionText,
                        onValueChange = {},
                        label = { Text("Ví") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedWallet) },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.LightGray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expandedWallet,
                        onDismissRequest = { expandedWallet = false }
                    ) {
                        walletOptions.forEach { selectedwalletopt -> // Use walletOptions here
                            DropdownMenuItem(
                                text = { Text(selectedwalletopt) },
                                onClick = {
                                    selectedWalletOptionText = selectedwalletopt
                                    expandedWallet = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value = note,
                    onValueChange = { newNote -> viewModel.setNote(newNote) },
                    label = { Text("Ghi chú") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.LightGray,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    )
                )
                OutlinedTextField(
                    value = "", //decoy textfield to handle focus
                    onValueChange = { /* decoy textfield */ },
                    readOnly = true,
                    label = { Text("") }, // empty label
                    modifier = Modifier.fillMaxWidth().focusRequester(decoyFocusRequester),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent,
                        cursorColor = Color.Transparent
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            if (!navController.popBackStack().not()) {
                                navController.navigate("transaction")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White,
                            disabledContentColor = Color.White,
                            disabledContainerColor = Color.Gray
                        )
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = { viewModel.saveTransaction(navController, context) },
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

