package com.moksh.domain.usecases.category

import com.moksh.domain.repository.CategoryRepository
import javax.inject.Inject

class InsertDefaultCategory @Inject constructor(private val categoryRepository: CategoryRepository) {
    suspend fun invoke() = categoryRepository.insertDefaultCategories()
}