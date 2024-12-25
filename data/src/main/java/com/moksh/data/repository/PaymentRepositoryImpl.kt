package com.moksh.data.repository

import com.moksh.data.datasource.PaymentModeDataSource
import com.moksh.data.entities.local.PaymentModeEntity
import com.moksh.data.mappers.toDto
import com.moksh.data.mappers.toEntity
import com.moksh.domain.model.request.SavePaymentModeRequest
import com.moksh.domain.model.response.PaymentMode
import com.moksh.domain.repository.PaymentModeRepository
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result
import com.moksh.domain.util.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PaymentModeRepositoryImpl @Inject constructor(
    private val dataSource: PaymentModeDataSource
) : PaymentModeRepository {

    override suspend fun insertPaymentMode(paymentMode: SavePaymentModeRequest): Result<String, DataError.Local> {
        return dataSource.insertPaymentMode(paymentMode.toEntity())
    }

    override fun getAllPaymentModes(): Result<Flow<List<PaymentMode>>, DataError.Local> {
        return dataSource.getAllPaymentModes()
            .map { flow ->
                flow.map { list -> list.map { it.toDto() } }
            }

    }

    override suspend fun getPaymentModeById(id: String): Result<PaymentMode?, DataError.Local> {
        return dataSource.getPaymentModeById(id).map { it.toDto() }
    }

    override suspend fun updatePaymentMode(paymentMode: PaymentMode): Result<Unit, DataError.Local> {
        return dataSource.updatePaymentMode(paymentMode.toEntity())
    }

    override suspend fun deletePaymentMode(paymentMode: PaymentMode): Result<Unit, DataError.Local> {
        return dataSource.deletePaymentMode(paymentMode.toEntity())
    }

    override suspend fun searchPaymentModes(query: String): Result<List<PaymentMode>, DataError.Local> {
        return dataSource.searchPaymentModes(query).map { list -> list.map { it.toDto() } }
    }

    override suspend fun insertDefaultPaymentModes(): Result<Unit, DataError.Local> {
        val defaultPaymentModes = listOf(
            PaymentModeEntity(name = "Cash"),
            PaymentModeEntity(name = "Credit Card"),
            PaymentModeEntity(name = "Debit Card"),
            PaymentModeEntity(name = "Bank Transfer"),
            PaymentModeEntity(name = "UPI"),
        )

        defaultPaymentModes.forEach { paymentMode ->
            dataSource.insertPaymentMode(paymentMode)
        }

        return Result.Success(Unit)
    }
}