package com.moksh.presentation.ui.payment_mode.viewmodel

import com.moksh.domain.model.response.PaymentMode

sealed interface PaymentModeAction {
    data class PaymentModeChanged(val paymentMode: PaymentMode) : PaymentModeAction
    data object ToggleAddNewPaymentModeSheet : PaymentModeAction
    data class AddPaymentModeChanged(val name: String) : PaymentModeAction
    data object OnBackPress : PaymentModeAction
    data object SaveNewPaymentMode : PaymentModeAction
}
