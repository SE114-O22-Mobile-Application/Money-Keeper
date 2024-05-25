package com.uit.moneykeeper.budget.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uit.moneykeeper.budget.viewmodel.BudgetCategory
import com.uit.moneykeeper.budget.viewmodel.BudgetScreenViewModel
import com.uit.moneykeeper.budget.viewmodel.MonthlyBudget

@Composable
fun BudgetScreen(viewModel: BudgetScreenViewModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        BasicText(text = "Ngân sách", style = MaterialTheme.typography.titleMedium)
        BasicText(text = viewModel.year.toString(), style = MaterialTheme.typography.titleLarge)

        LazyColumn {
            items(viewModel.monthlyBudgets.size) { index ->
                val monthlyBudget = viewModel.monthlyBudgets[index]
                MonthlyBudgetItem(monthlyBudget)
            }
        }
    }
}

@Composable
fun MonthlyBudgetItem(monthlyBudget: MonthlyBudget) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        BasicText(text = "Thg ${monthlyBudget.month}", style = MaterialTheme.typography.titleMedium)
        BasicText(text = "${monthlyBudget.totalBudget} VND", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))

        for (category in monthlyBudget.categories) {
            BudgetCategoryItem(category)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetCategoryItem(category: BudgetCategory) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Icon(
            imageVector = when (category.name) {
                "Ăn uống" -> Icons.Default.Fastfood
                "Giải trí" -> Icons.Default.Movie
                "Mua sắm" -> Icons.Default.ShoppingCart
                "Khác" -> Icons.Default.MoreHoriz
                else -> Icons.Default.Category
            },
            contentDescription = category.name,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        BasicText(text = category.name, style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.weight(1f))
        BasicText(text = "${category.amount} VND", style = MaterialTheme.typography.bodyMedium)
    }
}