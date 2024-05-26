package com.uit.moneykeeper

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
import com.uit.moneykeeper.transaction.views.TransactionDetailScreen
import com.uit.moneykeeper.transaction.views.TransactionScreen

@Composable
fun MKNavHost(
    navController: NavHostController,
    selectedWalletViewModel: SelectedWalletViewModel,
    modifier: Modifier = Modifier,
    showNavigationBar: MutableState<Boolean>
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
//        composable("sign_in") {
//            SignInScreen(navController)
//        }
        composable("home") {
            showNavigationBar.value = true
            HomeScreen(navController, viewModel = HomeScreenViewModel() , selectedWalletViewModel)
        }
        composable("WalletDetail") {
            showNavigationBar.value = true
            WalletDetail(navController = navController, viModel = selectedWalletViewModel.getViModel())
        }
        composable("NewTransactionScreen") {
            showNavigationBar.value = false
            NewTransactionScreen(navController, viewModel = NewTransactionViewModel())
        }
        composable("EditTransactionScreen") {
            showNavigationBar.value = false
            EditTransactionScreen(navController, viewModel = EditTransactionViewModel())
        }
        composable("TransactionDetailScreen"){
            showNavigationBar.value = false
            TransactionDetailScreen(navController, viewModel = TransactionDetailViewModel())
        }
        composable("transaction") {
            showNavigationBar.value = true
            TransactionScreen(navController, viewModel = TransactionViewModel())
        }
        composable("budget") {
            showNavigationBar.value = true
            // Replace with your own composable
            Text(text = "Budget")
        }
        composable("account") {
            showNavigationBar.value = true
            // Replace with your own composable
            Text(text = "Account")
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }