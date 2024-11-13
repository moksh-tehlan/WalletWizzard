package com.moksh.data.mappers

import com.moksh.data.entities.local.TransactionEntity
import com.moksh.data.entities.local.TransactionWithDetails
import com.moksh.domain.model.response.SaveTransaction
import com.moksh.domain.model.response.Transaction

object TransactionMapper {
    fun toDto(transactionWithDetails: TransactionWithDetails) = Transaction(
        id = transactionWithDetails.transaction.id,
        amount = transactionWithDetails.transaction.amount,
        type = transactionWithDetails.transaction.type,
        category = transactionWithDetails.category?.let { CategoryMapper.toDto(it) },
        paymentMode = transactionWithDetails.paymentMode?.let { PaymentModeMapper.toDto(it) },
        remark = transactionWithDetails.transaction.remark,
        attachmentUri = transactionWithDetails.transaction.attachmentUri,
        createdAt = transactionWithDetails.transaction.createdAt,
        updatedAt = transactionWithDetails.transaction.updatedAt
    )

    fun toEntity(dto: SaveTransaction) = TransactionEntity(
        amount = dto.amount,
        type = dto.type,
        categoryId = dto.category?.id,
        paymentModeId = dto.paymentMode?.id,
        remark = dto.remark,
        attachmentUri = dto.attachmentUri,
        createdAt = dto.createdAt,
        updatedAt = dto.updatedAt
    )

    fun toEntity(dto: Transaction) = TransactionEntity(
        id = dto.id,
        amount = dto.amount,
        type = dto.type,
        categoryId = dto.category?.id,
        paymentModeId = dto.paymentMode?.id,
        remark = dto.remark,
        attachmentUri = dto.attachmentUri,
        createdAt = dto.createdAt,
        updatedAt = dto.updatedAt
    )
}

fun TransactionWithDetails.toDto() = TransactionMapper.toDto(this)
fun Transaction.toEntity() = TransactionMapper.toEntity(this)
fun SaveTransaction.toEntity() = TransactionMapper.toEntity(this)
