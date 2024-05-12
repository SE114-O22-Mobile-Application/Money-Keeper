package com.uit.moneykeeper.components
import android.app.DatePickerDialog
import android.util.Log
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.uit.moneykeeper.R
import kotlinx.datetime.LocalDate
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(){
    val calendarState = rememberSheetState()
    var selectedDate by remember { mutableStateOf(java.time.LocalDate.now()) }
    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true
        ),
        selection = CalendarSelection.Date {
            date ->
            Log.d("SelectedDate", "$date")
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
                    contentDescription = "Select Week",
                    tint = Color.Black // Set the icon color to black
                )
            }
        )
    }

}