package com.moksh.presentation.ui.passbook_entry.viewmodel

import com.moksh.domain.model.response.Category
import com.moksh.domain.model.response.PaymentMode

sealed interface PassbookEntryAction {
    data class AmountChanged(val amount: String) : PassbookEntryAction
    data class RemarkChanged(val remark: String) : PassbookEntryAction
    data object OnCategoryChange : PassbookEntryAction
    data object OnPaymentModeChange : PassbookEntryAction
    data class PaymentModeChanged(val paymentMode: PaymentMode) : PassbookEntryAction
    data class DateChanged(val date: String) : PassbookEntryAction
    data object SaveTransaction : PassbookEntryAction
    data class UpdateSelectedCategoryAndPaymentMode(val categoryId:String?,val paymentId:String?) : PassbookEntryAction
}