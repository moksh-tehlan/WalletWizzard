package com.moksh.presentation.ui.auth.otp.viewmodel

data class OtpVerificationState(
    val isLoading: Boolean = false,
    val buttonEnabled: Boolean = false,
    val phoneNumber: String = "",
    val otp: String = ""
)
