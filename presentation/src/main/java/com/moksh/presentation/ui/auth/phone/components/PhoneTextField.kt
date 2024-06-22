package com.moksh.presentation.ui.auth.phone.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moksh.presentation.core.theme.WalletWizzardTheme
import com.moksh.presentation.ui.common.Gap
import com.moksh.presentation.ui.common.WizzardTextField

@Composable
fun PhoneTextField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "+91",
            style = MaterialTheme.typography.bodySmall
        )
        Gap(size = 15.dp)
        WizzardTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp),
            value = value, onValueChange = onValueChange,
            hint = "Enter phone number",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            ),
        )
    }
}

@Composable
@Preview
private fun PhoneTextFieldPreview() {
    WalletWizzardTheme {
        PhoneTextField(value = "", onValueChange = {})
    }
}