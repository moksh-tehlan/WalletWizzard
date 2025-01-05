package com.moksh.presentation.ui.common

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.moksh.presentation.core.theme.WalletWizzardTheme
import java.util.Date

@Composable
fun WizzardDatePicker(
    modifier: Modifier = Modifier,
    onDateSelected: (Date?) -> Unit,
    onDismiss: () -> Unit = {}
) {

    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val selectedDate = datePickerState.selectedDateMillis?.let { milliseconds ->
                    Date(milliseconds)
                }
                onDateSelected(selectedDate)
                onDismiss()
            }) {
                Text("Ok")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
@Preview
private fun WizzardDatePickerPreview() {
    WalletWizzardTheme {
        WizzardDatePicker(
            onDateSelected = {}
        )
    }
}