package com.uit.moneykeeper.home.views

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uit.moneykeeper.R
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uit.moneykeeper.home.viewmodel.ListWalletViewModel
import com.uit.moneykeeper.home.viewmodel.SelectedWalletViewModel
import com.uit.moneykeeper.models.ViModel
import androidx.compose.runtime.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.zIndex
import com.uit.moneykeeper.home.viewmodel.HomeScreenViewModel


data class Wallet(val name: String, val amount: Int)
object SelectWallet {
    var Wallet: Wallet = Wallet("a",1)
}
@Composable
fun HomeScreen(navController: NavController,viewModel: HomeScreenViewModel , selectedWalletViewModel: SelectedWalletViewModel) {
//    Text(text = "This is Home Screen 3")
    println("is call home")
    MainContent(navController = navController,viewModel ,selectedWalletViewModel)
}

@Composable
fun MainContent(navController: NavController, viewModel: HomeScreenViewModel, selectedWalletViewModel: SelectedWalletViewModel) {
    val wallets = ListWalletViewModel().walletList
    var total = 0.0
    var showDialog by remember { mutableStateOf(false) }
    var textInput_ten by remember { mutableStateOf("") }
    var textInput_soDu by remember { mutableStateOf("") }
    for(wallet in wallets) {
        total +=  wallet.soDu
    }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Box(modifier = Modifier
                .fillMaxWidth()
//                .height(80.dp)
                .height(IntrinsicSize.Min)
                .background(Color(0xFFF8F8F8)))
            {
                Column {
                    Column( modifier = Modifier
                        .clickable {
                            selectedWalletViewModel.setViModel(ViModel(total, "Tất cả", 0))
                            navController.navigate("WalletDetail")
                        }
                    ) {
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
                    }

                    for(wallet in wallets) {
                        WalletCardItem(navController,wallet,selectedWalletViewModel)
                    }
                    Button(onClick = { showDialog = true },
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

            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(top = 30.dp)
//                .height(IntrinsicSize.Min)
                .background(Color(0xFFF8F8F8)))
            {
                Column {
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
                    OutlinedTextField(
                        value = "",
                        onValueChange = {  },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Red), // Đặt màu nền đỏ ở đây
                        label = { Text("Nhập văn bản") }
                    )
                }
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(top = 30.dp)
//                .height(IntrinsicSize.Min)
                .background(Color(0xFFF8F8F8)))
            {
                Column {
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
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Thêm ví mới",
                                    textAlign = TextAlign.Center,
                                    style = TextStyle(fontSize = 20.sp),
                                    modifier = Modifier
                                        .fillMaxWidth()) },
                    text = {
                        Column {
                            // Ô nhập văn bản
                            TextField(
                                value = textInput_ten,
                                onValueChange = { textInput_ten = it },
                                label = { Text("Tên ví") }
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            TextField(
                                value = textInput_soDu,
                                onValueChange = { textInput_soDu = it },
                                label = { Text("Số dư") },
                                modifier = Modifier
                                    .padding(top = 10.dp)
                            )
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = { showDialog = false
                                        viewModel.AddNewWallet("Cancel",textInput_ten,textInput_soDu.toDouble(), walletList = wallets)},
                            colors = ButtonDefaults.buttonColors(Color(0xFFf25207))
                        )             {
                            Text("Huỷ")
                        }
                    },
                    confirmButton = {
                        // Nút xác nhận
                        Button( modifier = Modifier,
                            onClick = {
                                viewModel.AddNewWallet("Add",textInput_ten,textInput_soDu.toDouble(), walletList = wallets)
                                showDialog = false
                            },
                            colors = ButtonDefaults.buttonColors(Color(0xFF1cba46))
                        ) {
                            Text("Thêm")
                        }
                    }

                )
            }
        }
    }
}

@Composable
fun WalletCardItem(navController: NavController,wallet: ViModel, selectedWalletViewModel: SelectedWalletViewModel) {
    val walletIcon = R.drawable.baseline_account_balance_wallet_24
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable {
            selectedWalletViewModel.setViModel(wallet)
            navController.navigate("WalletDetail")
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
                        walletIcon
                    ),
                    contentDescription = "Ví 1"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = wallet.ten)

            }
            Row(modifier = Modifier
                .padding(5.dp),
                horizontalArrangement = Arrangement.End
            ) {
                // Your row content here
                Text(formatNumberWithCommas(wallet.soDu)) // Example content
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

    Button(onClick = {  },
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
fun formatNumberWithCommas(doubleValue: Double): String {
    val formattedValue = String.format("%.2f", doubleValue) // Format với hai số sau dấu phẩy
    val parts = formattedValue.split('.') // Tách phần nguyên và phần thập phân

    var integerPart = parts[0] // Phần nguyên
    var decimalPart = parts.getOrElse(1) { "" } // Phần thập phân, mặc định là chuỗi trống nếu không có phần thập phân

    // Đổi mỗi 3 chữ số của phần nguyên thành một dấu chấm
    val integerLength = integerPart.length
    var index = 0
    while (integerLength - index > 3) {
        integerPart = integerPart.substring(0, integerLength - 3 - index) + "." + integerPart.substring(integerLength - 3 - index)
        index += 3
    }

    // Xóa các số 0 không cần thiết sau dấu phẩy nếu có
    while (decimalPart.isNotEmpty() && decimalPart.last() == '0') {
        decimalPart = decimalPart.dropLast(1)
    }

    // Nếu phần thập phân không còn chữ số nào thì không cần hiển thị dấu phẩy
    if (decimalPart.isEmpty()) {
        return integerPart
    }

    return "$integerPart,$decimalPart"
}
