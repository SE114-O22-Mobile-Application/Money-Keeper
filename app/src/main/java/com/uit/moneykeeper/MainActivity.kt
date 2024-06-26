package com.uit.moneykeeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.Observer
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.uit.moneykeeper.budget.viewmodel.NewBudgetViewModel
import com.uit.moneykeeper.components.MKNavigationBar
import com.uit.moneykeeper.global.GlobalObject
//import com.uit.moneykeeper.global.uploadCTNganSachSamples
//import com.uit.moneykeeper.global.uploadNganSachSamples
//import com.uit.moneykeeper.global.uploadLoaiGiaoDichSamples
//import com.uit.moneykeeper.global.uploadViSamples
//import com.uit.moneykeeper.global.uploadgiaoDichSamples
import com.uit.moneykeeper.home.viewmodel.SelectedWalletViewModel
import com.uit.moneykeeper.ui.theme.MoneyKeeperTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoneyKeeperTheme {
                val navController = rememberNavController()
                val showNavigationBar = remember { mutableStateOf(true) }
                Scaffold(
                    bottomBar = {
                        if (showNavigationBar.value) {
                            MKNavigationBar(navController = navController)
                        }
                    }
                ) { innerPadding ->
                    MKNavHost(
                        navController = navController,
                        selectedWalletViewModel = SelectedWalletViewModel(),
                        newbudgetViewModel = NewBudgetViewModel(),
                        modifier = Modifier.padding(innerPadding),
                        showNavigationBar = showNavigationBar
                    )
                }
            }
        }
//        uploadgiaoDichSamples()
//        uploadLoaiGiaoDichSamples()
//        uploadViSamples()
//        uploadNganSachSamples()
//        uploadCTNganSachSamples()
    }
}
