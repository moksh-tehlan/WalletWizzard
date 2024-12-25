package com.moksh.presentation.ui.passbook_entry.viewmodel

import com.moksh.domain.model.response.TransactionType

sealed interface PassbookEntryEvent {
    data object TransactionSaved : PassbookEntryEvent
    data class OnCategoryChange(
        val transactionType: TransactionType,
        val categoryId: String? = null
    ) :
        PassbookEntryEvent

    data class OnPaymentModeChange(val paymentModeId: String? = null) :
        PassbookEntryEvent
}