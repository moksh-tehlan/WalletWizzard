package com.moksh.data.entities.local

import androidx.room.Embedded
import androidx.room.Relation

data class TransactionWithDetails(
    @Embedded val transaction: TransactionEntity,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "id"
    )
    val category: CategoryEntity?,
    @Relation(
        parentColumn = "payment_mode_id",
        entityColumn = "id"
    )
    val paymentMode: PaymentModeEntity?
)

