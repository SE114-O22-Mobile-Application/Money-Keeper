package com.uit.moneykeeper.home.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.uit.moneykeeper.models.ViModel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.max
import androidx.compose.ui.zIndex
import com.uit.moneykeeper.home.viewmodel.ListWalletViewModel
import com.uit.moneykeeper.home.views.SelectWallet
import com.uit.moneykeeper.sample.GlobalObject
import com.uit.moneykeeper.transaction.viewmodel.MonthlyTabViewModel
import com.uit.moneykeeper.transaction.views.MonthlyTab
import com.uit.moneykeeper.transaction.views.YearlyTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletDetail(navController: NavController, viModel: State<ViModel>) {
    val walletList = ListWalletViewModel().walletList
    val wallet = viModel.value
    var isOpenSelectWallet by remember { mutableStateOf(false) }
    var selectedWallet by remember { mutableStateOf(wallet) }
    var selectedTabIndex by remember { mutableStateOf(0)}
    Box(modifier = Modifier.fillMaxSize()) {
        // Combobox ở đây
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 50.dp)
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(2.dp)
                )
                .clickable { isOpenSelectWallet = !isOpenSelectWallet },


        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(height = 50.dp)
                    .background(Color(0xFF00c190))
                    .align(Alignment.Center)
                            ) {
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = selectedWallet.ten,
                    modifier = Modifier.weight(1f),
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                )
                Text(
                    text = formatNumberWithCommas(selectedWallet.soDu),
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    contentDescription = "Icon ở cuối"
                )
            }
        }
        if(isOpenSelectWallet) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
//                .background(Color.Blue)
                    .padding(top = 50.dp)
                    .background(Color(0xFFF8F8F8))
                    .heightIn(max = 200.dp)
                    .zIndex(1f)
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(2.dp)
                    ),
            ) {
                items(walletList.size) { index ->
                    val wallet = walletList[index]
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(50.dp)
                            .clickable {
                                selectedWallet = wallet
                                isOpenSelectWallet = false
                            }
                            .background(Color(0xFFb6f0e1))

                    ) {
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = wallet.ten,
                            modifier = Modifier.weight(1f) // Text sẽ chiếm hết không gian còn lại trong Row
                        )
                        Text(
                            text = formatNumberWithCommas(wallet.soDu),
                        )
                    }
                }
            }
        }
        Column(modifier = Modifier.padding(top = 50.dp)) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = { Text("Tháng") }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = { Text("Năm") }
                )
            }
        }
        // LazyColumn chiếm phần còn lại của màn hình
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp) // Để đẩy nó xuống dưới Combobox
        ) {
            if(selectedTabIndex == 0) {
                item {
                    Text(text = "Tháng")
                }
            }
            else {
                item {
                    Text(text = "Năm")
                }
            }

        }
    }
}
