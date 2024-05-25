package com.uit.moneykeeper.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import kotlinx.datetime.DayOfWeek
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekPicker() {
    val calendarState = rememberUseCaseState()
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var startOfWeek: LocalDate by remember { mutableStateOf(selectedDate) }
    var endOfWeek: LocalDate by remember { mutableStateOf(selectedDate) }

    Column {
        CalendarDialog(
            state = calendarState,
            config = CalendarConfig(
                monthSelection = true,
                yearSelection = true
            ),
            selection = CalendarSelection.Date { date ->
                selectedDate = date
                // Calculate start and end of the week based on the selected date
                when (date.dayOfWeek) {
                    DayOfWeek.MONDAY -> {
                        startOfWeek = date
                        endOfWeek = startOfWeek.plusDays(6) // End of the week is 6 days after the start
                    }
                    DayOfWeek.TUESDAY -> {
                        startOfWeek = date.minusDays(1)
                        endOfWeek = startOfWeek.plusDays(6) // End of the week is 6 days after the start
                    }
                    DayOfWeek.WEDNESDAY -> {
                        startOfWeek = date.minusDays(2)
                        endOfWeek = startOfWeek.plusDays(6) // End of the week is 6 days after the start
                    }
                    DayOfWeek.THURSDAY -> {
                        startOfWeek = date.minusDays(3)
                        endOfWeek = startOfWeek.plusDays(6) // End of the week is 6 days after the start
                    }
                    DayOfWeek.FRIDAY -> {
                        startOfWeek = date.minusDays(4)
                        endOfWeek = startOfWeek.plusDays(6) // End of the week is 6 days after the start
                    }
                    DayOfWeek.SATURDAY -> {
                        startOfWeek = date.minusDays(5)
                        endOfWeek = startOfWeek.plusDays(6) // End of the week is 6 days after the start
                    }
                    DayOfWeek.SUNDAY -> {
                        startOfWeek = date.minusDays(6)
                        endOfWeek = startOfWeek.plusDays(6) // End of the week is 6 days after the start
                    }
                }
            }

        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                calendarState.show()
            }
        ) {
            Text(startOfWeek.toString()+" - "+endOfWeek.toString())
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = "Select Week",
                tint = Color.Black // Set the icon color to black
            )
        }

    }
}
