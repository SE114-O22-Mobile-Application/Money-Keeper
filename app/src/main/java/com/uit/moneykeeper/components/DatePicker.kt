package com.uit.moneykeeper.components

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(){
    val calendarState = rememberUseCaseState()
    var selectedDate by remember { mutableStateOf(java.time.LocalDate.now()) }
    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true
        ),
        selection = CalendarSelection.Date {
                date ->
            Log.d("Ngày đã chọn: ", "$date")
            selectedDate=date
        }
    )
    Row {
        TextField(
            value = selectedDate.toString(),
            onValueChange = { /* Provide the value change handler here */ },
            modifier = Modifier
                .weight(1f) // Take up remaining space
                .width(50.dp) // Set the width of the TextField
        )
        IconButton(
            onClick = {
                calendarState.show()
            },
            modifier = Modifier.align(Alignment.CenterVertically),
            content = {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "Tuần đã chọn: ",
                    tint = Color.Black // Set the icon color to black
                )
            }
        )
    }

}