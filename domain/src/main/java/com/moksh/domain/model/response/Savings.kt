package com.moksh.domain.model.response

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Date

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
        get() = endDate?.let {
            if (!isExpired) {
                val diff = it.time - Date().time
                Date(diff)
            } else {
                Date(0)
            }
        }
}
