package com.moksh.domain.repository

import com.moksh.domain.model.response.PaymentMode
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface PaymentModeRepository {
    suspend fun insertPaymentMode(paymentMode: PaymentMode): Result<String, DataError.Local>
    fun getAllPaymentModes(): Result<Flow<List<PaymentMode>>, DataError.Local>
    suspend fun getPaymentModeById(id: String): Result<PaymentMode?, DataError.Local>
    suspend fun updatePaymentMode(paymentMode: PaymentMode): Result<Unit, DataError.Local>
    suspend fun deletePaymentMode(paymentMode: PaymentMode): Result<Unit, DataError.Local>
    suspend fun searchPaymentModes(query: String): Result<List<PaymentMode>, DataError.Local>
    suspend fun insertDefaultPaymentModes(): Result<Unit, DataError.Local>
}