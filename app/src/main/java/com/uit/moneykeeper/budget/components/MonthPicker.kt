package com.uit.moneykeeper.budget.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.window.Dialog
import java.time.LocalDate

@Composable
fun MonthPickerDialog(
    initialDate: LocalDate,
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDate) -> Unit
) {
    var selectedYear by remember { mutableStateOf(initialDate.year) }
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
                            if (selectedMonth > 1) {
                                selectedMonth -= 1
                            }
                        }
                    ) {
                        Icon(Icons.Default.ArrowBackIos, contentDescription = "Previous Month")
                    }

                    Text(
                        text = "Tháng $selectedMonth",
                        style = TextStyle(fontSize = 18.sp)
                    )

                    IconButton(
                        onClick = {
                            if (selectedMonth < 12) {
                                selectedMonth += 1
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
                            if (selectedYear > 1) {
                                selectedYear -= 1
                            }
                        }
                    ) {
                        Icon(Icons.Default.ArrowBackIos, contentDescription = "Previous Month")
                    }

                    Text(
                        text = "Năm $selectedYear",
                        style = TextStyle(fontSize = 18.sp)
                    )

                    IconButton(
                        onClick = {
                            selectedYear += 1
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
                            onDateSelected(LocalDate.of(selectedYear, selectedMonth, 1))
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