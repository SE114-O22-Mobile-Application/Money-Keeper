package com.uit.moneykeeper.transaction.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.uit.moneykeeper.models.PhanLoai
import com.uit.moneykeeper.transaction.viewmodel.GiaoDichItemViewModel
import com.uit.moneykeeper.ui.theme.Do
import com.uit.moneykeeper.ui.theme.XanhLa

@Composable
fun GiaoDichItem(navController: NavController, viewModel: GiaoDichItemViewModel) {
    val giaoDich = viewModel.giaoDich
    var backgroundColor by remember { mutableStateOf(Color.White) }

    val amountColor = when (giaoDich.loaiGiaoDich.loai) {
        PhanLoai.Chi -> XanhLa
        PhanLoai.Thu -> Do
    }

    val amountPrefix = when (giaoDich.loaiGiaoDich.loai) {
        PhanLoai.Chi -> "-"
        PhanLoai.Thu -> "+"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                    navController.navigate("TransactionDetailScreen/${giaoDich.id}")
                },
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor, // Use the color state here
        ),
    ) {
        Column (
            modifier = Modifier.padding(all = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = giaoDich.loaiGiaoDich.mauSac
                ) {
                    Icon(
                        imageVector = giaoDich.loaiGiaoDich.icon.getIcon(),
                        contentDescription = giaoDich.loaiGiaoDich.ten,
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(all = 8.dp)
                            .size(24.dp),
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column (
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = giaoDich.ten,
                        fontWeight = FontWeight.Bold
                    )

                    Row {
                        Icon(
                            imageVector = Icons.Filled.AccountBalanceWallet,
                            contentDescription = "Tên ví",
                            tint = Color.Gray,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .size(20.dp)
                                .padding(end = 4.dp)
                        )

                        Text(
                            text = giaoDich.vi.ten,
                            color = Color.Black,
                            fontSize = 16.sp,
                        )
                    }
                }

                Text(
                    text = "$amountPrefix${DoubleToStringConverter.convert(giaoDich.soTien)}",
                    color = amountColor,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    textAlign = TextAlign.Right
                )
            }

            if (giaoDich.ghiChu.isNotEmpty())
            {
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Description,
                        contentDescription = "Ghi chú",
                        tint = Color.Gray,
                        modifier = Modifier.align(Alignment.CenterVertically).size(16.dp)
                    )

                    Text(
                        text = giaoDich.ghiChu,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}