package com.moksh.presentation.ui.auth.phone.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moksh.domain.usecases.auth.SendOtp
import com.moksh.domain.util.Result
import com.moksh.presentation.core.utils.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PhoneLoginViewModel @Inject constructor(
    private val sendOtp: SendOtp,
    @ApplicationContext private val context: Context
) : ViewModel() {

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
            when (val response = sendOtp.invoke("+91"+_phoneLoginState.value.phoneNumber)) {
                is Result.Success -> {
                    eventChannel.send(
                        PhoneLoginEvent.OnOtpSent(
                            phoneNumber = _phoneLoginState.value.phoneNumber,
                            verificationId = response.data
                        )
                    )
                    _phoneLoginState.value = _phoneLoginState.value.copy(
                        isLoading = false,
                        buttonEnabled = true
                    )
                }

                is Result.Error -> {
                    Timber.d(response.error.asUiText().asString(context))
                }
            }
        }
    }
}