package com.uit.moneykeeper.budget.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel

data class BudgetCategory(val icon: String, val name: String, val amount: Double)

data class MonthlyBudget(val month: Int, val totalBudget: Double, val categories: List<BudgetCategory>)

data class BudgetScreenViewModel(val year: Int, val monthlyBudgets: List<MonthlyBudget>)
