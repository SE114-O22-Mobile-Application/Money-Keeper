package com.uit.moneykeeper

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.uit.moneykeeper.home.viewmodel.DetailWalletViewModel
import com.uit.moneykeeper.home.viewmodel.HomeScreenViewModel
import com.uit.moneykeeper.home.viewmodel.SelectedWalletViewModel
import com.uit.moneykeeper.home.views.HomeScreen
import com.uit.moneykeeper.home.views.WalletDetail
import com.uit.moneykeeper.transaction.viewmodel.TransactionViewModel
import com.uit.moneykeeper.transaction.views.TransactionScreen
import com.uit.moneykeeper.transaction.views.NewTransactionScreen
import com.uit.moneykeeper.transaction.viewmodel.NewTransactionViewModel
import com.uit.moneykeeper.models.ViModel

@Composable
fun MKNavHost(
    navController: NavHostController,
    selectedWalletViewModel: SelectedWalletViewModel,
    modifier: Modifier = Modifier
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
            HomeScreen(navController, viewModel = HomeScreenViewModel() , selectedWalletViewModel)
        }
        composable("WalletDetail") {
            WalletDetail(navController = navController, viModel = selectedWalletViewModel.getViModel())
        }
        composable("NewTransactionScreen") {
            NewTransactionScreen(navController, viewModel = NewTransactionViewModel())
        }
        composable("transaction") {
            TransactionScreen(navController, viewModel = TransactionViewModel())
        }
        composable("budget") {
            // Replace with your own composable
            Text(text = "Budget")
        }
        composable("account") {
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