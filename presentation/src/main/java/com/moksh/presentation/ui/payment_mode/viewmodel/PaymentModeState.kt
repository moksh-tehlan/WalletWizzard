package com.moksh.presentation.ui.payment_mode.viewmodel

import com.moksh.domain.model.response.PaymentMode

data class PaymentModeState(
    val selectedPaymentMode: PaymentMode? = null,
    val paymentModeDataState: PaymentModeDataState = PaymentModeDataState.Loading,
    val addNewCategorySheet: AddNewPaymentModeBottomSheetState? = null,
)

sealed interface PaymentModeDataState {
    data object Loading : PaymentModeDataState
    data class Success(val paymentModes: List<PaymentMode>) : PaymentModeDataState
    data class Error(val error: String) : PaymentModeDataState
}

data class AddNewPaymentModeBottomSheetState(
    val value: String = "",
    val error: String? = null,
    val isLoading: Boolean = false,
)