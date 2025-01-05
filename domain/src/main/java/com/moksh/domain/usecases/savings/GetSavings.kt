package com.moksh.domain.usecases.savings

import com.moksh.domain.repository.SavingRepository
import javax.inject.Inject

class GetSavings @Inject constructor(
    private val savingRepository: SavingRepository
) {
    operator fun invoke() = savingRepository.getAllActiveSavings()
}