package com.uit.moneykeeper.transaction.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.uit.moneykeeper.models.GiaoDichModel
import com.uit.moneykeeper.models.PhanLoai

@Composable
fun GiaoDichItem(giaoDich: GiaoDichModel) {
    val color = when (giaoDich.loaiGiaoDich.loai) {
        PhanLoai.Chi -> Color.Red
        PhanLoai.Thu -> Color.Green
    }

    val prefix = when (giaoDich.loaiGiaoDich.loai) {
        PhanLoai.Chi -> "-"
        PhanLoai.Thu -> "+"
    }

    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = giaoDich.loaiGiaoDich.ten,
                    color = giaoDich.loaiGiaoDich.mauSac,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(2f).align(Alignment.CenterVertically),
                )

                Column(modifier = Modifier.weight(3f)) {
                    Text(
                        text = giaoDich.loaiGiaoDich.ten,
                        color = giaoDich.loaiGiaoDich.mauSac,
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = giaoDich.taiKhoan.ten,
                        color = giaoDich.taiKhoan.mauSac,
                    )
                }

                Text(
                    text = "$prefix${giaoDich.soTien}",
                    color = color,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = giaoDich.ghiChu,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}