package com.uit.moneykeeper.budget.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.navigation.NavController
import com.uit.moneykeeper.budget.viewmodel.NewBudgetViewModel
import com.uit.moneykeeper.budget.viewmodel.getGiaoDichByLGD
import com.uit.moneykeeper.budget.viewmodel.getGiaoDichByMY
import com.uit.moneykeeper.budget.viewmodel.getListCTNganSachByNS
import com.uit.moneykeeper.budget.viewmodel.getListNganSach
import com.uit.moneykeeper.budget.viewmodel.getListNganSachByMonthYear
import com.uit.moneykeeper.global.GlobalObject
import com.uit.moneykeeper.global.nganSachList
import com.uit.moneykeeper.home.components.Statistical
import com.uit.moneykeeper.home.views.MonthPickerDialog
import com.uit.moneykeeper.home.views.YearPickerDialog
import com.uit.moneykeeper.home.views.formatNumberWithCommas
import com.uit.moneykeeper.models.CTNganSachModel
import com.uit.moneykeeper.models.NganSachModel
import com.uit.moneykeeper.ui.theme.Do
import com.uit.moneykeeper.ui.theme.Nen
import com.uit.moneykeeper.ui.theme.Xanh
import com.uit.moneykeeper.ui.theme.XanhLa
import java.time.LocalDate

@Composable
fun BudgetScreen(navController: NavController, newbudgetViewModel: NewBudgetViewModel) {
//    Text(text = "This is Home Screen 3")
    println("is in budget")
    MainContent(navController = navController, newbudgetViewModel)
}

@Composable
fun MainContent(navController: NavController, newbudgetViewModel: NewBudgetViewModel) {
    var openSelectMonth by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf(LocalDate.now()) }
    val listNS: List<NganSachModel> = getListNganSachByMonthYear(selectedTime);
    print(listNS);
//    if(listNS.isEmpty()) navController.navigate("newbudget")
    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(XanhLa)
            .wrapContentSize(Alignment.Center),

            ) {
            Text(text = "NGÂN SÁCH",
                style = TextStyle(fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 20.sp))
        }
        Row(modifier = Modifier
            .padding(top = 50.dp)
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
                            selectedTime.minusMonths(1)
                    }
            )
                Text(text = "${selectedTime.monthValue}/${selectedTime.year}",
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .clickable { openSelectMonth = true },
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))

            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = "Menu",
                tint = Color.Black,
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp)
                    .clickable {
                        selectedTime =
                            selectedTime.plusMonths(1)
                    }
            )
        }
        if(openSelectMonth)
                MonthPickerDialog(
                    initialDate = selectedTime,
                    onDismissRequest = { openSelectMonth = false },
                    onDateSelected = {date ->
                        selectedTime = date
                    }
                )
        if(listNS.isNotEmpty())
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 100.dp) // Để đẩy nó xuống dưới Combobox
            ) {

                if(listNS.size > 0)
                    item { BudgetDetail(ngansach = listNS[0]) }

            }
        else
        FloatingActionButton(
            onClick = {
                newbudgetViewModel.setTime(selectedTime)
                println("Budget: " + selectedTime)
                navController.navigate("newbudget")
                      },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.White
            )
        }
    }
}

@Composable
fun BudgetDetail(ngansach: NganSachModel) {

    val ListCTNS = getListCTNganSachByNS(ngansach);
    val tmpListGD = getGiaoDichByMY(ngansach.thoiGian)
    val TongTien = tmpListGD.sumOf { it.soTien }
    Column() {

        Column(
            modifier = Modifier
                .padding(top = 3.dp)
                .fillMaxWidth()
                .background(Color.White)
                .height(70.dp) // Adjust the height to fit both the Row and ProgressBar
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(40.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Tháng " + ngansach.thoiGian.monthValue,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                    modifier = Modifier
                        .padding(horizontal = 6.dp))
                Text(
                    formatNumberWithCommas(ngansach.TongTien),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                    modifier = Modifier
                        .padding(horizontal = 6.dp))

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(horizontal = 20.dp)
            ) {
                LinearProgressIndicator(
                    progress = Math.min(1f, (TongTien/ngansach.TongTien).toFloat()), // Set your desired progress value here
                    color = if(TongTien >= ngansach.TongTien) Do else XanhLa, // Set your desired color here
                    backgroundColor = Color.Gray, // Set your desired background color here
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp) // Adjust the height of the ProgressBar as needed
                )

                Text(
                    text = formatNumberWithCommas(TongTien), // Your progress value here
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 8.dp)
                )
            }
        }
        for(CT in ListCTNS) {
            val ListGD = getGiaoDichByLGD(tmpListGD, CT.LoaiNS)
            val total = ListGD.sumOf { it.soTien }
            Column(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
                    .background(Color.White)
                    .height(70.dp) // Adjust the height to fit both the Row and ProgressBar
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = CT.TenNS,
                        style = TextStyle(fontSize = 18.sp),
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                    )
                    Text(
                        text = formatNumberWithCommas(CT.SoTien),
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(horizontal = 20.dp)
                ) {
                    LinearProgressIndicator(
                        progress = Math.min(1f, (total/CT.SoTien).toFloat()), // Set your desired progress value here
                        color = if(total >= CT.SoTien) Do else XanhLa, // Set your desired color here
                        backgroundColor = Color.Gray, // Set your desired background color here
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp) // Adjust the height of the ProgressBar as needed
                    )

                    Text(
                        text = formatNumberWithCommas(total), // Your progress value here
                        style = TextStyle(fontSize = 14.sp),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(top = 8.dp)
                    )
                }
            }
        }
    }
}