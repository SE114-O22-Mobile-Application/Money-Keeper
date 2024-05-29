package com.uit.moneykeeper.home.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import com.uit.moneykeeper.global.GlobalObject
import com.uit.moneykeeper.home.viewmodel.ListWalletViewModel
import com.uit.moneykeeper.home.components.Statistical
import com.uit.moneykeeper.home.viewmodel.DetailWalletViewModel
import com.uit.moneykeeper.ui.theme.Do
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletDetail(navController: NavController, viewModel: DetailWalletViewModel ,viModel2: State<ViModel>) {
    val listVi = viewModel.getViList();
    var viModel = viModel2.value
    if(viModel2.value.id == 0) {
        val total = listVi.sumOf { it.soDu }
        viModel = ViModel(0, "Tất cả", total)
    }
    var walletList2 = ListWalletViewModel().walletList
    val wallets = remember { mutableStateListOf<ViModel>() }
    LaunchedEffect(key1 = walletList2 ) {
        println("LaunchWL")
        wallets.clear();
        for(wallet in walletList2) {
            wallets.add(wallet);
        }
    }
    var total = 0.0
    for(wallet in wallets) {
        total +=  wallet.soDu
    }
    println("List wallet: " + wallets)
    println("Total: " + total)
    val wltmp: MutableList<ViModel> = mutableListOf();
    wltmp.add(ViModel(0, "Tất cả", total));
    wallets.forEach() {
        item ->
        wltmp.add(item);
    }
    val walletList = wltmp;
    val wallet = viModel

    var isOpenSelectWallet by remember { mutableStateOf(false) }
    var selectedWallet by remember { mutableStateOf(wallet) }
    var selectedTabIndex by remember { mutableStateOf(0)}
    var selectedTime by remember { mutableStateOf(LocalDate.now())}
    var openSelectMonth by remember { mutableStateOf(false) }
    var openSelectYear by remember { mutableStateOf(false) }
    var openEditWallet by remember { mutableStateOf(false) }
    var openConfirmDelete by remember { mutableStateOf(false) }
    var openErrorDelete by remember { mutableStateOf(false) }
    var textInput_ten by remember { mutableStateOf(selectedWallet.ten) }
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
        Row(modifier = Modifier
            .padding(top = 100.dp)
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIos,
                contentDescription = "Menu",
                tint = Color.Black,
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp)
                    .clickable {
                        selectedTime =
                            if (selectedTabIndex == 0) selectedTime.minusMonths(1) else selectedTime.minusYears(
                                1
                            )
                    }
            )
            if(selectedTabIndex == 0)
                Text(text = "${selectedTime.monthValue}/${selectedTime.year}",
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .clickable { openSelectMonth = true },
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))
            else
                Text(text = "${selectedTime.year}",
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .clickable { openSelectYear = true },
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))
            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = "Menu",
                tint = Color.Black,
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp)
                    .clickable {
                        selectedTime =
                            if (selectedTabIndex == 0) selectedTime.plusMonths(1) else selectedTime.plusYears(
                                1
                            )
                    }
            )
        }
        // LazyColumn chiếm phần còn lại của màn hình
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp) // Để đẩy nó xuống dưới Combobox
        ) {
            if (openSelectMonth)
                item {
                    MonthPickerDialog(
                        initialDate = selectedTime,
                        onDismissRequest = { openSelectMonth = false },
                        onDateSelected = { date ->
                            selectedTime = date
                        }
                    )
                }
            if (openSelectYear)
                item {
                    YearPickerDialog(
                        initialDate = selectedTime,
                        onDismissRequest = { openSelectYear = false },
                        onDateSelected = { date ->
                            selectedTime = date
                        }
                    )
                }
            if (selectedTabIndex == 0) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),

                            ) {
                            Text(
                                "Thu",
                                modifier = Modifier.align(Alignment.Center),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF4e6df5)
                                )
                            )
                        }
                        Statistical(selectedWallet, selectedTime, selectedTime, "Month", "Thu")
                    }

                    Spacer(
                        modifier = Modifier
                            .height(40.dp)
                            .background(Color.Red)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),

                            ) {
                            Text(
                                "Chi",
                                modifier = Modifier.align(Alignment.Center),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFeb7434)
                                )
                            )
                        }
                        Statistical(selectedWallet, selectedTime, selectedTime, "Month", "Chi")
                    }
                }
            } else {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),

                            ) {
                            Text(
                                "Thu",
                                modifier = Modifier.align(Alignment.Center),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF4e6df5)
                                )
                            )
                        }
                        Statistical(selectedWallet, selectedTime, selectedTime, "Year", "Thu")
                    }

                    Spacer(
                        modifier = Modifier
                            .height(40.dp)
                            .background(Color.Red)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),

                            ) {
                            Text(
                                "Chi",
                                modifier = Modifier.align(Alignment.Center),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFeb7434)
                                )
                            )
                        }
                        Statistical(selectedWallet, selectedTime, selectedTime, "Year", "Chi")
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
            if (selectedWallet.id != 0) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { openEditWallet = true },
                            modifier = Modifier
                                .weight(1f)
                                .height(70.dp)
                                .padding(end = 4.dp), // Add some spacing between the buttons
                            colors = ButtonDefaults.buttonColors(Color(0xFF009adb)),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(
                                "Chỉnh sửa",
                                fontSize = 20.sp,
                            )
                        }

                        Button(
                            onClick = {
                                      if(!viewModel.checkDeleteWallet(selectedWallet)) {
                                          openConfirmDelete = true;
                                      }
                                else openErrorDelete = true;
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(70.dp)
                                .padding(start = 4.dp), // Add some spacing between the buttons
                            colors = ButtonDefaults.buttonColors(Do), // Assuming `Do` is a color value
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(
                                "Xoá",
                                fontSize = 20.sp,
                            )
                        }
                    }

                }
            }
        }
        if(openEditWallet)
            androidx.compose.material.AlertDialog(
                onDismissRequest = { openEditWallet = false },
                text = {
                    Column(
                    ) {
                        Text(
                            "Chỉnh sửa ví",
                            textAlign = TextAlign.Center,
                            style = TextStyle(fontSize = 20.sp),
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        // Ô nhập văn bản
                        OutlinedTextField(
                            value = textInput_ten,
                            onValueChange = { textInput_ten = it },
                            label = { Text("Tên ví") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.LightGray,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                    }
                },
                buttons = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 25.dp) // Padding ngang cho hàng chứa các nút
                    ) {
                        Button(
                            onClick = {
                                openEditWallet = false
                                textInput_ten = ""
                            },
                            colors = ButtonDefaults.buttonColors(Color(0xFFf25207)),
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 4.dp) // Khoảng cách giữa hai nút
                        ) {
                            Text("Huỷ")
                        }
                        Button(
                            onClick = {
                                openEditWallet = false
                                // Cái hàm update chỗ này
                                // Tên ví mới là textInput_ten
                                // Cái ví cần sửa là selectedWallet
                                textInput_ten = ""
                            },
                            colors = ButtonDefaults.buttonColors(Color(0xFF1cba46)),
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 4.dp) // Khoảng cách giữa hai nút
                        ) {
                            Text("Chỉnh sửa")
                        }
                    }
                }

            )
        if(openConfirmDelete)
            AlertDialog(
                onDismissRequest = { openConfirmDelete = false },
                title = { Text(text = "Xác nhận xóa") },
                text = { Text(text = "Bạn có chắc chắn muốn xóa?") },
                confirmButton = {
                    Button(onClick = {
                        openConfirmDelete = false
                        //Xóa ví chỗ này
                        //Ví cần xoá là selectedWallet
                        viewModel.DeleteWallet(selectedWallet)
                        walletList2  = walletList2.filter { it.id != selectedWallet.id }
                        selectedWallet = ViModel(0, "Tất cả", walletList2.sumOf { it.soDu })
                    }) {
                        Text("Xác nhận")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        openConfirmDelete = false
                    }) {
                        Text("Hủy")
                    }
                },
                properties = DialogProperties()
            )
        if(openErrorDelete)
            AlertDialog(
                onDismissRequest = { openErrorDelete = false },
                title = { Text(text = "Thông báo") },
                text = { Text(text = "Không thể xoá do đã có giao dịch được tạo với ví này") },
                confirmButton = {
                    Button(onClick = {
                        openErrorDelete = false
                    }) {
                        Text("Đóng")
                    }
                },
                properties = DialogProperties()
            )
    }
}

@Composable
fun MonthPickerDialog(
    initialDate: LocalDate,
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDate) -> Unit
) {
    var selectedYear by remember { mutableStateOf(initialDate.year.toString()) }
    var selectedMonth by remember { mutableStateOf(initialDate.month.value.toString()) }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Chọn Tháng",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            val month = selectedMonth.toIntOrNull() ?: -1
                            if (month != 1 && month > 1) {
                                selectedMonth = (month - 1).toString()
                            }
                        }
                    ) {
                        Icon(Icons.Default.ArrowBackIos, contentDescription = "Previous Month")
                    }

                    Text(
                        text = "Tháng",
                        style = TextStyle(fontSize = 20.sp),
                    )
                    BasicTextField(
                        modifier = Modifier
                            .background(Color.White)
                            .width(50.dp),
                        value = selectedMonth,
                        onValueChange = { newValue ->
                            if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                                val newMonth = newValue.toIntOrNull() ?: 0;
                                if(newMonth < 1 && newValue.isNotEmpty()) selectedMonth = "1"
                                else if(newMonth > 12) selectedMonth = "12"
                                else selectedMonth = newValue;
                            } },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 20.sp,
                            // Align text to the bottom
                            textAlign = TextAlign.Left,
                            lineHeight = 20.sp // Adjust line height as needed
                        ),

                    )

                    IconButton(
                        onClick = {
                            val month = selectedMonth.toIntOrNull() ?: -1
                            if (month != 1 && month < 12) {
                                selectedMonth = (month + 1).toString()
                            }
                        }
                    ) {
                        Icon(Icons.Default.ArrowForwardIos, contentDescription = "Next Month")
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            val year = selectedYear.toIntOrNull() ?: -1
                            if (year != 1 && year > 0) {
                                selectedYear = (year - 1).toString()
                            }
                        }
                    ) {
                        Icon(Icons.Default.ArrowBackIos, contentDescription = "Previous Month")
                    }

                    Text(
                        text = "Năm",
                        style = TextStyle(fontSize = 20.sp)
                    )
                    BasicTextField(
                        modifier = Modifier
                            .background(Color.White)
                            .width(50.dp),
                        value = selectedYear,
                        onValueChange = { newValue ->
                            if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                                val newYear = newValue.toIntOrNull() ?: 0;
                                if(newYear > LocalDate.now().year) selectedYear = LocalDate.now().year.toString()
                                else selectedYear = newValue;
                            } },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 20.sp,
                            // Align text to the bottom
                            textAlign = TextAlign.Left,
                            lineHeight = 20.sp // Adjust line height as needed
                        ),

                        )

                    IconButton(
                        onClick = {
                            val year = selectedYear.toIntOrNull() ?: -1
                            if (year != 1 && year < LocalDate.now().year) {
                                selectedYear = (year + 1).toString()
                            }
                        }
                    ) {
                        Icon(Icons.Default.ArrowForwardIos, contentDescription = "Next Month")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = onDismissRequest) {
                        Text("Hủy")
                    }
                    Button(
                        onClick = {
                            val year = selectedYear.toIntOrNull() ?: 0;
                            val month = selectedMonth.toIntOrNull() ?: 1;
                            onDateSelected(LocalDate.of(year, month, 1))
                            onDismissRequest()
                        }
                    ) {
                        Text("Xác nhận")
                    }
                }
            }
        }
    }
}

@Composable
fun YearPickerDialog(
    initialDate: LocalDate,
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDate) -> Unit
) {
    var selectedYear by remember { mutableStateOf(initialDate.year.toString()) }
    var selectedMonth by remember { mutableStateOf(initialDate.month.value) }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Chọn Năm",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            val year = selectedYear.toIntOrNull() ?: -1
                            if (year != 1 && year > 0) {
                                selectedYear = (year - 1).toString()
                            }
                        }
                    ) {
                        Icon(Icons.Default.ArrowBackIos, contentDescription = "Previous Month")
                    }

                    Text(
                        text = "Năm",
                        style = TextStyle(fontSize = 20.sp)
                    )
                    BasicTextField(
                        modifier = Modifier
                            .background(Color.White)
                            .width(50.dp),
                        value = selectedYear,
                        onValueChange = { newValue ->
                            if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                                val newYear = newValue.toIntOrNull() ?: 0;
                                if(newYear > LocalDate.now().year) selectedYear = LocalDate.now().year.toString()
                                else selectedYear = newValue;
                            } },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 20.sp,
                            // Align text to the bottom
                            textAlign = TextAlign.Left,
                            lineHeight = 20.sp // Adjust line height as needed
                        ),

                        )

                    IconButton(
                        onClick = {
                            val year = selectedYear.toIntOrNull() ?: -1
                            if (year != 1 && year < LocalDate.now().year) {
                                selectedYear = (year + 1).toString()
                            }
                        }
                    ) {
                        Icon(Icons.Default.ArrowForwardIos, contentDescription = "Next Month")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = onDismissRequest) {
                        Text("Huỷ")
                    }
                    Button(
                        onClick = {
                            val year = selectedYear.toIntOrNull() ?: 0;
                            onDateSelected(LocalDate.of(year, selectedMonth, 1))
                            onDismissRequest()
                        }
                    ) {
                        Text("Xác nhận")
                    }
                }
            }
        }
    }
}
