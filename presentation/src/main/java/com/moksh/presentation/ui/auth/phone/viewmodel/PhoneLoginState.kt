package com.moksh.presentation.ui.auth.phone.viewmodel

data class PhoneLoginState(
    val isLoading: Boolean = false,
    val buttonEnabled: Boolean = false,
    val phoneNumber: String = ""
)
