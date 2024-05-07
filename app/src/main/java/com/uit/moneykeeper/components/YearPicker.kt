package com.uit.moneykeeper.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun YearPickerComponent(selectedYear: MutableState<Int?>) {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val years = (currentYear - 100)..currentYear // Display years from 100 years ago to the current year

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Chọn năm",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primary
        )
        Spacer(modifier = Modifier.padding(8.dp))

        for (year in years) {
            Button(
                onClick = { selectedYear.value = year },
                modifier = Modifier.padding(vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (year == selectedYear.value) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
                    contentColor = if (year == selectedYear.value) Color.White else MaterialTheme.colors.onSurface
                )
            ) {
                Text(text = year.toString())
            }
        }
    }
}
