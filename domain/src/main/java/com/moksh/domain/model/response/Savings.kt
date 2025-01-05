package com.moksh.domain.model.response

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.concurrent.TimeUnit

data class Savings(
    val id: String,
    val name: String,
    val targetAmount: Double,
    val currentAmount: Double,
    val endDate: Date?,
    val progressBarColor: Int,
    val notes: String?,
    val isActive: Boolean,
    val isSynced: Boolean,
    val createdAt: Date,
    val updatedAt: Date
) {
    companion object {
        const val MIN_TARGET_AMOUNT = 0.0
    }

    val progress: Float
        get() = if (targetAmount > 0) (currentAmount / targetAmount).toFloat() else 0f

    val isCompleted: Boolean
        get() = currentAmount >= targetAmount

    val isExpired: Boolean
        get() = endDate?.let { it < Date() } ?: false

    val remainingAmount: Double
        get() = (targetAmount - currentAmount).coerceAtLeast(0.0)

    val daysLeft: Date?
        @RequiresApi(Build.VERSION_CODES.O)
        get() = endDate?.let {
            if (!isExpired) {
                val start = LocalDate.now()
                val end = it.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                val days = ChronoUnit.DAYS.between(start, end)
                Date.from(LocalDate.ofEpochDay(days).atStartOfDay(ZoneId.systemDefault()).toInstant())
            } else {
                Date(0) // or null
            }
        }
}
