package com.uit.moneykeeper.transaction.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uit.moneykeeper.models.GiaoDichModel
import com.uit.moneykeeper.models.PhanLoai
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DailyItem(date: LocalDate,giaoDichList: List<GiaoDichModel>) {
    val currentIn = giaoDichList.filter { it.loaiGiaoDich.loai == PhanLoai.Thu }.sumOf { it.soTien }
    val currentOut = giaoDichList.filter { it.loaiGiaoDich.loai == PhanLoai.Chi }.sumOf { it.soTien }
    val currentSum = currentIn - currentOut

    Card (
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        border = BorderStroke(1.dp, Color.Gray),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = date.format(DateTimeFormatter.ofPattern("dd")),
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
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

                Spacer(modifier = Modifier.weight(1f))

                Column {
                    if (currentIn != 0.0) {
                        Text(
                            text = "+${DoubleToStringConverter.convert(currentIn)}",
                            color = Color.Green,
                            textAlign = TextAlign.End
                        )
                    }

                    if (currentOut != 0.0) {
                        if (currentIn != 0.0) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        Text(
                            text = "–${DoubleToStringConverter.convert(currentOut)}",
                            color = Color.Red,
                            textAlign = TextAlign.End
                        )
                    }

                    if (currentIn != 0.0 && currentOut != 0.0) {
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = buildAnnotatedString {
                                if (currentSum > 0) {
                                    append("+")
                                }
                                append(DoubleToStringConverter.convert(currentSum))
                            },
                            textAlign = TextAlign.End
                        )
                    }
                }
            }

            Divider(color = Color.LightGray, thickness = 1.dp)

            for (giaoDich in giaoDichList) {
                GiaoDichItem(giaoDich)

                // Add a lighter gray separator between each GiaoDichItem.
                Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
            }
        }
    }
}