package com.moksh.presentation.ui.home_tab.viewmodel

import com.moksh.domain.model.response.Savings
import com.moksh.presentation.core.utils.UiText

data class PocketState(
    val pockets: List<Savings> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null,
)
