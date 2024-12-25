package com.moksh.domain.usecases.category

import com.moksh.domain.model.request.SaveCategoryRequest
import com.moksh.domain.repository.CategoryRepository
import javax.inject.Inject

class SaveCategory @Inject constructor(private val categoryRepository: CategoryRepository) {
    suspend fun invoke(saveCategoryRequest: SaveCategoryRequest) = categoryRepository.insertCategory(
        saveCategoryRequest
    )
}