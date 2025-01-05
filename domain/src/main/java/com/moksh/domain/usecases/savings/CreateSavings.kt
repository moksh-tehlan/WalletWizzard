package com.moksh.domain.usecases.savings

import com.moksh.domain.model.request.InsertSaving
import com.moksh.domain.repository.SavingRepository
import javax.inject.Inject

class CreateSavings @Inject constructor(
    private val savingRepository: SavingRepository
) {
    suspend operator fun invoke(
        insertSaving: InsertSaving
    ) = savingRepository.insertSaving(
        insertSaving
    )
}