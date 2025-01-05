package com.moksh.presentation.ui.savings.new_pocket.viewmodel

import com.moksh.presentation.core.utils.UiText
import java.util.Date

data class AddNewPocketState(
    val pocketName: String = "",
    val amount: String = "",
    val date: Date? = null,
    val showDatePicker: Boolean = false,
    val error: UiText? = null,
    val isButtonLoading: Boolean = false,
)