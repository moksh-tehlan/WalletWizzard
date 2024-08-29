package com.moksh.data.entities.utils

import androidx.room.TypeConverter
import com.moksh.domain.model.response.PaymentMode

class Converters {
    @TypeConverter
    fun fromPaymentMode(paymentMode: PaymentMode): String {
        return paymentMode.name
    }

    @TypeConverter
    fun toPaymentMode(paymentMode: String): PaymentMode {
        return PaymentMode.valueOf(paymentMode)
    }
}