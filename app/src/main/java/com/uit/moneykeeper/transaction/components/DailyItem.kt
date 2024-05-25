package com.uit.moneykeeper.transaction.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uit.moneykeeper.models.GiaoDichModel
import com.uit.moneykeeper.models.PhanLoai
import com.uit.moneykeeper.transaction.viewmodel.DailyItemViewModel
import com.uit.moneykeeper.transaction.viewmodel.GiaoDichItemViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import com.uit.moneykeeper.ui.theme.Do
import com.uit.moneykeeper.ui.theme.XanhLa

@Composable
fun DailyItem(viewModel: DailyItemViewModel) {
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
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = viewModel.date.format(DateTimeFormatter.ofPattern("dd")),
                    modifier = Modifier.padding(all = 8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                val dayOfWeek = viewModel.date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("vi"))
                val backgroundColor = if (viewModel.date == LocalDate.now()) Color.Blue else Color.Gray

                Text(
                    text = dayOfWeek,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .background(backgroundColor, shape = RoundedCornerShape(50))
                        .padding(horizontal = 8.dp),
                    color = Color.White
                )

                Spacer(modifier = Modifier.weight(1f))

                Column (
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (viewModel.currentIn != 0.0) {
                        Text(
                            text = "+${DoubleToStringConverter.convert(viewModel.currentIn)}",
                            color = XanhLa,
                            textAlign = TextAlign.Right
                        )
                    }

                    if (viewModel.currentOut != 0.0) {
                        Text(
                            text = "–${DoubleToStringConverter.convert(viewModel.currentOut)}",
                            color = Do,
                            textAlign = TextAlign.Right
                        )
                    }
                }
            }

            for (giaoDich in viewModel.todayList) {
                Divider(color = Color.LightGray, thickness = 0.5.dp)

                val giaoDichViewModel = GiaoDichItemViewModel(giaoDich)
                GiaoDichItem(viewModel = giaoDichViewModel)
            }
        }
    }
}