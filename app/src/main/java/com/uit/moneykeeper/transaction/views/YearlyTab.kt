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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import com.uit.moneykeeper.transaction.viewmodel.TransactionViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.uit.moneykeeper.transaction.components.DailyItem
import com.uit.moneykeeper.transaction.components.DoubleToStringConverter
import com.uit.moneykeeper.transaction.components.MonthlyItem
import com.uit.moneykeeper.transaction.viewmodel.DailyItemViewModel
import com.uit.moneykeeper.transaction.viewmodel.MonthlyItemViewModel
import com.uit.moneykeeper.transaction.viewmodel.YearlyTabViewModel
import com.uit.moneykeeper.ui.theme.Do
import com.uit.moneykeeper.ui.theme.XanhLa

@Composable
fun YearlyTab(navController: NavController, viewModel: YearlyTabViewModel) {
    val selectedYear by viewModel.selectedYear.collectAsState()
    val sumIn by viewModel.sumIn.collectAsState()
    val sumOut by viewModel.sumOut.collectAsState()
    val sum by viewModel.sum.collectAsState()
    val monthlyItemList by viewModel.monthlyItemList.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = viewModel::previousYear) {
                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Năm trước")
            }

            Text(
                text = selectedYear.toString(),
                modifier = Modifier
                    .clickable { /* Show calendar here */ }
                    .heightIn(max = Dp.Infinity)
                    .padding(PaddingValues(16.dp))
                    .align(Alignment.CenterVertically),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )

            IconButton(onClick = viewModel::nextYear) {
                Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Năm sau")
            }
        }

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
                val monthlyItemViewModel = MonthlyItemViewModel(monthlyItem.first, monthlyItem.second)
                MonthlyItem(navController = navController, viewModel = monthlyItemViewModel)
            }
        }
    }
}