package com.moksh.presentation.ui.passbook_entry.viewmodel

import com.moksh.domain.model.response.Category
import com.moksh.domain.model.response.PaymentMode
import com.moksh.domain.model.response.TransactionType

data class PassbookEntryState(
    val id: String = "",
    val entryType: TransactionType = TransactionType.Income,
    val amount: String = "",
    val remark: String = "",
    val category: Category?=null,
    val paymentMode: PaymentMode?=null,
    val date: String = "",
    val paymentModeList: List<PaymentMode> = emptyList(),
    val categoryList: List<Category> = emptyList(),
    val isLoading: Boolean = false
)
