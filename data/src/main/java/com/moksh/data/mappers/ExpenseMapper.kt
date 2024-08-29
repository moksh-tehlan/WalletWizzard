package com.moksh.data.mappers

import com.moksh.data.entities.local.ExpenseEntity
import com.moksh.data.entities.local.ExpenseWithCategory
import com.moksh.domain.model.response.Expense

fun ExpenseWithCategory.toExpense() = Expense(
    id = expense.id,
    amount = expense.amount,
    date = expense.date,
    category = category.toCategory(),
    remark = expense.remark,
    paymentMode = expense.paymentMode,
)