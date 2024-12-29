package com.moksh.presentation.core.utils

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Double.toStringAmount(): String {
    val formatter = NumberFormat.getNumberInstance(Locale("en", "IN"))

    return if (this % 1.0 == 0.0) {
        // For whole numbers, don't show decimals
        formatter.apply {
            maximumFractionDigits = 0
        }.format(this)
    } else {
        // For decimals, remove trailing zeros
        formatter.apply {
            minimumFractionDigits = 0
            maximumFractionDigits = 20
        }.format(this)
    }
}

fun Date.toFormattedTime(pattern:DatePatterns = DatePatterns.TimePattern): String {
    val formatter = SimpleDateFormat(pattern.pattern, Locale.ENGLISH)
    return formatter.format(this).lowercase()
}

enum class DatePatterns(val pattern: String) {
    TimePattern("h:mm a"),
    DatePattern("dd MMM yyyy"),
    ShortDatePattern("dd/MM/yyyy"),
    FullDateTimePattern("dd MMM yyyy, h:mm a"),
    MonthYearPattern("MMM yyyy"),
    DayPattern("EEEE"),  // Full day name
    ShortDayPattern("EEE"), // Short day name
    YearPattern("yyyy")
}

