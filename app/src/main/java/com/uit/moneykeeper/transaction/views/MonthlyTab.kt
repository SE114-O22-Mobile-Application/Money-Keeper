package com.uit.moneykeeper.transaction.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.uit.moneykeeper.transaction.components.DailyList
import com.uit.moneykeeper.transaction.viewmodel.DailyListViewModel
import com.uit.moneykeeper.transaction.viewmodel.MonthlyTabViewModel
import java.time.format.DateTimeFormatter

@Composable
fun MonthlyTab(viewModel: MonthlyTabViewModel) {
    val selectedMonth by viewModel.selectedMonth.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = viewModel::previousMonth) {
                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Tháng trước")
            }

            Text(
                text = selectedMonth.format(DateTimeFormatter.ofPattern("MM/yyyy")),
                modifier = Modifier
                    .clickable { /* Show calendar here */ }
                    .heightIn(max = Dp.Infinity)
                    .padding(PaddingValues(8.dp))
                    .align(Alignment.CenterVertically),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )

            IconButton(onClick = viewModel::nextMonth) {
                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Tháng sau")
            }
        }

        DailyList(viewModel = DailyListViewModel(viewModel.thisMonthList.collectAsState().value))
    }
}