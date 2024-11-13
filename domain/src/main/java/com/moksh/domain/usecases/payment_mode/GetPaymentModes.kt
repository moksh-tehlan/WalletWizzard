package com.moksh.domain.usecases.payment_mode

import com.moksh.domain.repository.PaymentModeRepository
import javax.inject.Inject

class GetPaymentModes @Inject constructor(private val paymentModeRepository: PaymentModeRepository) {
    suspend operator fun invoke() = paymentModeRepository.getAllPaymentModes()
}