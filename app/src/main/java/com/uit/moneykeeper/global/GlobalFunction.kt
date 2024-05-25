package com.uit.moneykeeper.global

import com.google.firebase.Timestamp
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

object GlobalFunction {

    fun convertLocalDateToTimestamp(localDate: LocalDate): Timestamp {
        // Create a Date from the LocalDate at the start of the day
        val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())

        // Get the seconds and nanoseconds from the Date
        val seconds = date.time / 1000
        val nanoseconds = ((date.time % 1000) * 1000000).toInt()

        // Create a new Timestamp from the seconds and nanoseconds
        return Timestamp(seconds, nanoseconds)
    }

    fun convertTimestampToLocalDate(timestamp: Timestamp): LocalDate {
        // Create a Date from the Timestamp
        val date = Date(timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000)

        // Convert the Date to a LocalDate
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    }

}