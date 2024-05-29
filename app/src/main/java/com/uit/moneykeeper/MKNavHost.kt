package com.uit.moneykeeper

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.uit.moneykeeper.budget.viewmodel.NewBudgetViewModel
import com.uit.moneykeeper.budget.viewmodel.getListNganSachByMonthYear
import com.uit.moneykeeper.budget.views.BudgetScreen
import com.uit.moneykeeper.budget.views.NewBudget
import com.uit.moneykeeper.global.GlobalObject
import com.uit.moneykeeper.home.viewmodel.DetailWalletViewModel
import com.uit.moneykeeper.home.viewmodel.HomeScreenViewModel
import com.uit.moneykeeper.home.viewmodel.SelectedWalletViewModel
import com.uit.moneykeeper.home.views.HomeScreen
import com.uit.moneykeeper.home.views.WalletDetail
import com.uit.moneykeeper.transaction.viewmodel.EditTransactionViewModel
import com.uit.moneykeeper.transaction.viewmodel.NewTransactionViewModel
import com.uit.moneykeeper.transaction.viewmodel.TransactionDetailViewModel
import com.uit.moneykeeper.transaction.viewmodel.TransactionViewModel
import com.uit.moneykeeper.transaction.views.EditTransactionScreen
import com.uit.moneykeeper.transaction.views.NewTransactionScreen
import java.time.LocalDate
import com.uit.moneykeeper.transaction.views.TransactionDetailScreen
import com.uit.moneykeeper.transaction.views.TransactionScreen

@Composable
fun MKNavHost(
    navController: NavHostController,
    selectedWalletViewModel: SelectedWalletViewModel,
    newbudgetViewModel: NewBudgetViewModel,
    modifier: Modifier = Modifier,
    showNavigationBar: MutableState<Boolean>
) {
    var previousBackStackEntry by remember { mutableStateOf<NavBackStackEntry?>(null) }
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(currentBackStackEntry) {
        if (previousBackStackEntry?.destination?.route != "home" && (currentBackStackEntry?.destination?.route == "WalletDetail" )) {
            navController.popBackStack();
        }

        else if (currentBackStackEntry != null && currentBackStackEntry != previousBackStackEntry) {
            // Update the previous back stack entry
            previousBackStackEntry = currentBackStackEntry
        }
    }
    val listNS = getListNganSachByMonthYear(LocalDate.now())
    println("List NS: $listNS")
    NavHost(
        navController = navController,
        startDestination = if(listNS.isEmpty()) "home" else "home",
        modifier = modifier
    ) {
        composable("home") {
            showNavigationBar.value = true
            HomeScreen(navController, viewModel = HomeScreenViewModel(), selectedWalletViewModel)
        }
        composable("WalletDetail") {
            showNavigationBar.value = true
            WalletDetail(navController = navController, viewModel = DetailWalletViewModel(), viModel = selectedWalletViewModel.getViModel())
        }
        composable("NewTransactionScreen") {
            showNavigationBar.value = false
            NewTransactionScreen(navController, viewModel = NewTransactionViewModel())
        }
        composable("EditTransactionScreen/{Id}") { backStackEntry ->
            showNavigationBar.value = false
            val Id = backStackEntry.arguments?.getInt("Id")
            if (Id != null) {
                EditTransactionScreen(navController, viewModel = EditTransactionViewModel(Id))
            } else {
                Text(text = "Error: Id is null")
            }
        }
        composable("TransactionDetailScreen/{Id}") { backStackEntry ->
            showNavigationBar.value = false
            val Id = backStackEntry.arguments?.getInt("Id")
            if (Id != null) {
                TransactionDetailScreen(navController, viewModel = TransactionDetailViewModel(Id))
            } else {
                Text(text = "Error: Id is null")
            }
        }
        composable("transaction") {
            showNavigationBar.value = true
            TransactionScreen(navController, viewModel = TransactionViewModel())
        }
        composable("budget") {
            showNavigationBar.value = true
            BudgetScreen(navController = navController, newbudgetViewModel)
        }
        composable("newbudget") {
            showNavigationBar.value = false
//            Text(text = "Account")
            NewBudget(navController = navController, thoiGian = newbudgetViewModel.getTime())
        }
        composable("account") {
            showNavigationBar.value = true
            Text(text = "Account")
//            NewBudget(navController = navController, thoiGian = newbudgetViewModel.getTime())
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        println("Route to: $route")
        popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
