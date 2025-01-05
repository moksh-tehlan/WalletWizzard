package com.moksh.domain.model.request

import java.util.Date

data class InsertSaving(
    val name: String,
    val targetAmount: Double,
    val endDate: Date? = null,
    val progressBarColor: Int,
    val notes: String?=null,
)
