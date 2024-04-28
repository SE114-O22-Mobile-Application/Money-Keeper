package com.uit.moneykeeper

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.uit.moneykeeper.ui.theme.MoneyKeeperTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoneyKeeperTheme {
                val navController = rememberNavController()
                val currentBackStack by navController.currentBackStackEntryAsState()
                val currentDestination = currentBackStack?.destination
                val currentScreen =
                    mkScreens.find { it.route == currentDestination?.route } ?: Home

                Scaffold(
                    bottomBar = {
                        NavigationBar {
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
                ) { innerPadding ->
                    MKNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}