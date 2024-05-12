package com.uit.moneykeeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.uit.moneykeeper.components.MKNavigationBar
import com.uit.moneykeeper.ui.theme.MoneyKeeperTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoneyKeeperTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        MKNavigationBar(navController = navController)
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