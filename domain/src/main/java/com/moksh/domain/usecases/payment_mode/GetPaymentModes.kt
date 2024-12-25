package com.moksh.domain.usecases.payment_mode

import com.moksh.domain.repository.PaymentModeRepository
import javax.inject.Inject

class GetPaymentModes @Inject constructor(private val paymentModeRepository: PaymentModeRepository) {
    operator fun invoke() = paymentModeRepository.getAllPaymentModes()
    suspend fun invoke(paymentModeId: String) =
        paymentModeRepository.getPaymentModeById(paymentModeId)
}