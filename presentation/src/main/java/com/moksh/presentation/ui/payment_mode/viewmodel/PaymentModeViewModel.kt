package com.moksh.presentation.ui.payment_mode.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moksh.domain.model.request.SavePaymentModeRequest
import com.moksh.domain.model.response.PaymentMode
import com.moksh.domain.usecases.payment_mode.GetPaymentModes
import com.moksh.domain.usecases.payment_mode.SavePaymentMode
import com.moksh.domain.util.Result
import com.moksh.presentation.core.utils.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PaymentModeViewModel @Inject constructor(
    private val getPaymentModes: GetPaymentModes,
    private val savePaymentMode: SavePaymentMode,
    savedStateHandle: SavedStateHandle,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _paymentModeState = MutableStateFlow(PaymentModeState())
    val paymentModeState = _paymentModeState.asStateFlow()
        .onStart {
            val paymentModeId = savedStateHandle.get<String>("paymentModeId")
            getPaymentModeById(paymentModeId)
            getAllPaymentModes()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            PaymentModeState()
        )

    private val _paymentModeFlow = MutableSharedFlow<PaymentModeEvent>()
    val paymentModeFlow = _paymentModeFlow.asSharedFlow()

    fun onAction(action: PaymentModeAction) {
        when (action) {
            is PaymentModeAction.PaymentModeChanged -> paymentModeChanged(action.paymentMode)
            is PaymentModeAction.ToggleAddNewPaymentModeSheet -> toggleAddNewPaymentModeSheet()
            is PaymentModeAction.AddPaymentModeChanged -> addNewPaymentMode(action.name)
            is PaymentModeAction.OnBackPress -> onBackPress()
            is PaymentModeAction.SaveNewPaymentMode -> saveNewPaymentMode()
        }
    }

    private fun getAllPaymentModes() {
        viewModelScope.launch {
            when (val result = getPaymentModes.invoke()) {
                is Result.Success -> {
                    result.data.collect { paymentModes ->
                        _paymentModeState.update {
                            it.copy(
                                paymentModeDataState = PaymentModeDataState.Success(paymentModes = paymentModes)
                            )
                        }
                    }
                }
                is Result.Error -> {
                    Timber.d(result.error.asUiText().asString(context))
                }
            }
        }
    }

    private fun getPaymentModeById(paymentModeId: String?) {
        if (paymentModeId.isNullOrEmpty()) return
        viewModelScope.launch {
            when (val result = getPaymentModes.invoke(paymentModeId)) {
                is Result.Success -> {
                    _paymentModeState.update {
                        it.copy(
                            selectedPaymentMode = result.data,
                        )
                    }
                }

                is Result.Error -> {
                    Timber.d(result.error.asUiText().asString(context))
                }
            }
        }
    }


    private fun onBackPress() {
        viewModelScope.launch {
            _paymentModeFlow.emit(PaymentModeEvent.OnBackPress)
        }
    }

    private fun addNewPaymentMode(name: String) {
        _paymentModeState.update {
            it.copy(
                addNewCategorySheet = it.addNewCategorySheet?.copy(
                    value = name
                )
            )
        }
    }

    private fun saveNewPaymentMode() {
        val name = _paymentModeState.value.addNewCategorySheet?.value ?: ""
        if (name.isEmpty())return
        viewModelScope.launch {
            val request = SavePaymentModeRequest(
                name = name
            )
            when (val result = savePaymentMode.invoke(request)) {
                is Result.Success -> {
                    toggleAddNewPaymentModeSheet()
                }

                is Result.Error -> {
                    Timber.d(result.error.asUiText().asString(context = context))
                }
            }
        }

    }

    private fun toggleAddNewPaymentModeSheet() {
        _paymentModeState.update {
            it.copy(
                addNewCategorySheet = if (it.addNewCategorySheet == null) AddNewPaymentModeBottomSheetState() else null
            )
        }
    }

    private fun paymentModeChanged(paymentMode: PaymentMode) {
        _paymentModeState.update {
            it.copy(
                selectedPaymentMode = paymentMode
            )
        }
        viewModelScope.launch {
            _paymentModeFlow.emit(PaymentModeEvent.OnPaymentModeChanged(paymentMode))
        }

    }
}