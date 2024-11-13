package com.moksh.domain.usecases.category

import com.moksh.domain.model.response.TransactionType
import com.moksh.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategories @Inject constructor(private val categoryRepository: CategoryRepository) {
    fun invoke(type: TransactionType, query : String? = "") = categoryRepository.getCategoriesByType(type)
}