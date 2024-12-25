package com.moksh.data.datasource

import com.moksh.data.dao.PaymentModeDao
import com.moksh.data.entities.local.PaymentModeEntity
import com.moksh.data.entities.utils.safeCall
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result
import kotlinx.coroutines.flow.Flow

class PaymentModeDataSource(private val paymentModeDao: PaymentModeDao) {
    suspend fun insertPaymentMode(paymentMode: PaymentModeEntity): Result<String, DataError> =
        safeCall {
            paymentModeDao.insertPaymentMode(paymentMode)
            paymentMode.id
        }

    fun getAllPaymentModes(): Result<Flow<List<PaymentModeEntity>>, DataError> = safeCall {
        paymentModeDao.getAllPaymentModes()
    }

    suspend fun getPaymentModeById(id: String): Result<PaymentModeEntity, DataError> =
        safeCall {
            paymentModeDao.getPaymentModeById(id)
        }

    suspend fun updatePaymentMode(paymentMode: PaymentModeEntity): Result<Unit, DataError> =
        safeCall {
            paymentModeDao.updatePaymentMode(paymentMode)
        }

    suspend fun deletePaymentMode(paymentMode: PaymentModeEntity): Result<Unit, DataError> =
        safeCall {
            paymentModeDao.deletePaymentMode(paymentMode)
        }

    suspend fun searchPaymentModes(query: String): Result<List<PaymentModeEntity>, DataError> =
        safeCall {
            paymentModeDao.searchPaymentModes(query)
        }
}