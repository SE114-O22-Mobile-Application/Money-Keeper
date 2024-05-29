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
import com.uit.moneykeeper.models.PhanLoai
import com.uit.moneykeeper.transaction.viewmodel.SumEachTypeItemViewModel
import com.uit.moneykeeper.ui.theme.Do
import com.uit.moneykeeper.ui.theme.XanhLa

@Composable
fun SumEachTypeItem(viewModel: SumEachTypeItemViewModel) {
    val item = viewModel.item
    val backgroundColor by remember { mutableStateOf(Color.White) }

    val amountColor = when (item.loaiGiaoDich.loai) {
        PhanLoai.Chi -> Do
        PhanLoai.Thu -> XanhLa
    }

    val amountPrefix = when (item.loaiGiaoDich.loai) {
        PhanLoai.Chi -> "-"
        PhanLoai.Thu -> "+"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
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
                    color = item.loaiGiaoDich.mauSac
                ) {
                    Icon(
                        imageVector = item.loaiGiaoDich.icon.getIcon(),
                        contentDescription = item.loaiGiaoDich.ten,
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(all = 8.dp)
                            .size(24.dp),
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = item.loaiGiaoDich.ten,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "$amountPrefix${DoubleToStringConverter.convert(item.sum)}",
                    color = amountColor,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    textAlign = TextAlign.Right
                )
            }
        }
    }
}