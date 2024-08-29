package com.moksh.data.mappers

import com.moksh.data.entities.local.IncomeWithCategory
import com.moksh.domain.model.response.Income

fun IncomeWithCategory.toIncome(): Income {
    return Income(
        id = income.id,
        amount = income.amount,
        date = income.date,
        remark = income.remark,
        paymentMode = income.paymentMode,
        category = category.toCategory(),
    )
}