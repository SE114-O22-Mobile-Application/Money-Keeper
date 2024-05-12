package com.uit.moneykeeper

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.uit.moneykeeper.home.HomeScreen
import com.uit.moneykeeper.sign_in.SignInScreen
import com.uit.moneykeeper.transaction.viewmodel.EditTransactionViewModel
import com.uit.moneykeeper.transaction.viewmodel.TransactionViewModel
import com.uit.moneykeeper.transaction.views.TransactionScreen
import com.uit.moneykeeper.transaction.viewmodel.NewTransactionViewModel
import com.uit.moneykeeper.transaction.views.EditTransactionScreen
import com.uit.moneykeeper.transaction.views.NewTransactionScreen

@Composable
fun MKNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "transaction",
        modifier = modifier
    ) {
//        composable("sign_in") {
//            SignInScreen(navController)
//        }
//        composable("home") {
//            HomeScreen(navController)
//        }
        composable("NewTransactionScreen") {
            NewTransactionScreen(navController, viewModel = NewTransactionViewModel())
        }
        composable("EditTransactionScreen") {
            EditTransactionScreen(navController, viewModel = EditTransactionViewModel())
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