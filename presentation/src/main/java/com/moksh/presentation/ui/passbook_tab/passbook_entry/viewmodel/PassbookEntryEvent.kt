package com.moksh.presentation.ui.passbook_tab.passbook_entry.viewmodel

sealed interface PassbookEntryEvent {
    data object TransactionSaved : PassbookEntryEvent
}