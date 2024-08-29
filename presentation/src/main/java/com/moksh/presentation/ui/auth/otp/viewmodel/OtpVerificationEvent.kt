package com.moksh.presentation.ui.auth.otp.viewmodel

sealed interface OtpVerificationEvent {
    data object OtpVerified : OtpVerificationEvent
    data object OtpVerificationFailed : OtpVerificationEvent
}
