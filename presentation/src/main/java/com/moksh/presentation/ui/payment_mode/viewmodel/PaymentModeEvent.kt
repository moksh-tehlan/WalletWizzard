package com.moksh.presentation.ui.payment_mode.viewmodel

import com.moksh.domain.model.response.PaymentMode

sealed interface PaymentModeEvent {
    data class OnPaymentModeChanged(val paymentMode: PaymentMode?=null) : PaymentModeEvent
    data object OnBackPress : PaymentModeEvent
}