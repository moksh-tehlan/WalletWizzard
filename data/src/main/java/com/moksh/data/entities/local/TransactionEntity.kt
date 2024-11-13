package com.moksh.data.entities.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.moksh.domain.model.response.TransactionType
import java.util.Date
import java.util.UUID

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = PaymentModeEntity::class,
            parentColumns = ["id"],
            childColumns = ["payment_mode_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index("category_id"),
        Index("payment_mode_id")
    ]
)
data class TransactionEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "type") val type: TransactionType,
    @ColumnInfo(name = "category_id") val categoryId: String?,
    @ColumnInfo(name = "payment_mode_id") val paymentModeId: String?,
    @ColumnInfo(name = "remark") val remark: String?,
    @ColumnInfo(name = "attachment_uri") val attachmentUri: String?,
    @ColumnInfo(name = "is_synced") val isSynced: Boolean = false,
    @ColumnInfo(name = "created_at") val createdAt: Date = Date(),
    @ColumnInfo(name = "updated_at") val updatedAt: Date = Date()
)
