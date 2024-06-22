package com.moksh.presentation.ui.auth.phone.viewmodel

sealed interface PhoneLoginEvent {
    data class OnOtpSent(val phoneNumber: String) : PhoneLoginEvent
}