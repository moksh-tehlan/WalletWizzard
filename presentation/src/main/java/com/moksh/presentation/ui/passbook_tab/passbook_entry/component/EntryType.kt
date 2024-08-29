package com.moksh.presentation.ui.passbook_tab.passbook_entry.component

sealed class EntryType(val name: String) {
    data object Income : EntryType("income")
    data object Expense : EntryType("expense")
    companion object {
        fun valueOf(name: String?): EntryType {
            return when (name) {
                "income" -> Income
                "expense" -> Expense
                else -> throw IllegalArgumentException("Unknown entry type: $name")
            }
        }
    }
}