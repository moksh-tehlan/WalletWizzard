package com.moksh.domain.model.response

import java.util.Date

data class PaymentMode(
    val id: String,
    val name: String,
    val icon: String? = null,
    val createdAt: Date? = null,
    val updatedAt: Date? = null
)
