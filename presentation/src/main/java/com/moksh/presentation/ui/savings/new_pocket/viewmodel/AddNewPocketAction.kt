package com.moksh.presentation.ui.savings.new_pocket.viewmodel

import java.util.Date

sealed class AddNewPocketAction {
    data class OnPocketNameChange(val name: String) : AddNewPocketAction()
    data class OnAmountChange(val amount: String) : AddNewPocketAction()
    data class OnDateChange(val date: Date) : AddNewPocketAction()
    data object OnToggleDatePicker : AddNewPocketAction()
    data object OnAddNewPocket : AddNewPocketAction()
}