package com.moksh.domain.usecases.payment_mode

import com.moksh.domain.repository.PaymentModeRepository
import javax.inject.Inject

class InsertDefaultPaymentMode @Inject constructor(private val paymentModeRepository: PaymentModeRepository) {
    suspend fun invoke() = paymentModeRepository.insertDefaultPaymentModes()
}