package com.moksh.presentation.ui.passbook_tab.passbook_entry.viewmodel

import com.moksh.domain.model.response.Category
import com.moksh.domain.model.response.PaymentMode

sealed interface PassbookEntryAction {
    data class AmountChanged(val amount: String) : PassbookEntryAction
    data class RemarkChanged(val remark: String) : PassbookEntryAction
    data class CategoryChanged(val category: Category) : PassbookEntryAction
    data class PaymentModeChanged(val paymentMode: PaymentMode) : PassbookEntryAction
    data class DateChanged(val date: String) : PassbookEntryAction
    data object SaveTransaction : PassbookEntryAction
}