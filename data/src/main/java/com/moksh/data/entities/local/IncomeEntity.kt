package com.moksh.data.entities.local

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.moksh.domain.model.response.Income
import com.moksh.domain.model.response.PaymentMode

@Entity(
    tableName = "income",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["categoryId"])]
)
data class IncomeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amount: Double,
    val date: String,
    val remark: String,
    val categoryId: Long,
    @ColumnInfo(name = "payment_mode")
    val paymentMode: PaymentMode,
) {
    companion object {
        fun fromIncome(income: Income): IncomeEntity {
            return IncomeEntity(
                amount = income.amount,
                date = income.date,
                remark = income.remark,
                categoryId = income.category.id,
                paymentMode = income.paymentMode,
            )
        }
    }
}

data class IncomeWithCategory(
    @Embedded
    val income: IncomeEntity,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: CategoryEntity
)