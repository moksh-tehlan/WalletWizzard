package com.moksh.presentation.ui.auth.phone.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhoneLoginViewModel @Inject constructor() : ViewModel() {

    private val _phoneLoginState = MutableStateFlow(PhoneLoginState())
    val phoneLoginState = _phoneLoginState.asStateFlow()

    private val eventChannel = Channel<PhoneLoginEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(phoneLoginAction: PhoneLoginAction) {
        when (phoneLoginAction) {
            is PhoneLoginAction.ChangePhoneNumber -> changePhoneNumber(phoneLoginAction.phoneNumber)
            is PhoneLoginAction.OnContinue -> onContinue()
        }
    }

    private fun changePhoneNumber(phoneNumber: String) {
        if (phoneNumber.length <= 10) {
            _phoneLoginState.value = _phoneLoginState.value.copy(
                phoneNumber = phoneNumber
            )
            if (_phoneLoginState.value.isLoading) return
            if (phoneNumber.length == 10) {
                _phoneLoginState.value = _phoneLoginState.value.copy(
                    buttonEnabled = true
                )
            } else {
                _phoneLoginState.value = _phoneLoginState.value.copy(
                    buttonEnabled = false
                )
            }
        }
    }

    private fun onContinue() {
        _phoneLoginState.value = _phoneLoginState.value.copy(
            isLoading = true,
        )
        viewModelScope.launch {
            delay(500)
            eventChannel.send(PhoneLoginEvent.OnOtpSent(phoneNumber = _phoneLoginState.value.phoneNumber))
            _phoneLoginState.value = _phoneLoginState.value.copy(
                isLoading = false,
                buttonEnabled = _phoneLoginState.value.phoneNumber.length == 10
            )
        }
    }
}