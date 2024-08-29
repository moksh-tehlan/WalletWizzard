package com.moksh.data.entities.local

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.moksh.domain.model.response.Expense
import com.moksh.domain.model.response.PaymentMode

@Entity(
    tableName = "expense",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onUpdate = CASCADE
        )
    ]
)
data class ExpenseEntity(
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
        fun fromExpense(expense: Expense) = ExpenseEntity(
            amount = expense.amount,
            date = expense.date,
            remark = expense.remark,
            categoryId = expense.category.id,
            paymentMode = expense.paymentMode,
            id = expense.id
        )
    }
}

data class ExpenseWithCategory(
    @Embedded
    val expense: ExpenseEntity,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: CategoryEntity
)
