package com.moksh.presentation.ui.auth.phone.viewmodel

sealed interface PhoneLoginAction {
    data class ChangePhoneNumber(val phoneNumber: String) : PhoneLoginAction
    data object OnContinue : PhoneLoginAction
}