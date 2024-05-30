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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.uit.moneykeeper.transaction.viewmodel.EditTransactionViewModel
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@SuppressWarnings("SpellCheckingInspection")

@Composable
fun EditTransactionScreen(navController: NavController, viewModel: EditTransactionViewModel) {
    val giaoDich by viewModel.giaoDich.collectAsState(initial = null)
    val isLoading by viewModel.isLoading.collectAsState()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    var date by remember(giaoDich) { mutableStateOf(giaoDich?.ngayGiaoDich?.format(formatter) ?: "") }
    var amount by remember(giaoDich) { mutableStateOf(giaoDich?.soTien?.toString()?:"" ) }
    var name by remember(giaoDich) { mutableStateOf(giaoDich?.ten ?: "") }
    val category by viewModel.category.collectAsState()
    val categoryOptions = viewModel.categoryOptions.collectAsState().value
    var selectedCatOptionText by remember(giaoDich) { mutableStateOf(giaoDich?.loaiGiaoDich?.ten ?: "") }
    val wallet by viewModel.wallet.collectAsState()
    val walletOptions = viewModel.walletOptions.collectAsState().value
    var selectedWalletOptionText by remember(giaoDich) { mutableStateOf(giaoDich?.vi?.ten?: "") }
    var note by remember(giaoDich) { mutableStateOf(giaoDich?.ghiChu ?: "") }

    val focusRequester = remember { FocusRequester() }
    val decoyFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val calendarState = rememberUseCaseState()
    var isCalendarDialogShowing by remember { mutableStateOf(false) }

    val catFocusRequester = remember { FocusRequester() }
    val walletFocusRequester = remember { FocusRequester() }
    var expandedCat by remember { mutableStateOf(false) }
    var expandedWallet by remember { mutableStateOf(false) }

    val context = LocalContext.current

    CalendarDialog(
        state = calendarState,
        selection = CalendarSelection.Date { selectedDate ->
            date = selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
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
                        text = "Sửa giao dịch",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (!navController.popBackStack()) {
                            navController.navigate("TransactionDetailScreen/${viewModel.id}")
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
                    value = date.toString(),
                    onValueChange = { /* Do nothing as we don't want to allow manual date input */ },
                    label = { Text("Ngày thực hiện giao dịch") },
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
                    onValueChange = { newAmount ->
                        amount = if (newAmount.isEmpty()) {
                            ""
                        } else {
                            newAmount.toDoubleOrNull()?.let {
                                if (it % 1 == 0.0) it.toInt().toString() else it.toString()
                            } ?: ""
                        }
                        viewModel.setAmount(amount)
                    },
                    label = { Text("Số tiền") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.LightGray,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    )
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { newName ->
                        name = newName
                        viewModel.setName(newName)
                    },
                    label = { Text("Tên giao dịch") },
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
                    onExpandedChange = {
                        expandedCat = !expandedCat
                        if (expandedCat) {
                            catFocusRequester.requestFocus()
                        }
                    }
                ){
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                            .background(Color.White)
                            .focusRequester(catFocusRequester),
                        readOnly = true,
                        value = selectedCatOptionText.toString(),
                        onValueChange = {},
                        label = { Text("Loại giao dịch") },
                        trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCat)},
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
                    ){
                        categoryOptions.forEach {selectedcatopt ->
                            DropdownMenuItem(
                                text={Text(selectedcatopt) },
                                onClick = {
                                    selectedCatOptionText = selectedcatopt
                                    viewModel.setSelectedCatOptionText(selectedCatOptionText as String)
                                    expandedCat = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }
                ExposedDropdownMenuBox(
                    expanded = expandedWallet,
                    onExpandedChange = {
                        expandedWallet = !expandedWallet
                        if (expandedWallet) {
                            walletFocusRequester.requestFocus()
                        }
                    }
                ){
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                            .background(Color.White)
                            .focusRequester(walletFocusRequester),
                        readOnly = true,
                        value = selectedWalletOptionText.toString(),
                        onValueChange = {},
                        label = { Text("Ví") },
                        trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedWallet)},
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
                    ){
                        walletOptions.forEach {selectedwalletopt ->
                            DropdownMenuItem(
                                text={Text(selectedwalletopt) },
                                onClick = {
                                    selectedWalletOptionText = selectedwalletopt
                                    viewModel.setSelectedWalletOptionText(selectedWalletOptionText as String)
                                    expandedWallet = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value = note,
                    onValueChange = { newNote ->
                        note = newNote
                        viewModel.setNote(newNote)
                    },
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
                    onValueChange = { /* decoy do nothing */ },
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
                        onClick = { /*SAVE*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Green,
                            contentColor = Color.White,
                            disabledContentColor = Color.White,
                            disabledContainerColor = Color.Gray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}