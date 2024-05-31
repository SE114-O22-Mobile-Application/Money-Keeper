package com.uit.moneykeeper.transaction.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.uit.moneykeeper.home.views.MonthPickerDialog
import com.uit.moneykeeper.transaction.components.DailyList
import com.uit.moneykeeper.transaction.viewmodel.DailyListViewModel
import com.uit.moneykeeper.transaction.viewmodel.MonthlyTabViewModel
import java.time.format.DateTimeFormatter

@Composable
fun MonthlyTab(navController: NavController, viewModel: MonthlyTabViewModel) {
    val selectedMonth by viewModel.selectedMonth.collectAsState()
    var openSelectMonth by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = viewModel::previousMonth) {
                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Tháng trước")
            }

            Text(text = "${selectedMonth.monthValue}/${selectedMonth.year}",
                modifier = Modifier
                    .padding(top = 10.dp)
                    .clickable { openSelectMonth = true },
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
            )

            IconButton(onClick = viewModel::nextMonth) {
                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Tháng sau")
            }
        }

        DailyList(navController = navController,viewModel = DailyListViewModel(viewModel.thisMonthList.collectAsState().value))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp)
        ) {if (openSelectMonth)
            item {
                MonthPickerDialog(
                    initialDate = selectedMonth,
                    onDismissRequest = { openSelectMonth = false },
                    onDateSelected = { date ->
                        viewModel.changeMonth(date)
                    }
                )
            }
        }
    }
}