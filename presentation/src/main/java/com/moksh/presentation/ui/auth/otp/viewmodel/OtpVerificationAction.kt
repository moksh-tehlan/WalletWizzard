package com.moksh.presentation.ui.auth.otp.viewmodel

sealed interface OtpVerificationAction {
    data class OnOtpChange(val otp: String) : OtpVerificationAction
    data object OnSubmitOtp : OtpVerificationAction
}