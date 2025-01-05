package com.moksh.presentation.ui.savings.new_pocket.viewmodel

sealed class AddNewPocketEvent {
    data object CreatePocketSuccess : AddNewPocketEvent()
}