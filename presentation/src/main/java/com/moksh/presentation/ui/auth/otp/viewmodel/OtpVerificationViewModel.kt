package com.moksh.presentation.ui.auth.otp.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moksh.domain.model.request.SaveUserRequest
import com.moksh.domain.usecases.category.InsertDefaultCategory
import com.moksh.domain.usecases.payment_mode.InsertDefaultPaymentMode
import com.moksh.domain.usecases.user.SaveUser
import com.moksh.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpVerificationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val saveUser: SaveUser,
    private val insertPaymentModes: InsertDefaultPaymentMode,
    private val insertDefaultCategory: InsertDefaultCategory,
) : ViewModel() {
    private val _otpVerificationState = MutableStateFlow(OtpVerificationState())
    val otpState = _otpVerificationState.asStateFlow()

    private val eventChannel = Channel<OtpVerificationEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        _otpVerificationState.value = _otpVerificationState.value.copy(
            phoneNumber = savedStateHandle["phoneNumber"] ?: ""
        )
    }

    fun onAction(otpVerificationAction: OtpVerificationAction) {
        when (otpVerificationAction) {
            is OtpVerificationAction.OnOtpChange -> changeOtp(otpVerificationAction.otp)
            is OtpVerificationAction.OnSubmitOtp -> onSubmitOtp()
        }
    }

    private fun changeOtp(otp: String) {
        if (otp.length <= 4) {
            _otpVerificationState.value = _otpVerificationState.value.copy(
                otp = otp
            )
            if (_otpVerificationState.value.isLoading) return
            if (otp.length == 4) {
                _otpVerificationState.value = _otpVerificationState.value.copy(
                    buttonEnabled = true
                )
            } else {
                _otpVerificationState.value = _otpVerificationState.value.copy(
                    buttonEnabled = false
                )
            }
        }
    }

    private fun onSubmitOtp() {
        _otpVerificationState.value = _otpVerificationState.value.copy(
            isLoading = true,
        )
        viewModelScope.launch {
            val user = SaveUserRequest(
                phoneNumber = _otpVerificationState.value.phoneNumber,
                name = "Moksh Tehlan",
            )
            when (saveUser.invoke(user)) {
                is Result.Success -> {
                    insertPaymentModes.invoke()
                    insertDefaultCategory.invoke()
                    eventChannel.send(OtpVerificationEvent.OtpVerified)
                    _otpVerificationState.value = _otpVerificationState.value.copy(
                        isLoading = false,
                        buttonEnabled = _otpVerificationState.value.phoneNumber.length == 4
                    )
                }

                is Result.Error -> {
                    eventChannel.send(OtpVerificationEvent.OtpVerificationFailed)
                }
            }
        }
    }
}