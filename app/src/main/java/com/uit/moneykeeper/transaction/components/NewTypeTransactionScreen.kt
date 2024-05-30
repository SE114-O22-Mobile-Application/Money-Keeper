package com.uit.moneykeeper.transaction.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NewTypeTransactionScreen() {
    var transactionName by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tạo loại giao dịch mới",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = transactionName,
            onValueChange = { transactionName = it },
            label = { Text("Tên giao dịch") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        IconGrid()

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Button(
                onClick = { /* Handle "Chi" action */ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
            ) {
                Text("Chi")
            }
            Button(
                onClick = { /* Handle "Thu" action */ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
            ) {
                Text("Thu")
            }
        }

        Button(
            onClick = { /* Handle save action */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Lưu")
        }
    }
}

@Composable
fun IconGrid() {
    val icons = IconEnum.values().filter { it != IconEnum.Null }

    Column(
        modifier = Modifier
            .background(Color(0xFFF0F0F0), RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        for (row in icons.chunked(3)) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                for (iconEnum in row) {
                    IconButton(onClick = { /* Handle icon selection */ }) {
                        Icon(imageVector = iconEnum.getIcon(), contentDescription = null)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewTypeTransactionScreenPreview() {
    NewTypeTransactionScreen()
}
