package com.uit.moneykeeper.transaction.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.uit.moneykeeper.transaction.viewmodel.MonthlyTabViewModel
import com.uit.moneykeeper.transaction.viewmodel.TransactionViewModel

@Composable
fun TransactionScreen(navController: NavController, viewModel: TransactionViewModel) {
    val selectedTabIndex by viewModel.selectedTabIndex.collectAsState()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {navController.navigate("NewTransactionScreen")},
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add transaction",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { viewModel.changeTab(0) },
                    text = { Text("Tháng") }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { viewModel.changeTab(1) },
                    text = { Text("Năm") }
                )
            }

            Box {
                when (selectedTabIndex) {
                    0 -> MonthlyTab(navController,viewModel = MonthlyTabViewModel())
                    1 -> YearlyTab(viewModel)
                }
            }
        }
    }
}