package com.moksh.presentation.ui.passbook_tab.passbook_entry.viewmodel

import com.moksh.domain.model.response.Category
import com.moksh.domain.model.response.PaymentMode
import com.moksh.presentation.ui.passbook_tab.passbook_entry.component.EntryType

data class PassbookEntryState(
    val entryType: EntryType = EntryType.Income,
    val amount: Double = 0.0,
    val remark: String = "",
    val category: Category = Category(
        id = 0,
        name = "Rent"
    ),
    val paymentMode: PaymentMode = PaymentMode.CASH,
    val date: String = "",
)
