package com.uit.moneykeeper.transaction.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.uit.moneykeeper.transaction.viewmodel.MonthlyTabViewModel
import androidx.navigation.NavController
import com.uit.moneykeeper.transaction.viewmodel.TransactionViewModel

@Composable
fun TransactionScreen(navController: NavController,viewModel: TransactionViewModel) {
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
                    0 -> MonthlyTab(viewModel = MonthlyTabViewModel())
                    1 -> YearlyTab(viewModel)
                }
            }
        }
    }
}