package com.moksh.data.entities.utils

import androidx.room.TypeConverter
import com.moksh.domain.model.response.TransactionType
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time

    @TypeConverter
    fun toTransactionType(value: String) = enumValueOf<TransactionType>(value)

    @TypeConverter
    fun fromTransactionType(transactionType: TransactionType) = transactionType.name
}