package com.moksh.domain.model.response

data class Income(
    val id: Long = 0,
    val amount: Double,
    val date: String,
    val remark: String,
    val category: Category,
    val paymentMode: PaymentMode,
)