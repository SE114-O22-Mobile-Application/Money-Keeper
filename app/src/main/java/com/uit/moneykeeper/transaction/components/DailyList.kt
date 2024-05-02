package com.uit.moneykeeper.transaction.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uit.moneykeeper.models.GiaoDichModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DailyList(giaoDichList: List<GiaoDichModel>) {
    val sortedGiaoDichList = giaoDichList.sortedByDescending { it.ngayGiaoDich }

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(sortedGiaoDichList, key = { giaoDich -> giaoDich.id }) { giaoDich ->
            val date = giaoDich.ngayGiaoDich

            if (sortedGiaoDichList.firstOrNull() { it.ngayGiaoDich == date } == giaoDich) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = date.format(DateTimeFormatter.ofPattern("dd")),
                        modifier = Modifier.padding(vertical = 8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )

                    val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("vi"))
                    val backgroundColor = if (date == LocalDate.now()) Color.Blue else Color.Gray

                    Text(
                        text = dayOfWeek,
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .background(backgroundColor, shape = RoundedCornerShape(50))
                            .padding(horizontal = 8.dp),
                        color = Color.White
                    )
                }
            }

            GiaoDichItem(giaoDich)
        }
    }
}
