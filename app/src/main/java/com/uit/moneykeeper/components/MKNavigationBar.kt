package com.uit.moneykeeper.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.uit.moneykeeper.Home
import com.uit.moneykeeper.mkScreens
import com.uit.moneykeeper.navigateSingleTopTo

@Composable
fun MKNavigationBar(navController: NavHostController) {
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen =
        mkScreens.find { it.route == currentDestination?.route } ?: Home
    if (currentDestination?.route != "sign_in") NavigationBar {
        mkScreens.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        ImageVector.vectorResource(
                            if (navController.currentDestination?.route == screen.route) {
                                screen.activatedIcon
                            } else {
                                screen.inactivatedIcon
                            }
                        ), contentDescription = screen.route
                    )

                },
                label = {
                    Text(text = screen.label)
                },
                onClick = {
                    navController.navigateSingleTopTo(screen.route)
                },
                selected = currentScreen.route == screen.route
            )
        }
    }
}
