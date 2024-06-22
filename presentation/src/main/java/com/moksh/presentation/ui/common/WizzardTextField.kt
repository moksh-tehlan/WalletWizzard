package com.moksh.presentation.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle

@Composable
fun WizzardTextField(
    modifier: Modifier = Modifier,
    value: String,
    hint: String? = null,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(
        color = MaterialTheme.colorScheme.onBackground
    ),
    cursorBrush: Brush = SolidColor(MaterialTheme.colorScheme.onBackground),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        enabled = enabled,
        textStyle = textStyle,
        cursorBrush = cursorBrush,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    ) {
        Box(modifier = modifier, contentAlignment = Alignment.CenterStart) {
            it.invoke()
            if (value.isEmpty() && hint != null) {
                Text(
                    modifier = Modifier.alpha(0.2f),
                    text = hint, style = textStyle
                )
            }
        }
    }
}
