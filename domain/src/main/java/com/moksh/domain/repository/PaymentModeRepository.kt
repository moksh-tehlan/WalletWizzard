package com.moksh.domain.repository

import com.moksh.domain.model.request.SavePaymentModeRequest
import com.moksh.domain.model.response.PaymentMode
import com.moksh.domain.usecases.payment_mode.SavePaymentMode
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface PaymentModeRepository {
    suspend fun insertPaymentMode(paymentMode: SavePaymentModeRequest): Result<String, DataError>
    fun getAllPaymentModes(): Result<Flow<List<PaymentMode>>, DataError>
    suspend fun getPaymentModeById(id: String): Result<PaymentMode?, DataError>
    suspend fun updatePaymentMode(paymentMode: PaymentMode): Result<Unit, DataError>
    suspend fun deletePaymentMode(paymentMode: PaymentMode): Result<Unit, DataError>
    suspend fun searchPaymentModes(query: String): Result<List<PaymentMode>, DataError>
    suspend fun insertDefaultPaymentModes(): Result<Unit, DataError>
}