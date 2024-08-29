package com.moksh.domain.usecases.income

import com.moksh.domain.model.response.Income
import com.moksh.domain.repository.IncomeRepository
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result
import kotlinx.coroutines.flow.Flow

class GetIncome(private val incomeRepository: IncomeRepository) {
    fun invoke(): Result<Flow<List<Income>>, DataError> {
        return incomeRepository.getIncome()
    }
}