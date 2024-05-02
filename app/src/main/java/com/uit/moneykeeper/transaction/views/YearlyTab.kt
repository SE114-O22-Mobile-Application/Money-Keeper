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
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun YearlyTab(viewModel: TransactionViewModel) {
    val selectedYear by viewModel.selectedYear.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = viewModel::previousYear) {
                Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = "Năm trước")
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
                Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = "Năm sau")
            }
        }

        // Rest of the YearlyTab content goes here
    }
}