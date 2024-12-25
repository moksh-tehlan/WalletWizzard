package com.moksh.domain.usecases.payment_mode

import com.moksh.domain.model.request.SavePaymentModeRequest
import com.moksh.domain.repository.PaymentModeRepository
import javax.inject.Inject

class SavePaymentMode @Inject constructor(private val paymentModeRepository: PaymentModeRepository) {
    suspend fun invoke(paymentMode: SavePaymentModeRequest) = paymentModeRepository.insertPaymentMode(
        paymentMode
    )
}