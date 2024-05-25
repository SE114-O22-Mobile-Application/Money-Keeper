package com.uit.moneykeeper.components

import android.util.Log
import android.util.LogPrinter
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun YearPicker() {
    var selectedDate by remember { mutableStateOf("") }
    val calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, 1999)
        set(Calendar.MONTH, 1)
        set(Calendar.DAY_OF_MONTH, 1)
    }
    val calendarMax = Calendar.getInstance().apply {
        set(Calendar.YEAR, 2100)
        set(Calendar.MONTH, 0)
        set(Calendar.DAY_OF_MONTH, 1)
    }

    val initialCalendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR))
    }


    // State to control the visibility of the picker
    val (open, setOpen) = remember { mutableStateOf(false) }

    Icon(
        imageVector = Icons.Default.CalendarToday,
        contentDescription = "Show Month Picker",
        modifier = Modifier
            .padding(16.dp)
            .clickable {
                // Toggle the state to open the picker
                setOpen(true)
            }
    )
    TextField(
        value = TextFieldValue(selectedDate),
        onValueChange = {
            // Update the selected date when the text field value changes
            selectedDate = it.text
        },
        label = { Text("Năm: ") },
        enabled = false, // Disable editing
        modifier = Modifier.padding(16.dp)
    )
    if (open) {
        Box(
            Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.7f)
        ){
            ComposeCalendar(
                minDate = calendar.time,
                maxDate = calendarMax.time,
                initialDate = initialCalendar.time,
                locale = Locale("en"),
                title = "Select Year",
                buttonTextSize = 15.sp,
                calendarType = CalendarType.ONLY_YEAR,
                monthViewType = MonthViewType.ONLY_NUMBER_ONE_COLUMN,

                listener = object : SelectDateListener {
                    override fun onDateSelected(date: Date) {
                        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault())
                        val formattedDate = dateFormat.format(date)
                        Log.i("Năm đã chọn: ", formattedDate) // Log the selected date
                        selectedDate = formattedDate // You may update this as needed
                        val selectedYear = yearFromDate(date)
                        initialCalendar.apply {
                            set(Calendar.YEAR, selectedYear)
                        }
                        setOpen(false)
                    }
                    override fun onCanceled() {
                        setOpen(false)
                    }
                }
            )
        }
    }
}
private fun monthFromDate(date: Date): Int {
    val calendar = Calendar.getInstance()
    calendar.time = date
    return calendar.get(Calendar.MONTH)
}
private fun yearFromDate(date: Date): Int {
    val calendar = Calendar.getInstance()
    calendar.time = date
    return calendar.get(Calendar.YEAR)
}