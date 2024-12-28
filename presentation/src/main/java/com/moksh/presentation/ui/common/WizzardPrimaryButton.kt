package com.moksh.presentation.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moksh.presentation.core.theme.WalletWizzardTheme

@Composable
fun WizzardPrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit,
    buttonTestTag: String? = null,
    loaderTestTag: String? = null,
) {
    Button(
        modifier = modifier.semantics {
            if (buttonTestTag != null) this.testTag = buttonTestTag
        },
        onClick = {
            if (!isLoading)
                onClick()
        },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primaryContainer),
        shape = RoundedCornerShape(0.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.semantics {
                    if (loaderTestTag != null) testTag = loaderTestTag
                },
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
@Preview
private fun WizzardPrimaryButtonPreview() {
    WalletWizzardTheme {
        WizzardPrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            text = "CONTINUE",
            enabled = true,
            isLoading = false,
            onClick = {}
        )
    }
}