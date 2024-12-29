package com.moksh.domain.model.response

import java.util.Calendar
import java.util.Date

data class Transaction(
    val id: String,
    val amount: Double,
    val type: TransactionType,
    val category: Category?,
    val paymentMode: PaymentMode?,
    val remark: String? = null,
    val attachmentUri: String? = null,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

data class SaveTransaction(
    val amount: Double,
    val type: TransactionType,
    val category: Category?,
    val paymentMode: PaymentMode?,
    val remark: String? = null,
    val attachmentUri: String? = null,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

enum class TransactionType {
    Income, Expenses
}