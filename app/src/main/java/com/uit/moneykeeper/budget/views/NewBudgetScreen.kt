package com.uit.moneykeeper.budget.views

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.uit.moneykeeper.budget.viewmodel.AddNewCTNganSach
import com.uit.moneykeeper.budget.viewmodel.AddNewNganSach
import com.uit.moneykeeper.budget.viewmodel.getAllLGDChi
import com.uit.moneykeeper.budget.viewmodel.getListCTNganSachByNS
import com.uit.moneykeeper.budget.viewmodel.getListNganSachByMonthYear
import com.uit.moneykeeper.global.GlobalObject
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@SuppressWarnings("SpellCheckingInspection")
@Composable
fun NewBudget(navController: NavController, thoiGian: LocalDate) {
    println("New budget: " + thoiGian)
    val listLGD = getAllLGDChi()
    val listState = remember { List(listLGD.size) { mutableStateOf("") } }
    val listNS = getListNganSachByMonthYear(thoiGian.minusMonths(1));
    val listCTNS = if(listNS.size > 0) getListCTNganSachByNS(listNS[0]) else null
    if(listCTNS != null)
        for(i in 0 until listCTNS.size) {
            for(ns in listCTNS) {
    //            println("So sanh: " + ns.LoaiNS.ten + " " + listLGD[i].ten + " " + i + " " + ns.TenNS);
                if(ns.LoaiNS.equals(listLGD[i])) {
                    listState[i].value = ns.SoTien.toInt().toString()
                    break;
                }
            }
        }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Thêm mới ngân sách",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (!navController.popBackStack().not()) {
                            navController.navigate("budget")
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back to Transaction screen") } }
            )
        },
        modifier = Modifier.fillMaxSize()
    )
    {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)) {
            LazyColumn(modifier = Modifier.padding(top = 80.dp)) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 0.dp), // Padding around the box
                        contentAlignment = Alignment.Center // Center the content within the box
                    ) {
                        Text(
                            text = thoiGian.monthValue.toString() + "/" + thoiGian.year.toString(),
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                    }
                }
                itemsIndexed(listState) { index, textState ->
                    CustomTextFieldWithLabel(textState, listLGD[index].ten)
                }
                item { Spacer(modifier = Modifier.height(30.dp))}
                item {
                    Button(onClick = {
                        var sum = 0.0
                        for (mutableState in listState) {
                            // Chuyển đổi chuỗi thành số
                            val number = mutableState.value.toDoubleOrNull()
                            // Kiểm tra xem có phải là số hợp lệ không trước khi cộng vào tổng
                            if (number != null) {
                                sum += number
                            }
                        }
                        val ns = AddNewNganSach(thoiGian, sum)
                        for(i in 0 until listState.size) {
                            AddNewCTNganSach(ns, listLGD[i], listState[i].value.toDouble())
                        }
                        if (!navController.popBackStack().not()) {
                            navController.navigate("budget")
                        }
                    },
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
                            Text("Thêm ngân sách",
                                fontSize = 20.sp,)
                        }
                    }
                }
            }
        }
    }


}
@Composable
fun CustomTextFieldWithLabel(textState: MutableState<String>, label: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp) // Padding around the row
    ) {
        // Text label taking 30% width
        Text(
            text = label,
            modifier = Modifier
                .weight(0.3f)
                .align(Alignment.Bottom), // Center vertically within the row
            style = TextStyle(
                color = Color.Black,
                fontSize = 16.sp
            )
        )

        // Column to hold the TextField and Divider
        Column(
            modifier = Modifier.weight(0.7f)
        ) {
            // BasicTextField without border
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                value = textState.value,
                onValueChange = { newValue ->
                    if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                        textState.value = newValue
                    } },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 20.sp,
                    // Align text to the bottom
                    textAlign = TextAlign.Left,
                    lineHeight = 20.sp // Adjust line height as needed
                ),
                decorationBox = { innerTextField ->
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp) // Adjust height as needed
                            .padding(bottom = 4.dp), // Padding to place text at the bottom
                        contentAlignment = Alignment.BottomStart // Align text to the bottom start
                    ) {
                        innerTextField()
                    }
                }
            )

            // Divider below TextField
            Divider(
                color = Color.Gray,
                thickness = 1.dp
            )
        }
    }
}