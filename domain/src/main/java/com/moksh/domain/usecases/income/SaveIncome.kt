package com.moksh.domain.usecases.income

import com.moksh.domain.model.response.Income
import com.moksh.domain.repository.IncomeRepository
import com.moksh.domain.util.DataError
import com.moksh.domain.util.EmptyResult

class SaveIncome(private val incomeRepository: IncomeRepository) {
    suspend fun invoke(income: Income): EmptyResult<DataError> {
        return incomeRepository.saveIncome(income)
    }
}