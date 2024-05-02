package com.uit.moneykeeper.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletDetail(navController: NavController) {
    val wallet = SelectWallet.Wallet
    Scaffold(topBar = {
        TopAppBar(title = { Text(wallet.name) }, navigationIcon = {
            IconButton(onClick = { navController.navigate("main-screen") }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        })
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // ... Rest of the content
        }
    }
}
