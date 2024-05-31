package com.uit.moneykeeper.transaction.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.uit.moneykeeper.transaction.viewmodel.TransactionViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.uit.moneykeeper.home.views.YearPickerDialog
import com.uit.moneykeeper.transaction.components.CategoryDropdown
import com.uit.moneykeeper.transaction.components.DailyItem
import com.uit.moneykeeper.transaction.components.DoubleToStringConverter
import com.uit.moneykeeper.transaction.components.MonthlyItem
import com.uit.moneykeeper.transaction.components.WalletListDropdown
import com.uit.moneykeeper.transaction.viewmodel.CategoryDropdownViewModel
import com.uit.moneykeeper.transaction.viewmodel.DailyItemViewModel
import com.uit.moneykeeper.transaction.viewmodel.MonthlyItemViewModel
import com.uit.moneykeeper.transaction.viewmodel.WalletListDropdownViewModel
import com.uit.moneykeeper.transaction.viewmodel.YearlyTabViewModel
import com.uit.moneykeeper.ui.theme.Do
import com.uit.moneykeeper.ui.theme.XanhLa
import java.time.LocalDate

@Composable
fun YearlyTab(navController: NavController, viewModel: YearlyTabViewModel, moveToMonth: (LocalDate) -> Unit) {
    val selectedYear by viewModel.selectedYear.collectAsState()
    val sumIn by viewModel.sumIn.collectAsState()
    val sumOut by viewModel.sumOut.collectAsState()
    val sum by viewModel.sum.collectAsState()
    val monthlyItemList by viewModel.monthlyItemList.collectAsState()
    var openSelectYear by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = viewModel::previousYear) {
                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Năm trước")
            }

            Text(text = "$selectedYear",
                modifier = Modifier
                    .padding(top = 10.dp)
                    .clickable { openSelectYear = true },
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
            )

            IconButton(onClick = viewModel::nextYear) {
                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Năm sau")
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(all = 8.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.AccountBalanceWallet,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.Gray
            )
            WalletListDropdown(
                viewModel = WalletListDropdownViewModel(),
                onSelected = {
                    viewModel.updateWallet(it)
                },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.Filled.CreditCard,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.Gray
            )
            CategoryDropdown(
                viewModel = CategoryDropdownViewModel(),
                onSelected = {
                    viewModel.updateCategory(it)
                },
                modifier = Modifier.weight(1f)
            )
        }

        if (monthlyItemList.isEmpty()) {
            Text(
                text = "Chưa có giao dịch",
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 60.dp),
                fontSize = 18.sp,
            )
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                if (sumIn != 0.0) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Thu",
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "+${DoubleToStringConverter.convert(sumIn)}",
                            color = XanhLa
                        )
                    }
                }

                if (sumOut != 0.0) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Chi",
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "-${DoubleToStringConverter.convert(sumOut)}",
                            color = Do
                        )
                    }
                }

                if (sumIn != 0.0 && sumOut != 0.0) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Tổng",
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "${if (sum < 0) "" else "+"}${DoubleToStringConverter.convert(sum)}",
                            color = Color.Black
                        )
                    }
                }
            }

            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                monthlyItemList.forEach { monthlyItem ->
                    val monthlyItemViewModel = MonthlyItemViewModel(monthlyItem.first, selectedYear, monthlyItem.second)
                    MonthlyItem(navController = navController, viewModel = monthlyItemViewModel, moveToMonth = moveToMonth)
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp)
        ) {
            if (openSelectYear)
                item {
                    YearPicker(
                        initialDate = selectedYear,
                        onDismissRequest = { openSelectYear = false },
                        onDateSelected = { date ->
                            viewModel.changeYear(date.year)
                        }
                    )
                }
        }
    }
}

@Composable
fun YearPicker(
    initialDate: Int,
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDate) -> Unit
) {
    var selectedYear by remember { mutableStateOf(initialDate) }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Chọn Năm",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            val year = selectedYear
                            if (year != 1 && year > 0) {
                                selectedYear = year - 1
                            }
                        }
                    ) {
                        Icon(Icons.Default.ArrowBackIos, contentDescription = "Previous Month")
                    }

                    Text(
                        text = "Năm",
                        style = TextStyle(fontSize = 20.sp)
                    )
                    BasicTextField(
                        modifier = Modifier
                            .background(Color.White)
                            .width(50.dp),
                        value = selectedYear.toString(),
                        onValueChange = { newValue ->
                            if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                                val newYear = newValue.toIntOrNull() ?: 0;
                                if(newYear > LocalDate.now().year) selectedYear = LocalDate.now().year
                                else selectedYear = newYear;
                            } },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 20.sp,
                            // Align text to the bottom
                            textAlign = TextAlign.Left,
                            lineHeight = 20.sp // Adjust line height as needed
                        ),

                        )

                    IconButton(
                        onClick = {
                            val year = selectedYear
                            if (year != 1 && year < LocalDate.now().year) {
                                selectedYear = year + 1
                            }
                        }
                    ) {
                        Icon(Icons.Default.ArrowForwardIos, contentDescription = "Next Month")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = onDismissRequest) {
                        Text("Huỷ")
                    }
                    Button(
                        onClick = {
                            val year = selectedYear
                            onDateSelected(LocalDate.of(year, 1, 1))
                            onDismissRequest()
                        }
                    ) {
                        Text("Xác nhận")
                    }
                }
            }
        }
    }
}