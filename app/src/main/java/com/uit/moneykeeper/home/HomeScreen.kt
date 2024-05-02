package com.uit.moneykeeper.home

import android.widget.Button
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uit.moneykeeper.R
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.ui.text.font.FontStyle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


data class Wallet(val name: String, val amount: Int)
object SelectWallet {
    var Wallet: Wallet = Wallet("a",1)
}
@Composable
fun HomeScreen() {
//    Text(text = "This is Home Screen 3")
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main-screen" ) {
        composable("main-screen") {
            MainContent(navController = navController)
        }
        composable("walletdetail-screen") {
            WalletDetail(navController = navController)
        }
    }
}

@Composable
fun MainContent(navController: NavController) {
    var SelectWallet:Wallet
    val wallets = arrayOf(
        Wallet("Ví A", 1000000),
        Wallet("Ví B", 5000000),
        Wallet("Ví C", 2000000)
    )
    var total:Int = 0;
    for(wallet in wallets) {
        total +=  wallet.amount
    }
    println("total: " + total)
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Box(modifier = Modifier
                .fillMaxWidth()
//                .height(80.dp)
                .height(IntrinsicSize.Min)
                .background(Color(0xFFF8F8F8)))
            {
                Column {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Số dư",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = formatNumberWithCommas(total),
                            fontSize = 20.sp,
                            color = Color.Blue
                        )
                    }
                    for(wallet in wallets) {
                        WalletCardItem(navController,wallet)
                    }
                    ButtonAddWallet()
                }

            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(top = 30.dp)
//                .height(IntrinsicSize.Min)
                .background(Color(0xFFF8F8F8)))
            {
                Column() {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Chi phí - 7 ngày qua",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                    Text(text = "Biểu đồ cột")
                }
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(top = 30.dp)
//                .height(IntrinsicSize.Min)
                .background(Color(0xFFF8F8F8)))
            {
                Column() {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Thống kê",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                    Text(text = "Phần thống kê")
                }
            }
        }
    }
}

@Composable
fun WalletCardItem(navController: NavController,wallet: Wallet) {
    val WalletIcon = R.drawable.baseline_account_balance_wallet_24
    println("id: " + WalletIcon)
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable {
            SelectWallet.Wallet = wallet
            navController.navigate("walletdetail-screen")
        }
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier
                .padding(5.dp)
                ) {
                Icon(
                    ImageVector.vectorResource(
                        WalletIcon
                    ),
                    contentDescription = "Ví 1"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = wallet.name)

            }
            Row(modifier = Modifier
                .padding(5.dp),
                horizontalArrangement = Arrangement.End
            ) {
                // Your row content here
                Text(formatNumberWithCommas(wallet.amount)) // Example content
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .border(
                border = BorderStroke(
                    color = Color.Black,
                    width = 1.dp
                )
            )) {

        }
    }
}

@Composable
fun ButtonAddWallet() {

    Button(onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(8.dp),

        colors = ButtonDefaults.buttonColors(Color(0xFF00c190)),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = Icons.Default.Add, // Replace with your desired icon
                contentDescription = "Home icon"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Thêm ví",
                fontSize = 20.sp,)
        }
    }
}

fun formatNumberWithCommas(number: Int): String {
    val numberString = number.toString()
    val reversedNumberString = numberString.reversed()
    val formattedStringBuilder = StringBuilder()
    for (i in reversedNumberString.indices) {
        formattedStringBuilder.append(reversedNumberString[i])
        if ((i + 1) % 3 == 0 && (i + 1) != reversedNumberString.length) {
            formattedStringBuilder.append(".")
        }
    }
    println("Before: " + numberString)
    println("After: " + formattedStringBuilder.reverse().toString())
    return formattedStringBuilder.toString()
}

